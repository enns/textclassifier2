package org.ripreal.textclassifier2.storage.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
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

import java.util.List;

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
    public Flux<MongoClassifiableText> saveAllTexts(List<MongoClassifiableText> texts) {
        return textRepo.saveAll(texts);
    }

    @Override
    public Flux<MongoVocabularyWord> saveAllVocabulary(List<MongoVocabularyWord> vocabulary) {
        return vocabRepo.saveAll(vocabulary);
    }

    @Override
    public Flux<MongoCharacteristic> findAllCharacteristics() {
        return charRepo.findAll();
    }

    @Override
    public Mono<MongoCharacteristic> findCharacteristicByName(String name) {
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