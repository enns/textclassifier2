package org.ripreal.textclassifier2.translators;

import org.junit.*;
import org.junit.runner.RunWith;
import org.ripreal.textclassifier2.App;
import org.ripreal.textclassifier2.service.ClassifiableTextService;
import org.ripreal.textclassifier2.testdata.ClassifiableTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {App.class})
public class FromMongoToClassifiableTranslatorTest {

    @Autowired
    FromMongoToClassifiableTranslator translator;

    @Autowired
    ClassifiableTextService textService;

    @Before
    public void setUp() throws Exception {
        //textService.deleteAll().blockLast();
        //textService.saveAll(ClassifiableTestData.getTextTestData()).blockLast();
    }

    @After
    public void tearDown() throws Exception {
        //textService.deleteAll().blockLast();
    }

    @Test
    public void toClassifiableTexts() {
        //translator.toClassifiableTexts();
    }

    @Test
    public void toCharacteristics() {
    }

    @Test
    public void toVocabulary() {
    }
}