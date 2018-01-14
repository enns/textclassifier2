package org.ripreal.textclassifier2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.data.reactive.repos.CharacteristicRepo;
import org.ripreal.textclassifier2.model.Characteristic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacteristicService {
    final CharacteristicRepo repository;

    public Flux<Characteristic> findAll() {
        return repository
            .findAll()
            .doOnNext((item) -> log.info("Found {}", item));
    }

    public Mono<Characteristic> findByName(String name) {
        return repository
            .findById(name)
            .doOnNext((item) -> log.info("Found {}", item));
    }

    public Mono<Characteristic> save(Characteristic characteristic) {
        return repository
            .save(characteristic)
            .doOnNext((item) -> {
                log.info("Saved {}", item);
            });
    }
}
