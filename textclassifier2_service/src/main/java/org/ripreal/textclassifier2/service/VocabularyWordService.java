package org.ripreal.textclassifier2.service;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.data.reactive.queries.RepoSpecification;
import org.ripreal.textclassifier2.data.reactive.repos.VocabularyWordRepo;
import org.ripreal.textclassifier2.entries.PersistVocabularyWord;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VocabularyWordService implements DataService<PersistVocabularyWord> {

    private final VocabularyWordRepo vocabRepo;

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
    public Flux<PersistVocabularyWord> query(RepoSpecification<PersistVocabularyWord> spec) {
        return spec.get();
    }
}
