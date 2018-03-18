package org.ripreal.textclassifier2.service;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.data.queries.QuerySpecification;
import org.ripreal.textclassifier2.data.reactive.repos.CharacteristicRepo;
import org.ripreal.textclassifier2.data.entries.PersistCharacteristic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacteristicService implements DataService<PersistCharacteristic> {

    private final CharacteristicRepo repository;

    @Override
    public Flux<PersistCharacteristic> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<Void> deleteAll() {
        return repository.deleteAll().thenMany(Flux.empty());
    }

    @Override
    public List<PersistCharacteristic> query(QuerySpecification spec) {
        throw new Error("not implemented!");
    }

    @Override
    public Mono<PersistCharacteristic> findById(String name) {
        return repository.findById(name);
    }

    @Override
    public Flux<PersistCharacteristic> saveAll(List<PersistCharacteristic> characteristic) {
        return repository.saveAll(characteristic);
    }
}
