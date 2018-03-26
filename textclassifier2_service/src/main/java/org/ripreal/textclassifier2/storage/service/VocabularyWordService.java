package org.ripreal.textclassifier2.storage.service;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.storage.data.queries.QuerySpecification;
import org.ripreal.textclassifier2.storage.data.reactive.repos.VocabularyWordRepo;
import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VocabularyWordService implements ClassifiableTextService<MongoVocabularyWord> {

    private final VocabularyWordRepo vocabRepo;
    private final MongoOperations mongoOperations;

    @Override
    public Flux<MongoVocabularyWord> saveAll(List<MongoVocabularyWord> entities) {
        return vocabRepo.saveAll(entities);
    }

    @Override
    public Flux<MongoVocabularyWord> findAll() {
        return vocabRepo.findAll();
    }

    @Override
    public Mono<MongoVocabularyWord> findById(String id) {
        return vocabRepo.findById(id);
    }

    @Override
    public Flux<Void> deleteAll() {
        return vocabRepo.deleteAll().thenMany(Flux.empty());
    }

    @Override
    public List<MongoVocabularyWord> query(QuerySpecification spec) {
        return mongoOperations.find(
                spec.get(),
                MongoVocabularyWord.class,
                "persistVocabularyWord");
    }
}
