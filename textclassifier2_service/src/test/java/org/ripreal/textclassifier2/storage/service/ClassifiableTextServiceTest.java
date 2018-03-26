package org.ripreal.textclassifier2.storage.service;

import lombok.NoArgsConstructor;
import org.junit.Test;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.storage.service.decorators.LoggerClassifiableTextService;
import org.ripreal.textclassifier2.storage.testdata.ClassifiableTestData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@NoArgsConstructor
public class ClassifiableTextServiceTest extends AbstractServiceTest<MongoClassifiableText> {

    @Autowired
    private ClassifiableTextService<MongoClassifiableText> service;

    @Override
    protected ClassifiableTextService<MongoClassifiableText> createDataService() {
        return new LoggerClassifiableTextService<>(service);
    }

    @Override
    protected List<MongoClassifiableText> getTestData() {
        return ClassifiableTestData.getTextTestData();
    }

    @Test
    public void testFindById() throws Exception {
        ClassifiableTextService<MongoClassifiableText> service = createDataService();

        MongoClassifiableText text1 = service.saveAll(getTestData()).blockLast();
        MongoClassifiableText found1 = service.findById(text1.getId()).block();
        assertNotNull(found1);

        MongoClassifiableText text2 = service.saveAll(getTestData()).blockLast();
        MongoClassifiableText found2 = service.findById(text2.getId()).block();
        assertNotNull(found2);

        Iterator<CharacteristicValue> iterator1 = found1.getCharacteristics().iterator();
        Iterator<CharacteristicValue> iterator2 = found2.getCharacteristics().iterator();
        while (iterator1.hasNext() && iterator2.hasNext()) {
            assertEquals(iterator1.next().getCharacteristic(),
                iterator2.next().getCharacteristic());
        }
    }

}