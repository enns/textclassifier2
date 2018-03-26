package org.ripreal.textclassifier2.storage.service;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.storage.data.queries.QuerySpecification;
import org.ripreal.textclassifier2.storage.data.reactive.repos.CharacteristicRepo;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacteristicService implements ClassifiableTextService<MongoCharacteristic> {

    private final CharacteristicRepo repository;

    @Override
    public Flux<MongoCharacteristic> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<Void> deleteAll() {
        return repository.deleteAll().thenMany(Flux.empty());
    }

    @Override
    public List<MongoCharacteristic> query(QuerySpecification spec) {
        throw new Error("not implemented!");
    }

    @Override
    public Mono<MongoCharacteristic> findById(String name) {
        return repository.findById(name);
    }

    @Override
    public Flux<MongoCharacteristic> saveAll(List<MongoCharacteristic> characteristic) {
        return repository.saveAll(characteristic);
    }
}
