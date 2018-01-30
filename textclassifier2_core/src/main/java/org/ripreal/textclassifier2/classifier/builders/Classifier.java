package org.ripreal.textclassifier2.classifier.builders;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.classifier.ClassifierUnit;
import org.ripreal.textclassifier2.classifier.textreaders.ClassifiableReader;
import org.ripreal.textclassifier2.classifier.textreaders.DataSourceException;
import org.ripreal.textclassifier2.classifier.textreaders.EmptySheetException;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

// FACADE + BRIDGE
@RequiredArgsConstructor
class Classifier {

    private  textsForTraining = new ArrayList<>();
    private List<Consumer<String>> listeners = new ArrayList<>();
    private final ClassifiableReader textReader;

    public NeroClassifierBuilder setTextsForTraining(List<ClassifiableText> textsForTraining) {
        this.textsForTraining = textsForTraining;
        return this;
    }

    public NeroClassifierBuilder subscribe(Consumer<String> consumer) {
        this.listeners.add(consumer);
        return this;
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
