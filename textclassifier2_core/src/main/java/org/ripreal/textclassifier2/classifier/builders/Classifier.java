package org.ripreal.textclassifier2.classifier.builders;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.classifier.ClassifierUnit;
import org.ripreal.textclassifier2.classifier.textreaders.ClassifiableReader;
import org.ripreal.textclassifier2.classifier.textreaders.DataSourceException;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

// FACADE + BRIDGE
@NoArgsConstructor
class Classifier {

    private final List<ClassifierUnit> classifierUnits = new ArrayList<>();

    public Classifier(ClassifierUnit classifierUnit) {
        this.classifierUnits.add(classifierUnit);
    }

    public void train() {

        // create and train classifiers

        List<ClassifiableText> classifiableTexts = textReader.toClassifiableTexts();

        createClassifiers(characteristics, vocabulary);
        trainAndSaveClassifiers(classifiableTextForTrain);
        checkClassifiersAccuracy();
    }

    private List<ClassifiableText> getClassifiableTexts(File file, int sheetNumber) {
        List<ClassifiableText> classifiableTexts = new ArrayList<>();
        try {

        } catch (IOException | DataSourceException e) {

        }

        return classifiableTexts;
    }

    private void createClassifiers(List<Characteristic> characteristics, List<VocabularyWord> vocabulary) {
        for (Characteristic characteristic : characteristics) {
            ClassifierUnit classifier = new ClassifierUnit(characteristic, vocabulary, nGramStrategy);
            classifier.addObserver(logWindow);
            classifiers.add(classifier);
        }
    }

    protected  void trainAndSaveClassifiers(List<ClassifiableText> classifiableTextForTrain);

    protected  void checkClassifiersAccuracy();
}
