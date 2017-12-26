package org.ripreal.textclassifier2.testdata;

import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.data.reactive.CharacteristicRepo;
import org.ripreal.textclassifier2.data.reactive.ClassifiableTextRepo;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Slf4j
@Configuration
public class ClassifiableTestData {
    /*
    @Bean
    @Transactional
    public CommandLineRunner init(final ClassifiableTextRepo repository) {

        ClassifiableText text = new ClassifiableText("test test");
        return args -> {
            repository.deleteAll()
                    .thenMany(Flux.just(text)
                    .flatMap(repository:: save))
                    .subscribe(null, null, () ->
                            repository.findAll().subscribe(movie -> log.info("\n{}", movie)));

        };
    }
    */
}
