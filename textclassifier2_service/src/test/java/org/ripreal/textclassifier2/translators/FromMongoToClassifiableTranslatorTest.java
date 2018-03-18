package org.ripreal.textclassifier2.translators;

import org.junit.*;
import org.ripreal.textclassifier2.SpringTestConfig;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.service.ClassifiableTextService;
import org.ripreal.textclassifier2.service.VocabularyWordService;
import org.ripreal.textclassifier2.testdata.ClassifiableTestData;
import org.springframework.beans.factory.annotation.Autowired;

public class FromMongoToClassifiableTranslatorTest extends SpringTestConfig {

    @Autowired
    FromMongoToClassifiableTranslator translator;

    @Autowired
    ClassifiableTextService textService;

    @Autowired
    VocabularyWordService vocabularyService;

    @Before
    public void setUp() throws Exception {
        textService.deleteAll().blockLast();
        textService.saveAll(ClassifiableTestData.getTextTestData()).blockLast();
        vocabularyService.deleteAll().blockLast();
        vocabularyService.saveAll(ClassifiableTestData.getVocabTestData()).blockLast();

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