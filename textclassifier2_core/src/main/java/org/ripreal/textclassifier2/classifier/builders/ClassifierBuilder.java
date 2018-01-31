package org.ripreal.textclassifier2.classifier.builders;

import lombok.NonNull;
import org.ripreal.textclassifier2.classifier.textreaders.ClassifiableReader;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.ngram.NGramStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

// BUILDER + FACTORY
public class ClassifierBuilder {

    private final List<ClassifierUnit> units;
    private final List<Consumer<String>> listeners;
    private final Supplier<List<ClassifiableText>> supplier;

    public static ClassifierBuilder fromSupplier(@NonNull Supplier<List<ClassifiableText>> supplier) {
        return new ClassifierBuilder(supplier);
    }

    private ClassifierBuilder(Supplier<List<ClassifiableText>> supplier) {
        this.units = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.supplier = supplier;
    }

    public void addNeroClassifier(Characteristic characteristic) {
        addNeroClassifier(characteristic, new ArrayList<>(), NGramStrategy.getNGramStrategy(""));
    }

    public void addNeroClassifier(Characteristic characteristic, List<VocabularyWord> vocab, NGramStrategy nGramStrategy) {

    }

    public void addNeroClassifier(ClassifierUnit unit) {

    }

    public void build() {

        List<ClassifiableText> classifiableTexts = supplier.get();
        List<VocabularyWord> vocabulary = getVocabulary(classifiableTexts);
        List<Characteristic> characteristics = getCharacteristics(classifiableTexts);

        createClassifiers(characteristics, vocabulary);

    }


    public List<VocabularyWord> getVocabulary(List<ClassifiableText> classifiableTexts) {
        return null;
    }

    public List<Characteristic> getCharacteristics(List<ClassifiableText> classifiableTexts) {
        return null;
    }

    public ClassifierBuilder addNeroClassifer(Characteristic characteristic, List<> NGramStrategy ngram) {
        ClassifierUnit unit = new NeroClassifierUnit(
                characteristic,
                this.reader.toClassifiableTexts());
        unit
        characteristics.add(n{
        })
        return this;
    }

    public ClassifierBuilder subscribe(Consumer<String> consumer) {
        this.listeners.add(consumer);
        return this;
    }

    public static Classifier buildClassifier() {
        return new Classifier();
    }

    private void createClassifiersUnits() throws IncorrectMethodCallException {
        for (Map.Entry<Characteristic, Class<? extends ClassifierUnit>> characteristicEntry : classifiableCharacteristics.entrySet()) {
            try {
                characteristicEntry.getValue().newInstance();
                ClassifierUnit classifier = new ClassifierUnit(characteristic, vocabulary, nGramStrategy);
                classifier.addObserver(logWindow);
                classifiers.add(classifier);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IncorrectMethodCallException("" +
                        "Cannot dynamically create instance of ClassifierUnit class. " +
                        "Is the ClassifierUnit class exists?");
            }
        }
    }

    private void createClassifiers(List<Characteristic> characteristics, List<VocabularyWord> vocabulary) {
        for (Characteristic characteristic : characteristics) {
            ClassifierUnit classifier = new ClassifierUnit(characteristic, vocabulary, nGramStrategy);
            classifier.addObserver(logWindow);
            classifiers.add(classifier);
        }
    }

}
