package org.ripreal.textclassifier2.classifier;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.actions.ClassifierAction;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicFactory;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.modelimp.DefCharacteristicFactory;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.ngram.VocabularyBuilder;
import org.ripreal.textclassifier2.textreaders.ClassifiableReader;
import org.ripreal.textclassifier2.textreaders.ClassifiableReaderBuilder;

import javax.validation.constraints.Negative;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

// BUILDER + COMPOSITE
@RequiredArgsConstructor
public final class Classifier {

    private final ClassifiableReader reader;

    private final CharacteristicFactory characteristicFactory;

    private final List<ClassifierUnit> classifierUnits = new ArrayList<>();

    private final List<ClassifierAction> listeners = new ArrayList<>();

    private final int AMOUNT_OF_TEXTS_FOR_CHECKING = 5;

    // CONSTRUCTORS

    public static Classifier fromReader(@NotNull Function<ClassifiableReaderBuilder, ClassifiableReader> provider,
        @NotNull CharacteristicFactory characteristicFactory) {

        ClassifiableReader reader = provider.apply(ClassifiableReaderBuilder.builder(characteristicFactory));
        Classifier classifier = new Classifier(reader, characteristicFactory);
        return classifier;
    }

    // CLIENT SECTION

    public Classifier addNeroClassifierUnit(String characteristicName, @NotNull NGramStrategy nGramStrategy) {
        classifierUnits.add(new NeroClassifierUnit(characteristicFactory.newCharacteristic(characteristicName), nGramStrategy));
        return this;
    }

    public Classifier addClassifierUnit(@NotNull ClassifierUnit unit) {
        classifierUnits.add(unit);
        return this;
    }

    public Classifier subscribe(@NotNull ClassifierAction action) {
        listeners.add(action);
        classifierUnits.forEach(unit -> unit.subscribe(action));
        reader.subscribe(action);
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

    public void saveClassifiers(@NotNull File file) {
        for (ClassifierUnit classifier : classifierUnits) {
            classifier.saveTrainedClassifier(file);
        }
    }

    public void saveClassifiers(@NotNull OutputStream stream) {
        for (ClassifierUnit classifier : classifierUnits) {
            classifier.saveTrainedClassifier(stream);
        }
    }

    // INNER SECTION

    private void buildClassifiers(@NotNull List<ClassifiableText> classifiableTexts) {

        for (ClassifierUnit classifier : classifierUnits) {
            classifier.setVocabulary(new VocabularyBuilder(classifier.getNGramStrategy()).getVocabulary(classifiableTexts, characteristicFactory));
            classifier.build(classifiableTexts);
        }
    }

    private void shutDownClassifiers() {
        for (ClassifierUnit classifier : classifierUnits) {
            classifier.shutdown();
        }
    }

    private void checkClassifiersAccuracy(@NotEmpty List<ClassifiableText> textForTesting) {

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

    private void dispatch(@NotNull String text) {
        listeners.forEach(action -> action.dispatch(ClassifierAction.EventTypes.CLASSIFIER_EVENT, text));
    }

}
