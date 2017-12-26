package org.ripreal.textclassifier2.testdata;

import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.data.reactive.CharacteristicValueRepo;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.data.reactive.CharacteristicRepo;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
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
public class ClassifiableTextTestData {

    @Bean
    @Transactional
    public CommandLineRunner init(final CharacteristicRepo repository, final CharacteristicValueRepo valRepository) {

        // fill characteristic

        Characteristic characteristic1 = new Characteristic("Отдел");

        Set<CharacteristicValue> vals = new HashSet<>();
        CharacteristicValue auto = new CharacteristicValue("Автосалон", 0);
        vals.add(auto);
        CharacteristicValue agro = new CharacteristicValue("Агро", 1)
        vals.add(agro);
        CharacteristicValue logistic = new CharacteristicValue("Логистика", 1)
        vals.add(logistic);

        characteristic1.setPossibleValues(vals);

        Characteristic characteristic2 = new Characteristic("Тип");

        Set<CharacteristicValue> vals2 = new HashSet<>();
        vals.add(new CharacteristicValue("Техподдержка", 0));
        vals.add(new CharacteristicValue("Разработчики", 1));
        vals.add(new CharacteristicValue("Методисты", 1));

        characteristic2.setPossibleValues(vals2);

        // fill classifiable text

        ClassifiableText text1 = new ClassifiableText("Требуется починить телефон");

        return args -> {
            repository.deleteAll()
                    .thenMany(Flux.just(characteristic1, characteristic2).flatMap(repository::save))
                    .subscribe(null, null, () ->
                        repository.findAll().subscribe(movie -> log.info("\n{}", movie)));
        };
    }
}
