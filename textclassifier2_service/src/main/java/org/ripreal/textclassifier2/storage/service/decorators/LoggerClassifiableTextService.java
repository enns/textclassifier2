package org.ripreal.textclassifier2.storage.service.decorators;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristicValue;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import org.ripreal.textclassifier2.storage.service.ClassifiableService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class LoggerClassifiableTextService implements ClassifiableService {

    @Autowired
    private final ClassifiableService service;

    @Override
    public Flux<MongoClassifiableText> findAllTexts() {
        return service.findAllTexts()
            .doOnRequest((request) -> log.info("start findAllTexts request"))
            .doOnNext((item) -> log.info("found {}", item));
    }

    @Override
    public Flux<MongoClassifiableText> saveAllTexts(Iterable<MongoClassifiableText> texts) {
        return service.saveAllTexts(texts)
            .doOnRequest((request) -> log.info("start saveAllTexts request"))
            .doOnNext((item) -> log.info("written to db {}", item))
            .doOnError((a1) -> log.error("request error {}", a1))
            .doOnTerminate(() -> log.info("terminated request"));
    }

    @Override
    public Flux<MongoCharacteristic> findAllCharacteristics() {
        return service.findAllCharacteristics()
            .doOnRequest((request) -> log.info("start findAllCharacteristics request"))
            .doOnNext((item) -> log.info("found {}", item));
    }

    @Override
    public Flux<MongoCharacteristicValue> findAllCharacteristicValues() {
        return service.findAllCharacteristicValues()
            .doOnRequest((request) -> log.info("start findAllCharacteristicValues request"))
            .doOnNext((item) -> log.info("found {}", item));
    }

    @Override
    public Mono<MongoCharacteristic> findCharacteristicByName(String name) {
        return service.findCharacteristicByName(name)
            .doOnRequest((request) -> log.info("start findCharacteristicByName request"))
            .doOnNext((item) -> log.info("found by id {}", item));
    }

    @Override
    public Flux<MongoCharacteristicValue> findCharacteristicValuesByCharacteristic(Characteristic characteristic) {
        return service.findAllCharacteristicValues()
                .doOnRequest((request) -> log.info("start findAllCharacteristicValues request"))
                .doOnNext((item) -> log.info("found {}", item));
    }

    @Override
    public Flux<MongoVocabularyWord> findVocabularyByNgram(NGramStrategy ngram) {
        return service.findVocabularyByNgram(ngram)
                .doOnRequest((request) -> log.info("start findVocabularyByNgram request with parameter {}", ngram))
                .doOnNext((item) -> log.info("found by id {}", item));
    }

    @Override
    public Flux<MongoVocabularyWord> saveAllVocabulary(Iterable<MongoVocabularyWord> vocabulary) {
        return service.saveAllVocabulary(vocabulary)
            .doOnRequest((request) -> log.info("start saveAllVocabulary request"))
            .doOnNext((item) -> log.info("written to db {}", item))
            .doOnError((a1) -> log.error("request error {}", a1))
            .doOnTerminate(() -> log.info("terminated request"));
    }

    @Override
    public Flux<Void> deleteAll() {
        return service.deleteAll()
            .doOnRequest((request) -> log.info("start request"))
            .doOnNext((item) -> log.info("found {}", item));
    }

}
