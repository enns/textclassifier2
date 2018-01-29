package org.ripreal.textclassifier2.service;

import lombok.NoArgsConstructor;
import org.junit.Test;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.service.decorators.LoggerDataService;
import org.ripreal.textclassifier2.testdata.ClassifiableTextTestDataHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@NoArgsConstructor
public class ClassifiableTextServiceTest extends AbstractServiceTest<ClassifiableText> {

    @Autowired
    private DataService<ClassifiableText> service;

    @Override
    protected DataService<ClassifiableText> createDataService() {
        return new LoggerDataService<>(service);
    }

    @Override
    protected List<ClassifiableText> getTestData() {
        return ClassifiableTextTestDataHelper.getTextTestData();
    }

    @Test
    public void findAll() throws Exception {
    }

    @Test
    public void findById() throws Exception {
    }

    @Test
    public void deleteAll() throws Exception {
    }

}