package org.ripreal.textclassifier2.testdata;

import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.data.reactive.CharacteristicValueRepo;
import org.ripreal.textclassifier2.data.reactive.ClassifiableTextRepo;
import org.ripreal.textclassifier2.model.CharactValuePair;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.data.reactive.CharacteristicRepo;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.*;

@Slf4j
@Configuration
public class ClassifiableTextTestData {

    @Bean
    @Transactional
    public CommandLineRunner init(final ClassifiableTextRepo textRepo,
                                  final CharacteristicRepo charRepo,
                                  final CharacteristicValueRepo charValRepo) {
        return args -> {
            charValRepo.deleteAll().block();
            charRepo.deleteAll().block();
            textRepo.deleteAll()
            .thenMany(Flux.fromIterable(Helper.getTextTestData()).flatMap(textRepo::save))
            .subscribe(null, null, () ->
                        textRepo.findAll().subscribe(text -> log.info("\nwritten to db: {}", text)));
        };
    }
}
