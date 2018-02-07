package org.ripreal.textclassifier2.service.decorators;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class LoggerDataService<T> implements DataService<T> {

    @Autowired
    private final DataService<T> service;

    @Override
    public Flux<T> saveAll(List<T> entities) {
        return service.saveAll(entities)
                .doOnRequest((request) -> log.info("start request"))
                .doOnNext((item) -> log.info("written to db {}", item));
    }

    @Override
    public Flux<T> findAll() {
        return service.findAll()
                .doOnRequest((request) -> log.info("start request"))
                .doOnNext((item) -> log.info("found {}", item));
    }

    @Override
    public Mono<T> findById(String id) {
        return service.findById(id)
                .doOnRequest((request) -> log.info("start request"))
                .doOnNext((item) -> log.info("found by id {}", item));
    }

    @Override
    public Flux<Void> deleteAll() {
        return service.deleteAll()
                .doOnRequest((request) -> log.info("start request"))
                .doOnNext((item) -> log.info("found {}", item));
    }
}
