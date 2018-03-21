package org.ripreal.textclassifier2.service;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.data.queries.QuerySpecification;
import org.ripreal.textclassifier2.data.reactive.repos.VocabularyWordRepo;
import org.ripreal.textclassifier2.data.entries.PersistVocabularyWord;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VocabularyWordService implements DataService<PersistVocabularyWord> {

    private final VocabularyWordRepo vocabRepo;
    private final MongoOperations mongoOperations;

    @Override
    public Flux<PersistVocabularyWord> saveAll(List<PersistVocabularyWord> entities) {
        return vocabRepo.saveAll(entities);
    }

    @Override
    public Flux<PersistVocabularyWord> findAll() {
        return vocabRepo.findAll();
    }

    @Override
    public Mono<PersistVocabularyWord> findById(String id) {
        return vocabRepo.findById(id);
    }

    @Override
    public Flux<Void> deleteAll() {
        return vocabRepo.deleteAll().thenMany(Flux.empty());
    }

    @Override
    public List<PersistVocabularyWord> query(QuerySpecification spec) {
        return mongoOperations.find(
                spec.get(),
                PersistVocabularyWord.class,
                "persistVocabularyWord");
    }
}
