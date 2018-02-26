package org.ripreal.textclassifier2.service;

import lombok.NoArgsConstructor;
import org.junit.Test;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.service.decorators.LoggerDataService;
import org.ripreal.textclassifier2.testdata.ClassifiableTestData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertNotNull;

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
        return ClassifiableTestData.getTextTestData();
    }

    @Test
    public void testFindById() throws Exception {
        DataService<ClassifiableText> service = createDataService();
        ClassifiableText text = service.saveAll(getTestData()).blockLast();
        assertNotNull(service.findById(text.getId()).block());
    }

}