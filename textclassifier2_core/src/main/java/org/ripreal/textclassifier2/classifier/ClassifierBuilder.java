package org.ripreal.textclassifier2.classifier;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import reactor.util.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

// FACADE + BUILDER
@NoArgsConstructor
public class ClassifierBuilder {

    private List<Classifier> classifiers = new ArrayList<>();
    private List<Consumer<String>> listeners = new ArrayList<>();
    private Supplier<List<ClassifiableText>> textSupplier;

    public static ClassifierBuilder builder() {
        return new ClassifierBuilder();
    }

    public ClassifierBuilder setTextProvider(Supplier<List<ClassifiableText>> supplier) {
        this.textSupplier = supplier;
        return this;
    }

    public ClassifierBuilder subscribe(Consumer<String> consumer) {
        this.listeners.add(consumer);
        return this;
    }

    public ClassifierBuilder addClassifier(Classifier classifier) {
        classifiers.add(classifier);
        return this;
    }

    public void train() {

        // read first sheet from a file
        List<ClassifiableText> classifiableTexts = textSupplier.get();

        // create and train classifiers

        createClassifiers(characteristics, vocabulary);
        trainAndSaveClassifiers(classifiableTextForTrain);
        checkClassifiersAccuracy();
    }

    private void createClassifiers(List<Characteristic> characteristics, List<VocabularyWord> vocabulary) {
        for (Characteristic characteristic : characteristics) {
            Classifier classifier = new Classifier(characteristic, vocabulary, nGramStrategy);
            classifier.addObserver(logWindow);
            classifiers.add(classifier);
        }
    }

    protected  void trainAndSaveClassifiers(List<ClassifiableText> classifiableTextForTrain);

    protected  void checkClassifiersAccuracy();

}
