package org.ripreal.textclassifier2.storage.service;

import org.junit.Test;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.service.decorators.LoggerClassifiableTextService;
import org.ripreal.textclassifier2.storage.testdata.ClassifiableTestData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CharacteristicTextServiceTest extends AbstractServiceTest<MongoCharacteristic> {

    @Autowired
    private ClassifiableTextService<MongoCharacteristic> service;

    @Override
    protected ClassifiableTextService<MongoCharacteristic> createDataService() {
        return new LoggerClassifiableTextService<>(service);
    }

    @Override
    protected List<MongoCharacteristic> getTestData() {
        return ClassifiableTestData.getCharacteristicTestData();
    }

    @Test
    public void testFindById() throws Exception {
        ClassifiableTextService<MongoCharacteristic> service = createDataService();
        MongoCharacteristic characteristic = service.saveAll(getTestData()).blockLast();
        assertNotNull(service.findById(characteristic.getName()).block());
    }

    @Test
    public void testSavingSameDataTwice() {
        // SAVING SAME DATA TWICE SHOULD NOT CREATE DOUBLES
        List<MongoCharacteristic> testData = getTestData();

        service.saveAll(testData).blockLast();

        long actualSize = service.findAll().toStream().count();

        List<MongoCharacteristic> testData2 = getTestData();
        service.saveAll(testData2).blockLast();
        long actualSize2 = service.findAll().toStream().count();
        assertEquals(actualSize, actualSize2);
    }

}
