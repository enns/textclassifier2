package org.ripreal.textclassifier2.testdata;

import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.data.reactive.ClassifiableTextRepo;
import org.ripreal.textclassifier2.data.reactive.VocabularyWordRepo;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Slf4j
@Configuration
public class VocabularyWordTestData {

    @Bean
    @Transactional
    public CommandLineRunner init(final VocabularyWordRepo repo) {
        return args -> {
            repo.deleteAll()
                .thenMany(Flux.fromIterable(Helper.getVocabTestData()).flatMap(repo::save))
                .subscribe(null, null, () ->
                    repo.findAll().subscribe(word -> log.info("\nwritten to db: {}", word)));
        };
    }
}
