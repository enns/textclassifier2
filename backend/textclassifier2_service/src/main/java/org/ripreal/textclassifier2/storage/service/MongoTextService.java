package org.ripreal.textclassifier2.storage.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristicValue;
import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import org.ripreal.textclassifier2.storage.data.queries.QuerySpecification;
import org.ripreal.textclassifier2.storage.data.reactive.repos.CharacteristicRepo;
import org.ripreal.textclassifier2.storage.data.reactive.repos.CharacteristicValueRepo;
import org.ripreal.textclassifier2.storage.data.reactive.repos.ClassifiableTextRepo;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.data.reactive.repos.VocabularyWordRepo;
import org.ripreal.textclassifier2.storage.data.reactive.specifications.FindVocabularyByNgramMongoSpec;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MongoTextService implements ClassifiableService {

    private final ClassifiableTextRepo textRepo;
    private final CharacteristicRepo charRepo;
    private final CharacteristicValueRepo charValRepo;
    private final VocabularyWordRepo vocabRepo;

    @Override
    public Flux<MongoClassifiableText> findAllTexts() {
        return textRepo.findAll();
    }

    @Override
    public Flux<MongoClassifiableText> saveAllTexts(@NonNull Iterable<MongoClassifiableText> texts) {
        return textRepo.saveAll(texts);
    }

    @Override
    public Flux<MongoCharacteristicValue> saveAllCharacteristics(Set<MongoCharacteristic> characteristics) {
        Set<MongoCharacteristicValue> rs = characteristics.stream().flatMap((chrs) -> chrs.getPossibleValues().stream())
                .collect
                (Collectors
                .toSet());
        return charValRepo.saveAll(rs);
    }

    public Flux<MongoCharacteristicValue> saveAllCharacteristcValues(@NonNull Iterable<MongoCharacteristicValue> charVals) {
        return charValRepo.saveAll(charVals);
    }

    @Override
    public Flux<MongoVocabularyWord> saveAllVocabulary(@NonNull Iterable<MongoVocabularyWord> vocabulary) {
        return vocabRepo.saveAll(vocabulary);
    }

    public Flux<MongoCharacteristic> findAllCharacteristics() {
        return charValRepo
            .findAll()
            .map((chrVal) -> {
                MongoCharacteristic chr = chrVal.getCharacteristic();
                chr.addPossibleValue(chrVal);
                return chr;
            })
            .collect(HashSet<MongoCharacteristic>::new, (a1, a2) -> {
                a1.add(a2);
                a1.forEach(item -> {
                    if (item.equals(a2))
                        item.getPossibleValues().addAll(a2.getPossibleValues());
                });

            })
            .flatMapIterable((set) -> set);
    }

    public Flux<MongoCharacteristicValue> findCharacteristicValuesByCharacteristic(MongoCharacteristic MongoCharacteristic) {
        return charValRepo.findByCharacteristicName(MongoCharacteristic.getName());
    }

    public Flux<MongoCharacteristicValue> findAllCharacteristicValues() {
        return charValRepo.findAll();
    }

    @Override
    public Mono<MongoCharacteristic> findCharacteristicByName(@NonNull String name) {
        return charRepo.findById(name);
    }

    @Override
    @Transactional
    public Flux<Void> deleteAll() {
        return charValRepo.deleteAll()
                .thenMany(charRepo.deleteAll())
                .thenMany(vocabRepo.deleteAll())
                .thenMany(textRepo.deleteAll());
    }

    @Override
    public Flux<MongoVocabularyWord> findVocabularyByNgram(@NonNull NGramStrategy ngram) {
        return vocabRepo.findByNGram(ngram.getNGramType().toString());
    }

}