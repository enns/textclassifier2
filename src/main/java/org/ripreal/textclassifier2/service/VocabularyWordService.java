package org.ripreal.textclassifier2.service;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.data.reactive.repos.ClassifiableTextRepo;
import org.ripreal.textclassifier2.data.reactive.repos.VocabularyWordRepo;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VocabularyWordService implements DataService<VocabularyWord> {

    private final VocabularyWordRepo vocabRepo;

    @Override
    public Flux<VocabularyWord> saveAll(List<VocabularyWord> entities) {
        return vocabRepo.saveAll(entities);
    }

    @Override
    public Flux<VocabularyWord> findAll() {
        return vocabRepo.findAll();
    }

    @Override
    public Mono<VocabularyWord> findById(String id) {
        return vocabRepo.findById(id);
    }

    @Override
    public Flux<Void> deleteAll() {
        return vocabRepo.deleteAll().thenMany(Flux.empty());
    }
}
