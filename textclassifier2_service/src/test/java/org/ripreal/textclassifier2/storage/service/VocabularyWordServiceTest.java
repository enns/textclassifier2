package org.ripreal.textclassifier2.storage.service;

import org.junit.Test;
import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import org.ripreal.textclassifier2.storage.service.decorators.LoggerClassifiableTextService;
import org.ripreal.textclassifier2.storage.testdata.ClassifiableTestData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class VocabularyWordServiceTest extends AbstractServiceTest<MongoVocabularyWord> {

    @Autowired
    private ClassifiableTextService<MongoVocabularyWord> service;

    @Override
    protected ClassifiableTextService<MongoVocabularyWord> createDataService() {
        return new LoggerClassifiableTextService<>(service);
    }

    @Override
    protected List<MongoVocabularyWord> getTestData() {
        return ClassifiableTestData.getVocabTestData();
    }

    @Test
    public void findById() {
        ClassifiableTextService<MongoVocabularyWord> service = createDataService();
        MongoVocabularyWord data = service.saveAll(getTestData()).blockLast();
        assertNotNull(service.findById(data.getId()).block());
    }
}