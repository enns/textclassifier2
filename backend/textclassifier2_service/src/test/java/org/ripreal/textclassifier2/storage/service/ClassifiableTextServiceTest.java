package org.ripreal.textclassifier2.storage.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.storage.SpringTestConfig;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristicValue;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import org.ripreal.textclassifier2.storage.data.mapper.EntitiesConverter;
import org.ripreal.textclassifier2.storage.testdata.AutogenerateTestDataReader;
import org.ripreal.textclassifier2.storage.testdata.ClassifiableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ClassifiableTextServiceTest extends SpringTestConfig {

    @Autowired
    private ClassifiableService service;
    @Autowired
    private ClassifiableMapper classifiableMapper;

    public ClassifiableTextServiceTest() {
    }

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

        List<ClassifiableText> texts = new AutogenerateTestDataReader(classifiableMapper).next().getClassifiableTexts();

        assertNotNull(service.saveAllTexts(classifiableMapper.fromClassifiableText(texts)).blockLast());
        assertEquals((long) texts.size(), service.findAllTexts().toStream().count());

        // TEST Characteristics

        Set<MongoCharacteristic> characteristics = classifiableMapper.fromCharacteristic(new AutogenerateTestDataReader
                (classifiableMapper)
                .next()
                .getCharacteristics());
        // check doubles
        assertNotNull(service.saveAllTexts(classifiableMapper.fromClassifiableText(texts)).blockLast());

        Set<MongoCharacteristic> target = new HashSet<>();
        service.findAllCharacteristics().toIterable().forEach(target::add);
        assertEquals((long) characteristics.size(), target.size());

        MongoCharacteristic found1 = service.findCharacteristicByName(characteristics.toArray(
                new MongoCharacteristic[0])[0].getName()).block();
        assertNotNull(found1);

        // TEST Vocabulary

        Set<MongoVocabularyWord> vocabulary = new AutogenerateTestDataReader(classifiableMapper).getVocabTestData();

        assertNotNull(service.saveAllVocabulary(vocabulary).blockLast());
        // check doubles
        assertNotNull(service.saveAllVocabulary(vocabulary).blockLast());

        assertEquals((long) vocabulary.size(), service.findVocabularyByNgram(NGramStrategy.getNGramStrategy(
                NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM)).toStream().count());
    }

    @Test
    public void testFindByCharacteristic() {

        List<MongoClassifiableText> texts = classifiableMapper.fromClassifiableText(new AutogenerateTestDataReader
                (classifiableMapper).next().getClassifiableTexts());
        assertNotNull(service.saveAllTexts(texts).blockLast());

        MongoCharacteristicValue[] arr = texts.get(0).getCharacteristics().toArray(new MongoCharacteristicValue[0]);
        Set<MongoCharacteristicValue> res = service.findCharacteristicValuesByCharacteristic(arr[0].getCharacteristic()).toStream()
                .collect(Collectors.toSet());

    }

}
