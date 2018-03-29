package org.ripreal.textclassifier2.storage.translators;

import org.junit.*;
import org.ripreal.textclassifier2.storage.SpringTestConfig;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.storage.service.MongoTextService;
import org.ripreal.textclassifier2.storage.testdata.ClassifiableTestData;
import org.springframework.beans.factory.annotation.Autowired;

public class MongoClassifiableTranslatorTest extends SpringTestConfig {

    @Autowired
    MongoClassifiableTranslator translator;

    @Autowired
    MongoTextService textService;

    @Before
    public void setUp() throws Exception {
        textService.deleteAll().blockLast();
        textService.saveAllTexts(ClassifiableTestData.getTextTestData()).blockLast();
        textService.saveAllVocabulary(ClassifiableTestData.getVocabTestData()).blockLast();
    }

    @After
    public void tearDown() throws Exception {
        textService.deleteAll().blockLast();
    }

    @Test
    public void toClassifiableTexts() {
        translator.toClassifiableTexts();
    }

    @Test
    public void toCharacteristics() {
        translator.toCharacteristics();
    }

    @Test
    public void toVocabulary() {
        translator.toVocabulary(NGramStrategy.getNGramStrategy(NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM));
    }
}