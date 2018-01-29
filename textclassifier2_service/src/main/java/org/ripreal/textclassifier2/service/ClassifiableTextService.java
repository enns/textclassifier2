package org.ripreal.textclassifier2.service;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.data.reactive.repos.CharacteristicRepo;
import org.ripreal.textclassifier2.data.reactive.repos.CharacteristicValueRepo;
import org.ripreal.textclassifier2.data.reactive.repos.ClassifiableTextRepo;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassifiableTextService implements DataService<ClassifiableText> {
    private final ClassifiableTextRepo textRepo;
    private final CharacteristicRepo charRepo;
    private final CharacteristicValueRepo charValRepo;

    @Override
    public Flux<ClassifiableText> findAll() {
        return textRepo.findAll();
    }

    @Override
    public Mono<ClassifiableText> findById(String id) {
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
    public Flux<ClassifiableText> saveAll(List<ClassifiableText> texts) {
        return textRepo.saveAll(texts);
    }

}