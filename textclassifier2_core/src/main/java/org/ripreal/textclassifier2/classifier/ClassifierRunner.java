package org.ripreal.textclassifier2.classifier;

import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;

import java.util.List;

// FACADE
public abstract class AbstractClassifierRunner {

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
