package org.ripreal.textclassifier2.storage.service;

import lombok.NoArgsConstructor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.storage.SpringTestConfig;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import org.ripreal.textclassifier2.storage.testdata.AutogenerateTestDataProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

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

        List<MongoClassifiableText> texts = new AutogenerateTestDataProvider().next().getClassifiableTexts();

        assertNotNull(service.saveAllTexts(texts).blockLast());
        assertEquals((long) texts.size(), service.findAllTexts().toStream().count());

        // TEST Characteristics

        Set<MongoCharacteristic> characteristics = new AutogenerateTestDataProvider().next().getCharacteristics();
        // check doubles
        assertNotNull(service.saveAllTexts(texts).blockLast());
        assertEquals((long) characteristics.size(), service.findAllCharacteristics().toStream().count());

        MongoCharacteristic found1 = service.findCharacteristicByName(characteristics.toArray(
                new MongoCharacteristic[0])[0].getName()).block();
        assertNotNull(found1);

        // TEST Vocabulary

        Set<MongoVocabularyWord> vocabulary = new AutogenerateTestDataProvider().next().getVocabulary();

        assertNotNull(service.saveAllVocabulary(vocabulary).blockLast());
        // check doubles
        assertNotNull(service.saveAllVocabulary(vocabulary).blockLast());

        assertEquals((long) vocabulary .size(), service.findVocabularyByNgram(NGramStrategy.getNGramStrategy(
            NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM)).toStream().count());

    }
}