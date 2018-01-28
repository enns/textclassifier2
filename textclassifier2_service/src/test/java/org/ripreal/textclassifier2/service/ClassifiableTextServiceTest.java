package org.ripreal.textclassifier2.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ripreal.textclassifier2.App;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.service.decorators.LoggerDataService;
import org.ripreal.textclassifier2.testdata.ClassifiableTextTestDataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@NoArgsConstructor
public class ClassifiableTextServiceTest extends AbstractServiceTest<ClassifiableText>{

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