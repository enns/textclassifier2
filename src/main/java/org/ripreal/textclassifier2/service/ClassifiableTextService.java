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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
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
    public Flux<Void> deleteAll() {
        return charValRepo.deleteAll()
            .thenMany(charRepo.deleteAll())
            .thenMany(textRepo.deleteAll());
    }

    public Flux<ClassifiableText> saveAll(List<ClassifiableText> texts) {
        return textRepo.saveAll(texts);
    }
    /*
    @Transactional
    public Flux<ClassifiableText> saveAll(List<ClassifiableText> texts) {

        Set<Characteristic> characteristics = texts
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
    */

    // TRASH

    private void saveCharacteristicIfNotExists1(Set<Characteristic> characteristics) {
        for (Characteristic item : characteristics) {
            Example<Characteristic> example = Example.of(item, ExampleMatcher.matching()
                    .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::startsWith));

            Characteristic found = charRepo.findById(item.getName()).block();
            String t = "";
        }

    }

}