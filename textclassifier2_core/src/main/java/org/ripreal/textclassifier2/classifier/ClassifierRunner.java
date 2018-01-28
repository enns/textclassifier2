package org.ripreal.textclassifier2.classifier;

import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;

import java.util.List;

// TEMPLATE METHOD
// FACADE + COMPOSITE
public class ClassifierRunner {

    public static ClassifierRunner builder() {
        return new ClassifierRunner();
    }

    public ClassifierRunner parseFrom(Object parser) {
        return this;
    }

    public ClassifierRunner saveTo(Object storageProvider) {
        return this;
    }

    public ClassifierRunner setClassifiers(List <Classifier> classifiers) {
        return this;
    }

    public ClassifierRunner subscribe() {
        return this;
    }

    public void run() {
        createStorage();

        // read first sheet from a file
        List<ClassifiableText> classifiableTexts = getClassifiableTexts();

        // create and train classifiers

        createClassifiers(characteristics, vocabulary);
        trainAndSaveClassifiers(classifiableTextForTrain);
        checkClassifiersAccuracy();
    }

    protected abstract void createStorage();

    protected abstract List<ClassifiableText> getClassifiableTexts();


    protected abstract void createClassifiers(List<Characteristic> characteristics, List<VocabularyWord> vocabulary);

    protected abstract void trainAndSaveClassifiers(List<ClassifiableText> classifiableTextForTrain);

    protected abstract void checkClassifiersAccuracy();

}
