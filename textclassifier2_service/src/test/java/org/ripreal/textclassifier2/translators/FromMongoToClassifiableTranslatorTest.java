package org.ripreal.textclassifier2.translators;

import org.junit.*;
import org.ripreal.textclassifier2.entries.PersistClassifiableText;
import org.ripreal.textclassifier2.service.ClassifiableTextService;
import org.ripreal.textclassifier2.service.DataService;
import org.ripreal.textclassifier2.testdata.ClassifiableTestData;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class FromMongoToClassifiableTranslatorTest {

    @Autowired
    static FromMongoToClassifiableTranslator translator;

    @Autowired
    static ClassifiableTextService textService;

    @BeforeClass
    @Autowired
    public static void setUp(ClassifiableTextService textService, FromMongoToClassifiableTranslator translator) throws Exception {
        textService.deleteAll().blockLast();
        textService.saveAll(ClassifiableTestData.getTextTestData()).blockLast();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        textService.deleteAll().blockLast();
    }

    @Test
    public void toClassifiableTexts() {
        translator.toClassifiableTexts();
    }

    @Test
    public void toCharacteristics() {
    }

    @Test
    public void toVocabulary() {
    }
}