package org.ripreal.textclassifier2.classifier;

import lombok.*;
import org.ripreal.textclassifier2.CharacteristicUtils;
import org.ripreal.textclassifier2.actions.ClassifierAction;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicFactory;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.ngram.VocabularyBuilder;
import org.ripreal.textclassifier2.textreaders.ClassifiableReader;
import org.ripreal.textclassifier2.textreaders.ClassifiableReaderBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

// BUILDER + COMPOSITE
@RequiredArgsConstructor
public final class ClassifierBuilder {

    private final ClassifiableReader reader;

    private final CharacteristicFactory characteristicFactory;

    private final List<ClassifierUnitProxy> classifierUnits = new ArrayList<>();

    private final List<ClassifierAction> listeners = new ArrayList<>();

    private final int AMOUNT_OF_TEXTS_FOR_CHECKING = 5;

    // CONSTRUCTORS

    public static ClassifierBuilder fromReader(@NonNull Function<ClassifiableReaderBuilder, ClassifiableReader> provider,
        @NonNull CharacteristicFactory characteristicFactory) {

        ClassifiableReader reader = provider.apply(ClassifiableReaderBuilder.builder(characteristicFactory));
        ClassifierBuilder classifier = new ClassifierBuilder(reader, characteristicFactory);
        return classifier;
    }

    // CLIENT SECTION

    public ClassifierBuilder addNeroClassifierUnit(@NonNull String characteristicName, @NonNull NGramStrategy nGramStrategy) {
        classifierUnits.add(
            new ClassifierUnitProxy(
                NeroClassifierUnit::new,
                nGramStrategy,
                null,
                characteristicFactory.newCharacteristic(characteristicName)
            ));
        return this;
    }

    public ClassifierBuilder subscribe(@NonNull ClassifierAction action) {
        listeners.add(action);
        reader.subscribe(action);
        return this;
    }

    public Classifier build() {

        if (!initialized()) {
            dispatch("Error. No classifier units were specified!");
            return null;
        }

        List<ClassifierUnit> units = buildClassifiers(reader.toClassifiableTexts());

        shutDownClassifiers(units);

        return new Classifier(units);
    }

    // INNER SECTION

    private List<ClassifierUnit> buildClassifiers(@NonNull List<ClassifiableText> classifiableTexts) {

        Set<Characteristic> characteristics = classifiableTexts.stream()
            .flatMap(text -> text.getCharacteristics().keySet().stream())
            .distinct()
            .collect(Collectors.toSet());


        List<ClassifierUnit> units = new ArrayList<>();
        for (ClassifierUnitProxy proxy: classifierUnits) {

            proxy.setVocabulary(new VocabularyBuilder(
                proxy.getNGramStrategy()).getVocabulary(classifiableTexts, characteristicFactory));

            proxy.setCharacteristic(
                CharacteristicUtils.findByValue(
                    characteristics,
                    proxy.getCharacteristic().getName(),
                    characteristicFactory::newCharacteristic)
            );

            ClassifierUnit unit = proxy.get();
            listeners.forEach(unit::subscribe);

            unit.build(classifiableTexts);

            units.add(unit);
        }
        return units;
    }

    private boolean initialized() {
        return ! (reader == null || classifierUnits.size() == 0);
    }

    private void shutDownClassifiers(List<ClassifierUnit> units) {
        for (ClassifierUnit classifier : units) {
            classifier.shutdown();
        }
    }

    private void dispatch(@NonNull String text) {
        listeners.forEach(action -> action.dispatch(ClassifierAction.EventTypes.CLASSIFIER_EVENT, text));
    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    class ClassifierUnitProxy {
        private final ClassifierUnitSupplier supplier;
        @Getter private final NGramStrategy nGramStrategy;
        @Setter private List<VocabularyWord> vocabulary;
        @Getter @Setter private Characteristic characteristic;

        public ClassifierUnit get() {
            return supplier.get(characteristic, vocabulary, nGramStrategy);
        };
    }

    @FunctionalInterface
    interface ClassifierUnitSupplier {
        ClassifierUnit get(Characteristic characteristic, List<VocabularyWord> vocabulary, NGramStrategy nGramStrategy);
    }
}

