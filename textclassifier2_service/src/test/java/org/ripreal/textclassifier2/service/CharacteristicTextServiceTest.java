package org.ripreal.textclassifier2.service;

import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.service.decorators.LoggerDataService;
import org.ripreal.textclassifier2.testdata.ClassifiableTextTestData;
import org.ripreal.textclassifier2.testdata.ClassifiableTextTestDataHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CharacteristicTextServiceTest extends AbstractServiceTest<Characteristic> {

    @Autowired
    private DataService<Characteristic> service;

    @Override
    protected DataService<Characteristic> createDataService() {
        return new LoggerDataService<>(service);
    }

    @Override
    protected List<Characteristic> getTestData() {
        return ClassifiableTextTestDataHelper.getCharacteristicTestData();
    }
}
