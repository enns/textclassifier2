package org.ripreal.textclassificator.testdata;

import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassificator.model.Characteristic;
import org.ripreal.textclassificator.data.reactive.CharacteristicRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Slf4j
@Configuration
public class CharacteristicTestData {

    @Bean
    @Transactional
    public CommandLineRunner init(final CharacteristicRepo repository) {
        Characteristic charact = new Characteristic();
        return args -> {
            repository.deleteAll()
                    .thenMany(Flux.just(charact)
                    .flatMap(repository::save))
                    .subscribe(null, null, () ->
                            repository.findAll().subscribe(movie -> log.info("\n{}", movie)));

        };
    }
}
