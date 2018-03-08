package org.ripreal.textclassifier2.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ripreal.textclassifier2.App;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {App.class})
public abstract class AbstractServiceTest<T> {

    @Before
    public void setUp() {
        DataService<T> service = createDataService();
        service.deleteAll().blockLast();
    }

    @After
    public void tearDown() {
        DataService<T> service = createDataService();
        service.deleteAll().blockLast();
    }

    protected abstract DataService<T> createDataService();

    protected abstract List<T> getTestData();

    @Test
    public void testCRUD() throws Exception {

        List<T> testData = getTestData();

        DataService<T> service = createDataService();

        // TEST CRUD

        assertNotNull(service.saveAll(testData).blockLast());

        assertEquals((long) testData.size(), service.findAll().toStream().count());

        assertNull(service.deleteAll().blockLast());

    }
}
