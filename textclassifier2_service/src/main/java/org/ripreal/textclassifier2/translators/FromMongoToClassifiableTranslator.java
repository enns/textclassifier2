package org.ripreal.textclassifier2.translators;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.actions.ClassifierAction;
import org.ripreal.textclassifier2.data.reactive.queries.FindVocabularyByNgramSpec;
import org.ripreal.textclassifier2.data.entries.PersistCharacteristic;
import org.ripreal.textclassifier2.data.entries.PersistClassifiableText;
import org.ripreal.textclassifier2.data.entries.PersistVocabularyWord;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.service.DataService;
import org.ripreal.textclassifier2.service.decorators.LoggerDataService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FromMongoToClassifiableTranslator implements ClassifiableTranslator {

    @NonNull
    ClassifiableFactory factory;

    @NonNull
    private final DataService<PersistClassifiableText> textService;

    @NonNull
    private final DataService<PersistCharacteristic> characteristicService;

    @NonNull
    private final DataService<PersistVocabularyWord> vocabularyService;

    public FromMongoToClassifiableTranslator(ClassifiableFactory factory,
                                             DataService<PersistClassifiableText> textService,
                                             DataService<PersistCharacteristic> characteristicService,
                                             DataService<PersistVocabularyWord> vocabularyService) {

        this.factory = factory;
        this.textService = new LoggerDataService<>(textService);
        this.characteristicService = new LoggerDataService<>(characteristicService);
        this.vocabularyService = new LoggerDataService<>(vocabularyService);
    }


    @Override
    public ClassifiableFactory getCharacteristicFactory() {
        return factory;
    }

    @Override
    public List<ClassifiableText> toClassifiableTexts() {
        return textService.findAll().toStream().collect(Collectors.toList());
    }

    @Override
    public Set<Characteristic> toCharacteristics() {
        return characteristicService.findAll().toStream().collect(Collectors.toSet());
    }

    @Override
    public List<VocabularyWord> toVocabulary(NGramStrategy ngram) {
        return vocabularyService.query(new FindVocabularyByNgramSpec(ngram.getNGramType()))
            .toStream().collect(Collectors.toList());
    }

    @Override
    public void reset() {

    }

    @Override
    public void subscribe(ClassifierAction action) {

    }

    @Override
    public void dispatch(String text) {

    }
}
