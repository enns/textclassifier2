package org.ripreal.textclassifier2.service;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.data.reactive.queries.RepoSpecification;
import org.ripreal.textclassifier2.data.reactive.repos.CharacteristicRepo;
import org.ripreal.textclassifier2.data.reactive.repos.CharacteristicValueRepo;
import org.ripreal.textclassifier2.data.reactive.repos.ClassifiableTextRepo;
import org.ripreal.textclassifier2.data.entries.PersistClassifiableText;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassifiableTextService implements DataService<PersistClassifiableText> {
    private final ClassifiableTextRepo textRepo;
    private final CharacteristicRepo charRepo;
    private final CharacteristicValueRepo charValRepo;

    @Override
    public Flux<PersistClassifiableText> findAll() {
        return textRepo.findAll();
    }

    @Override
    public Mono<PersistClassifiableText> findById(String id) {
        return textRepo.findById(id);
    }

    @Override
    @Transactional
    public Flux<Void> deleteAll() {
        return charValRepo.deleteAll()
                .thenMany(charRepo.deleteAll())
                .thenMany(textRepo.deleteAll());
    }

    @Override
    public Flux<PersistClassifiableText> saveAll(List<PersistClassifiableText> texts) {
        return textRepo.saveAll(texts);
    }

    @Override
    public Flux<PersistClassifiableText> query(RepoSpecification<PersistClassifiableText> spec) {
        return spec.get();
    }

}