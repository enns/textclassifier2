package org.ripreal.textclassifier2.classifier.builders;

import org.ripreal.textclassifier2.classifier.ClassifierUnit;
import org.ripreal.textclassifier2.classifier.NeroClassifierUnit;
import org.ripreal.textclassifier2.classifier.textreaders.ClassifiableReader;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.ngram.NGramStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

// BUILDER + FACTORY
public class ClassifierBuilder {

    private final List<ClassifierUnit> characteristics = new ArrayList<>();
    private final List<Consumer<String>> listeners = new ArrayList<>();
    private ClassifiableReader reader;

    public static ClassifierBuilder builder() {
        return new ClassifierBuilder();
    }

    public void setTextReader(ClassifiableReader reader) {
        this.reader = reader;
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
