package org.ripreal.textclassifier2.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DataService<T> {
    Flux<T> saveAll(List<T> entities);

    Flux<T> findAll();

    Mono<T> findById(String id);

    Flux<Void> deleteAll();

}
