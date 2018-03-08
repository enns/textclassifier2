package org.ripreal.textclassifier2.service;

import lombok.NoArgsConstructor;
import org.junit.Test;
import org.ripreal.textclassifier2.entries.PersistCharactValuePair;
import org.ripreal.textclassifier2.model.CharacteristicValuePair;
import org.ripreal.textclassifier2.entries.PersistClassifiableText;
import org.ripreal.textclassifier2.service.decorators.LoggerDataService;
import org.ripreal.textclassifier2.testdata.ClassifiableTestData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@NoArgsConstructor
public class ClassifiableTextServiceTest extends AbstractServiceTest<PersistClassifiableText> {

    @Autowired
    private DataService<PersistClassifiableText> service;

    @Override
    protected DataService<PersistClassifiableText> createDataService() {
        return new LoggerDataService<>(service);
    }

    @Override
    protected List<PersistClassifiableText> getTestData() {
        return ClassifiableTestData.getTextTestData();
    }

    @Test
    public void testFindById() throws Exception {
        DataService<PersistClassifiableText> service = createDataService();

        PersistClassifiableText text1 = service.saveAll(getTestData()).blockLast();
        PersistClassifiableText found1 = service.findById(text1.getId()).block();
        assertNotNull(found1);

        PersistClassifiableText text2 = service.saveAll(getTestData()).blockLast();
        PersistClassifiableText found2 = service.findById(text2.getId()).block();
        assertNotNull(found2);

        Iterator<CharacteristicValuePair> iterator1 = found1.getCharacteristics().iterator();
        Iterator<CharacteristicValuePair> iterator2 = found2.getCharacteristics().iterator();
        while (iterator1.hasNext() && iterator2.hasNext()) {
            assertEquals(iterator1.next().getValue().getCharacteristic(),
                iterator2.next().getValue().getCharacteristic());
        }
    }

}