package org.ripreal.textclassifier2.service;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.data.queries.QuerySpecification;
import org.ripreal.textclassifier2.data.reactive.repos.CharacteristicRepo;
import org.ripreal.textclassifier2.data.reactive.repos.CharacteristicValueRepo;
import org.ripreal.textclassifier2.data.reactive.repos.ClassifiableTextRepo;
import org.ripreal.textclassifier2.data.entries.PersistClassifiableText;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassifiableTextService implements DataService<PersistClassifiableText> {

    private final ClassifiableTextRepo textRepo;
    private final CharacteristicRepo charRepo;
    private final CharacteristicValueRepo charValRepo;
    private final MongoOperations mongoOperations;

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
    public List<PersistClassifiableText> query(QuerySpecification spec) {
        throw new Error("not implemented!");
    }

}