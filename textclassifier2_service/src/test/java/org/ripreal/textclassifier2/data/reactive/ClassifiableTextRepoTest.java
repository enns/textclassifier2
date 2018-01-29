package org.ripreal.textclassifier2.data.reactive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ripreal.textclassifier2.App;
import org.ripreal.textclassifier2.service.ClassifiableTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

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


        //TEST READ

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