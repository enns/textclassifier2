package org.ripreal.textclassifier2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.data.reactive.repos.CharacteristicRepo;
import org.ripreal.textclassifier2.data.reactive.repos.CharacteristicValueRepo;
import org.ripreal.textclassifier2.data.reactive.repos.ClassifiableTextRepo;
import org.ripreal.textclassifier2.model.CharactValuePair;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassifiableTextService {
    final ClassifiableTextRepo textRepo;
    final CharacteristicRepo charRepo;
    final CharacteristicValueRepo charValRepo;

    public Flux<ClassifiableText> findAll() {
        return textRepo
            .findAll()
            .doOnNext((item) -> log.info("Found {}", item));
    }

    @Transactional
    public Flux<ClassifiableText> saveAll(Flux<ClassifiableText> texts) {
        return textRepo
            .saveAll(texts)
            .doOnNext((item) -> {
                log.info("Saved {}", item);
            });
    }

    @Transactional
    public Mono<Void> deleteAll() {
        charValRepo.deleteAll().block();
        charRepo.deleteAll().block();
        return textRepo.deleteAll();
    }

    @Transactional
    public Flux<ClassifiableText> saveTextsWithCharacteristics(List<ClassifiableText> texts) {

        Set<Characteristic> characteristics =
            texts
            .stream()
            .flatMap(item -> item.getCharacteristics().stream())
            .map(CharactValuePair::getKey)
            .distinct()
            .collect(Collectors.toSet());

        Set<CharacteristicValue> characteristicVals = characteristics
            .stream()
            .flatMap(item -> item.getPossibleValues().stream())
            .distinct()
            .collect(Collectors.toSet());

        return charValRepo
            .saveAll(characteristicVals)
            .doOnNext(item -> log.info("Saved {}", item))
            .thenMany(charRepo.saveAll(characteristics))
            .doOnNext(item -> log.info("Saved {}", item))
            .thenMany(textRepo.saveAll(texts))
            .doOnNext(item -> log.info("Saved {}", item));

    }
}