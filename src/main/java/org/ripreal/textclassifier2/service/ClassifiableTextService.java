package org.ripreal.textclassifier2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.data.reactive.repos.ClassifiableTextRepo;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassifiableTextService {
    final ClassifiableTextRepo repository;

    public Flux<ClassifiableText> findAll() {
        return repository
            .findAll()
            .doOnNext((item) -> log.info("Found {}", item));
    }

    public Flux<ClassifiableText> saveAll(Flux<ClassifiableText> texts) {
        return repository
            .saveAll(texts)
            .doOnNext((item) -> {
                log.info("Saved {}", item);
            });
    }

    public Mono<ClassifiableText> save(ClassifiableText text) {
        return repository
            .save(text)
            .doOnNext((item) -> {
                log.info("Saved {}", item);
            });
    }
}