package org.ripreal.textclassifier2.storage.translators;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import org.ripreal.textclassifier2.storage.data.reactive.specifications.FindVocabularyByNgramMongoSpec;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.storage.service.ClassifiableService;
import org.ripreal.textclassifier2.storage.service.decorators.LoggerClassifiableTextService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class MongoClassifiableTranslator implements ClassifiableTranslator{

    @NonNull
    private final ClassifiableFactory factory;

    @NonNull
    private final ClassifiableService textService;

    public MongoClassifiableTranslator(ClassifiableFactory factory, ClassifiableService textService) {
        this.factory = factory;
        this.textService = new LoggerClassifiableTextService(textService);
    }


    @Override
    public ClassifiableFactory getCharacteristicFactory() {
        return factory;
    }

    @Override
    public List<ClassifiableText> toClassifiableTexts() {
        return textService.findAllTexts().toStream().collect(Collectors.toList());
    }

    @Override
    public Set<Characteristic> toCharacteristics() {
        return textService.findAllCharacteristics().toStream().collect(Collectors.toSet());
    }

    @Override
    public List<VocabularyWord> toVocabulary(@NonNull NGramStrategy ngram) {
        return textService.findVocabularyByNgram(ngram)
            .toStream().collect(Collectors.toList());
    }

    @Override
    public void reset() {

    }

}
