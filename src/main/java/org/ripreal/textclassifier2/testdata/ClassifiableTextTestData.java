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

        // fill characteristic

        Characteristic characteristic1 = new Characteristic("Отдел");

        Set<CharacteristicValue> vals = new HashSet<>();
        CharacteristicValue auto = new CharacteristicValue("Автосалон", 0);
        vals.add(auto);
        CharacteristicValue agro = new CharacteristicValue("Агро", 1);
        vals.add(agro);
        CharacteristicValue logistic = new CharacteristicValue("Логистика", 1);
        vals.add(logistic);

        characteristic1.setPossibleValues(vals);

        Characteristic characteristic2 = new Characteristic("Тип");

        Set<CharacteristicValue> vals2 = new HashSet<>();
        CharacteristicValue it = new CharacteristicValue("Техподдержка", 0);
        vals2.add(it);
        CharacteristicValue dev = new CharacteristicValue("Разработчики", 1);
        vals2.add(dev);
        CharacteristicValue analytics = new CharacteristicValue("Методисты", 1);
        vals2.add(analytics);

        characteristic2.setPossibleValues(vals2);

        ClassifiableText text1 = new ClassifiableText("Требуется починить телефон");
        text1.setCharacteristics(new HashSet<CharactValuePair>(Arrays.asList(
            new CharactValuePair(characteristic2, it),
            new CharactValuePair(characteristic1, auto)
        )));

        ClassifiableText text2 = new ClassifiableText("Требуется починить заказы клиента в 1с");
        text2.setCharacteristics(new HashSet<CharactValuePair>(Arrays.asList(
            new CharactValuePair(characteristic2, dev),
            new CharactValuePair(characteristic1, agro)
        )));

        return args -> {
            charValRepo.deleteAll().block();
            charRepo.deleteAll().block();
            textRepo.deleteAll()
            .thenMany(Flux.just(text1, text2).flatMap(textRepo::save))
                    .subscribe(null, null, () ->
                            textRepo.findAll().subscribe(movie -> log.info("\nwritten to db: {}", movie)));
        };
    }
}
