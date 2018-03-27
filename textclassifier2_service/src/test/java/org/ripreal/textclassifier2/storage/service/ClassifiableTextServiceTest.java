package org.ripreal.textclassifier2.storage.service;

import lombok.NoArgsConstructor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ripreal.textclassifier2.SpringTestConfig;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import org.ripreal.textclassifier2.storage.service.decorators.LoggerClassifiableTextService;
import org.ripreal.textclassifier2.storage.testdata.ClassifiableTestData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@NoArgsConstructor
public class ClassifiableTextServiceTest extends SpringTestConfig {

    @Autowired
    private ClassifiableService service;

    @Before
    public void setUp() {
        service.deleteAll().blockLast();
    }

    @After
    public void tearDown() {
        service.deleteAll().blockLast();
    }

    @Test
    public void testCRUD() {

        // TEST Texts

        List<MongoClassifiableText> texts = ClassifiableTestData.getTextTestData();

        assertNotNull(service.saveAllTexts(texts).blockLast());
        assertEquals((long) texts.size(), service.findAllTexts().toStream().count());

        // TEST Characteristics

        List<MongoCharacteristic> characteristics = ClassifiableTestData.getCharacteristicTestData();
        assertEquals((long) characteristics.size(), service.findAllCharacteristics().toStream().count());

        MongoCharacteristic found1 = service.findCharacteristicByName(characteristics.get(0).getName()).block();
        assertNotNull(found1);

        // TEST Vocabulary

        List<MongoVocabularyWord> vocabulary = ClassifiableTestData.getVocabTestData();

        assertNotNull(service.saveAllVocabulary(vocabulary).blockLast());

        assertEquals((long) vocabulary .size(), service.findVocabularyByNgram(NGramStrategy.getNGramStrategy(NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM)).toStream().count());

        assertNotNull(service.deleteAll().blockLast());

    }
}