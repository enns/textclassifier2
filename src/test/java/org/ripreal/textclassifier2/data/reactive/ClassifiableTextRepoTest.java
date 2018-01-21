package org.ripreal.textclassifier2.data.reactive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ripreal.textclassifier2.App;
import org.ripreal.textclassifier2.data.reactive.repos.CharacteristicRepo;
import org.ripreal.textclassifier2.data.reactive.repos.CharacteristicValueRepo;
import org.ripreal.textclassifier2.data.reactive.repos.ClassifiableTextRepo;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.service.ClassifiableTextService;
import org.ripreal.textclassifier2.testdata.ClassifiableTextTestDataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {App.class})
public class ClassifiableTextRepoTest {
    @Autowired
    ClassifiableTextService service;

    @Before
    public void setUp() {
        service.deleteAll().blockLast();
    }

    @Test
    public void testCRUD() throws InterruptedException {

        //TEST CREATE
        List<ClassifiableText> data = ClassifiableTextTestDataHelper.getTextTestData();
        Iterable<ClassifiableText> savedData = service.saveAll(data).toIterable();
        assertEquals(savedData.iterator().hasNext(), true);

        //TEST READ
        for (ClassifiableText entry : savedData) {
            ClassifiableText text = data.iterator().next();
            assertEquals(entry.getCharacteristics().size(), text.getCharacteristics().size());
        }
        /*
        long textAmount = data.get(data.size() - 1).getCharacteristics().size();
        assertEquals(charRepo.findAll().count().block().longValue(), textAmount);

        long charValAmount = data.get(data.size() - 1).getCharacteristics().stream().
                mapToLong((charact) -> charact.getKey().getPossibleValues().size()).sum();

        assertEquals(charValRepo.findAll().count().block().longValue(), charValAmount);

        //TEST DELETE
        textRepo.deleteAll().block();
        assertEquals(textRepo.findAll().toIterable().iterator().hasNext(), false);
        */
    }
}