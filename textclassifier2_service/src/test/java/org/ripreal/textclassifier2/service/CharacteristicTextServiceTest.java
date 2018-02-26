package org.ripreal.textclassifier2.service;

import org.junit.Test;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.service.decorators.LoggerDataService;
import org.ripreal.textclassifier2.testdata.ClassifiableTestData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CharacteristicTextServiceTest extends AbstractServiceTest<Characteristic> {

    @Autowired
    private DataService<Characteristic> service;

    @Override
    protected DataService<Characteristic> createDataService() {
        return new LoggerDataService<>(service);
    }

    @Override
    protected List<Characteristic> getTestData() {
        return ClassifiableTestData.getCharacteristicTestData();
    }

    @Test
    public void testFindById() throws Exception {
        DataService<Characteristic> service = createDataService();
        Characteristic characteristic = service.saveAll(getTestData()).blockLast();
        assertNotNull(service.findById(characteristic.getName()).block());
    }

    @Test
    public void testSavingSameDataTwice() {
        // SAVING SAME DATA TWICE SHOULD NOT CREATE DOUBLES

        List<Characteristic> testData = getTestData();

        service.saveAll(testData).blockLast();

        long actualSize = service.findAll().toStream().count();

        List<Characteristic> testData2 = getTestData();
        service.saveAll(testData).blockLast();
        long actualSize2 = service.findAll().toStream().count();
        assertEquals(actualSize, actualSize2);
    }

}
