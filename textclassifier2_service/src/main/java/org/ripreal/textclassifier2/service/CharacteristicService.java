package org.ripreal.textclassifier2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.data.reactive.repos.CharacteristicRepo;
import org.ripreal.textclassifier2.model.Characteristic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacteristicService implements DataService<Characteristic> {

    private final CharacteristicRepo repository;

    @Override
    public Flux<Characteristic> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<Void> deleteAll() {
        return repository.deleteAll().thenMany(Flux.empty());
    }

    @Override
    public Mono<Characteristic> findById(String name) {
        return repository.findById(name);
    }

    @Override
    public Flux<Characteristic> saveAll(List<Characteristic> characteristic) {
        return repository.saveAll(characteristic);
    }
}
