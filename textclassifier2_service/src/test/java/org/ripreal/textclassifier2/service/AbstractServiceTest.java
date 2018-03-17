package org.ripreal.textclassifier2.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ripreal.textclassifier2.SpringTestConfig;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public abstract class AbstractServiceTest<T> extends SpringTestConfig {

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
