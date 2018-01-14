package org.ripreal.textclassifier2.testdata;

import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.data.reactive.repos.CharacteristicValueRepo;
import org.ripreal.textclassifier2.data.reactive.repos.ClassifiableTextRepo;
import org.ripreal.textclassifier2.data.reactive.repos.VocabularyWordRepo;
import org.ripreal.textclassifier2.data.reactive.repos.CharacteristicRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Profile("test")
@Slf4j
@Configuration()
public class ClassifiableTextTestData {

    @Bean
    @Transactional
    public CommandLineRunner init(final ClassifiableTextRepo textRepo,
                                  final CharacteristicRepo charRepo,
                                  final CharacteristicValueRepo charValRepo,
                                  final VocabularyWordRepo vocabRepo) {
        return args -> {
            charValRepo.deleteAll().block();
            charRepo.deleteAll().block();
            textRepo.deleteAll()
            .thenMany(Flux.fromIterable(Helper.getTextTestData()).flatMap(textRepo::save))
            .subscribe(null, null, () ->
                        textRepo.findAll().subscribe(text -> log.info("\nwritten to db: {}", text)));

            vocabRepo.deleteAll()
                    .thenMany(Flux.fromIterable(Helper.getVocabTestData()).flatMap(vocabRepo::save))
                    .subscribe(null, null, () ->
                            vocabRepo.findAll().subscribe(word -> log.info("\nwritten to db: {}", word)));
        };
    }
}
