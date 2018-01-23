package org.ripreal.textclassifier2.service;

import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DataService<T> {
    Flux<T> saveAll(List<T> entities);

    Flux<T> findAll();

    Mono<T> findById(String id);

    Flux<Void> deleteAll();

}
