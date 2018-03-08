package org.ripreal.textclassifier2.service;

import org.junit.Test;
import org.ripreal.textclassifier2.entries.PersistVocabularyWord;
import org.ripreal.textclassifier2.service.decorators.LoggerDataService;
import org.ripreal.textclassifier2.testdata.ClassifiableTestData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class VocabularyWordServiceTest extends AbstractServiceTest<PersistVocabularyWord> {

    @Autowired
    private DataService<PersistVocabularyWord> service;

    @Override
    protected DataService<PersistVocabularyWord> createDataService() {
        return new LoggerDataService<>(service);
    }

    @Override
    protected List<PersistVocabularyWord> getTestData() {
        return ClassifiableTestData.getVocabTestData();
    }

    @Test
    public void findById() {
        DataService<PersistVocabularyWord> service = createDataService();
        PersistVocabularyWord data = service.saveAll(getTestData()).blockLast();
        assertNotNull(service.findById(data.getId()).block());
    }
}