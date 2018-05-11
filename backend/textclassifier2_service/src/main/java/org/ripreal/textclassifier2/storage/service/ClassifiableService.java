package org.ripreal.textclassifier2.storage.service;

import lombok.NonNull;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristicValue;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

public interface ClassifiableService {

    Flux<MongoClassifiableText> saveAllTexts(Iterable<MongoClassifiableText> texts);

    Flux<MongoCharacteristicValue> saveAllCharacteristics(Set<MongoCharacteristic> texts);

    Flux<MongoClassifiableText> findAllTexts();

    Flux<MongoCharacteristic> findAllCharacteristics();

    Flux<MongoCharacteristicValue> findAllCharacteristicValues();

    Mono<MongoCharacteristic> findCharacteristicByName(String name);

    Flux<MongoCharacteristicValue> findCharacteristicValuesByCharacteristic(MongoCharacteristic MongoCharacteristic);

    Flux<MongoVocabularyWord> saveAllVocabulary(Iterable<MongoVocabularyWord> vocabulary);

    Flux<MongoVocabularyWord> findVocabularyByNgram(@NonNull NGramStrategy ngram);

    Flux<Void> deleteAll();


}
