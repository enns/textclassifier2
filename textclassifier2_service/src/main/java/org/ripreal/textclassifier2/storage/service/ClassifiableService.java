package org.ripreal.textclassifier2.storage.service;

import lombok.NonNull;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ClassifiableService {

    Flux<MongoClassifiableText> saveAllTexts(List<MongoClassifiableText> texts);

    Flux<MongoClassifiableText> findAllTexts();

    Flux<MongoCharacteristic> findAllCharacteristics();

    Mono<MongoCharacteristic> findCharacteristicByName(String name);

    Flux<MongoVocabularyWord> saveAllVocabulary(List<MongoVocabularyWord> vocabulary);

    Flux<MongoVocabularyWord> findVocabularyByNgram(@NonNull NGramStrategy ngram);

    Flux<Void> deleteAll();


}
