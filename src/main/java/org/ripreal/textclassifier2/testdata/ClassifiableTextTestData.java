package org.ripreal.textclassifier2.testdata;

import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.data.reactive.repos.VocabularyWordRepo;
import org.ripreal.textclassifier2.service.ClassifiableTextService;
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
    public CommandLineRunner init(ClassifiableTextService textService,
                                  final VocabularyWordRepo vocabRepo) {
        return args -> {
            textService
                .deleteAll()
                .thenMany(textService.saveTextsWithCharacteristics(ClassifiableTextTestDataHelper.getTextTestData()))
                .subscribe(null, null, () ->
                            textService.findAll().subscribe(text -> log.info("\nwritten to db: {}", text)));
            vocabRepo
                .deleteAll()
                .thenMany(Flux.fromIterable(ClassifiableTextTestDataHelper.getVocabTestData()).flatMap(vocabRepo::save))
                .subscribe(null, null, () ->
                            vocabRepo.findAll().subscribe(word -> log.info("\nwritten to db: {}", word)));
        };
    }
}
