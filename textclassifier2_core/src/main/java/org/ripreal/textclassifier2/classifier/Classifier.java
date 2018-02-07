package org.ripreal.textclassifier2.classifier;

import org.ripreal.textclassifier2.actions.ClassifierAction;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.textreaders.ClassifiableReader;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.textreaders.ClassifiableReaderBuilder;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

// BUILDER + COMPOSITE
public class Classifier {

    private ClassifiableReader reader = null;

    private final List<ClassifierUnit> classifierUnits = new ArrayList<>();
    private final List<ClassifierAction> listeners = new ArrayList<>();

    private final int AMOUNT_OF_TEXTS_FOR_CHECKING = 5;

    // CONSTRUCTORS

    public static Classifier fromReader(Function<ClassifiableReaderBuilder, ClassifiableReader> provider) {
        Classifier classifier = new Classifier();
        classifier.reader = provider.apply(ClassifiableReaderBuilder.builder());
        return classifier;
    }

    // CLIENT SECTION

    public Classifier addNeroClassifierUnit(Characteristic characteristic, NGramStrategy nGramStrategy) {
        classifierUnits.add(new NeroClassifierUnit(characteristic, nGramStrategy));
        return this;
    }

    public Classifier subscribe(ClassifierAction action) {
        listeners.add(action);
        classifierUnits.forEach(unit -> unit.subscribe(action));
        return this;
    }

    public Classifier build() {

        if (!initialized()) {
            dispatch("Error. No classifier units were specified!");
            return this;
        }

        List<ClassifiableText> classifiableTexts = reader.toClassifiableTexts();

        buildClassifiers(classifiableTexts);
        shutDownClassifiers();
        checkClassifiersAccuracy(
            classifiableTexts.subList(0, Math.min(AMOUNT_OF_TEXTS_FOR_CHECKING, classifiableTexts.size())));

        return this;
    }

    public void saveClassifiers(File file) {
        for (ClassifierUnit classifier : classifierUnits) {
            classifier.saveTrainedClassifier(file);
        }
    }

    public void saveClassifiers(OutputStream stream) {
        for (ClassifierUnit classifier : classifierUnits) {
            classifier.saveTrainedClassifier(stream);
        }
    }

    // INNER SECTION

    private void buildClassifiers(List<ClassifiableText> classifiableTexts) {
        for (ClassifierUnit classifier : classifierUnits) {
            classifier.build(classifiableTexts);
        }
    }

    private void shutDownClassifiers() {
        for (ClassifierUnit classifier : classifierUnits) {
            classifier.shutdown();
        }
    }

    private void checkClassifiersAccuracy(List<ClassifiableText> textForTesting) {

        for (ClassifierUnit classifier : classifierUnits) {
            Characteristic characteristic = classifier.getCharacteristic();
            int correctlyClassified = 0;

            for (ClassifiableText classifiableText : textForTesting) {
                CharacteristicValue idealValue = classifiableText.getCharacteristicValue(characteristic.getName());
                CharacteristicValue classifiedValue = classifier.classify(classifiableText);

                if (classifiedValue.getValue().equals(idealValue.getValue())) {
                    correctlyClassified++;
                }
            }
            double accuracy = ((double) correctlyClassified / textForTesting.size()) * 100;
            dispatch(String.format("Accuracy of Classifier for '" + characteristic.getName() + "' characteristic: %.2f%%", accuracy));
        }
    }

    private boolean initialized() {
        return ! (reader == null || classifierUnits.size() == 0);
    }

    private void dispatch(String text) {
        listeners.forEach(action -> action.dispatch(ClassifierAction.EventTypes.CLASSIFIER_EVENT, text));
    }

}
