package org.ripreal.textclassifier2.classifier;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.CharacteristicUtils;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.ngram.VocabularyBuilder;
import org.ripreal.textclassifier2.testdata.ExcelFileReader;
import org.ripreal.textclassifier2.testdata.TestDataReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// BUILDER + COMPOSITE
@Slf4j
@RequiredArgsConstructor
public final class ClassifierBuilder {

    @NonNull
    private final TestDataReader reader;
    @NonNull
    private final ClassifiableFactory textFactory;
    @NonNull
    private final List<ClassifierUnitProxy> classifierUnits = new ArrayList<>();

    private final int AMOUNT_OF_TEXTS_FOR_CHECKING = 5;

    // CONSTRUCTORS

    public static ClassifierBuilder fromExcel(File file, ClassifiableFactory factory) {
        return new ClassifierBuilder(new ExcelFileReader(file, 1, factory), factory);
    }

    public static ClassifierBuilder fromReader(TestDataReader reader, ClassifiableFactory factory) {
        return new ClassifierBuilder(reader, factory);
    }

    // CLIENT SECTION

    public ClassifierBuilder addNeroClassifierUnit(@NonNull String characteristicName, @NonNull NGramStrategy nGramStrategy) {
        addNeroClassifierUnit(null, characteristicName, null, nGramStrategy);
        return this;
    }

    public ClassifierBuilder addNeroClassifierUnit(File trainedClassifier, @NonNull String characteristicName, List<VocabularyWord> vocabulary, @NonNull NGramStrategy nGramStrategy) {
        classifierUnits.add(
                new ClassifierUnitProxy(
                        NeroClassifierUnit::new,
                        trainedClassifier,
                        nGramStrategy,
                        vocabulary,
                        textFactory.newCharacteristic(characteristicName)
                ));
        return this;
    }

    public Classifier build() throws IOException {
        if (!initialized()) {
            throw new IllegalArgumentException("Error. No classifier units were specified!");
        }
        List<ClassifierUnit> units = buildClassifiers();
        shutDownClassifiers(units);
        return new Classifier(units);
    }

    // INNER SECTION

    private List<ClassifierUnit> buildClassifiers() throws IOException {

        TestDataReader.ClassifiableData data = reader.readAll();

        Set<Characteristic> characteristics = data.getCharacteristics();

        List<ClassifierUnit> units = new ArrayList<>();
        for (ClassifierUnitProxy proxy : classifierUnits) {

            proxy.setVocabulary(new VocabularyBuilder(proxy.getNGramStrategy()).getVocabulary(
                data.getClassifiableTexts(), textFactory));

            proxy.setCharacteristic(
                    CharacteristicUtils.findByValue(
                            characteristics,
                            proxy.getCharacteristic().getName(),
                            textFactory::newCharacteristic)
            );

            ClassifierUnit unit = proxy.get();

            unit.build(data.getClassifiableTexts());

            units.add(unit);
        }

        return units;
    }

    private boolean initialized() {
        return !(reader == null || classifierUnits.size() == 0);
    }

    private void shutDownClassifiers(List<ClassifierUnit> units) {
        for (ClassifierUnit classifier : units) {
            classifier.shutdown();
        }
    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    class ClassifierUnitProxy {
        private final ClassifierUnitSupplier supplier;
        @Getter
        private final File trainedClassifier;
        @Getter
        private final NGramStrategy nGramStrategy;
        @Getter
        @Setter
        private List<VocabularyWord> vocabulary;
        @Getter
        @Setter
        private Characteristic characteristic;

        public ClassifierUnit get() {
            return supplier.get(trainedClassifier, characteristic, vocabulary, nGramStrategy);
        }

        ;
    }

    @FunctionalInterface
    interface ClassifierUnitSupplier {
        ClassifierUnit get(File trainedClassifier, Characteristic characteristic, List<VocabularyWord> vocabulary, NGramStrategy nGramStrategy);
    }
}

