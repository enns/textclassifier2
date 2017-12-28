package org.ripreal.textclassifier2.data.reactive;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.ripreal.textclassifier2.App;
import org.ripreal.textclassifier2.model.CharactValuePair;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {App.class})
public class ClassifiableTextRepoTest {
    @Autowired
    ClassifiableTextRepo textRepo;
    @Autowired
    CharacteristicRepo charRepo;
    @Autowired
    CharacteristicValueRepo charValRepo;

    @Before
    public void setUp() {
        textRepo.deleteAll().block();
        charRepo.deleteAll().block();
        charValRepo.deleteAll().block();
    }

    @Test
    public void testCRUD() {

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

        Flux.just(text1 , text2).flatMap(textRepo::save).blockLast();
        Iterable<ClassifiableText> entries = textRepo.findAll().toIterable();

        assertEquals(entries.iterator().hasNext(), true);
        for(ClassifiableText entry : entries) {
            assertEquals(entry.getCharacteristics().size(), text1.getCharacteristics().size());
        }
        assertEquals(charRepo.findAll().count().block().longValue(), 2l);
        assertEquals(charValRepo.findAll().count().block().longValue(), vals.size() + vals2.size());

    }

}