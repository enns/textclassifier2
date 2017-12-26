package org.ripreal.textclassifier2.service;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.data.reactive.ClassifiableTextRepo;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ClassifiableTextService {
    final ClassifiableTextRepo repository;

    public Flux<ClassifiableText> findAll() {
        return repository.findAll();
    }
}