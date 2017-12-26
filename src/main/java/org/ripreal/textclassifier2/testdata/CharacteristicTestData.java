package org.ripreal.textclassifier2.testdata;

import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.data.reactive.CharacteristicRepo;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Configuration
public class CharacteristicTestData {

    @Bean
    @Transactional
    public CommandLineRunner init(final CharacteristicRepo repository) {
        Characteristic charact = new Characteristic("отдел4");
        Set<CharacteristicValue> vals = new HashSet<>();
        vals.add(new CharacteristicValue("logistics", charact));
        charact.setPossibleValues(vals);
        return args -> {
            //repository.deleteAll()
                   // .thenMany
                    (Flux.just(charact).flatMap(repository::save))
                    .subscribe(null, null, () ->
                            repository.findAll().subscribe(movie -> log.info("\n{}", movie)));

        };
    }
}
