package org.ripreal.textclassifier2.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ripreal.textclassifier2.App;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.testdata.ClassifiableTextTestDataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {App.class})
public abstract class AbstractServiceTest<T> {

    private DataService<T> service;

    @Before
    public void setUp() {
        service = createDataService();
        service
            .deleteAll()
            .thenMany(service.saveAll(getTestData()))
            .blockLast();
    }

    protected abstract DataService<T> createDataService();

    protected abstract List<T> getTestData();

    @Test
    public void saveAll() throws Exception {
        //TEST CREATE
        /*
        List<T> data = getTestData();
        Iterable<T> savedData = service.saveAll(data).toIterable();
        assertEquals(savedData.iterator().hasNext(), true);

        for (T savedEntry : savedData) {
            assertEquals(data.iterator().next(), savedEntry);
        }
        */
    }
}
