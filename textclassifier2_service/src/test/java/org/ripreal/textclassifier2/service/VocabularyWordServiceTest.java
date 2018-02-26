package org.ripreal.textclassifier2.service;

import org.junit.Test;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.service.decorators.LoggerDataService;
import org.ripreal.textclassifier2.testdata.ClassifiableTestData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class VocabularyWordServiceTest extends AbstractServiceTest<VocabularyWord> {

    @Autowired
    private DataService<VocabularyWord> service;

    @Override
    protected DataService<VocabularyWord> createDataService() {
        return new LoggerDataService<>(service);
    }

    @Override
    protected List<VocabularyWord> getTestData() {
        return ClassifiableTestData.getVocabTestData();
    }

    @Test
    public void findById() {
        DataService<VocabularyWord> service = createDataService();
        VocabularyWord data = service.saveAll(getTestData()).blockLast();
        assertNotNull(service.findById(data.getId()).block());
    }
}