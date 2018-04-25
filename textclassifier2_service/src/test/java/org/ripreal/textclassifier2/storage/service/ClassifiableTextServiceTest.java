package org.ripreal.textclassifier2.storage.service;

import lombok.NoArgsConstructor;
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
import org.ripreal.textclassifier2.storage.data.mapper.EntitiesConverter;
import org.ripreal.textclassifier2.storage.testdata.AutogenerateTestDataReader;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        List<ClassifiableText> texts = new AutogenerateTestDataReader().next().getClassifiableTexts();

        assertNotNull(service.saveAllTexts(EntitiesConverter.castToMongoTexts(texts)).blockLast());
        assertEquals((long) texts.size(), service.findAllTexts().toStream().count());

        // TEST Characteristics

        Set<Characteristic> characteristics = new AutogenerateTestDataReader().next().getCharacteristics();
        // check doubles
        assertNotNull(service.saveAllTexts(EntitiesConverter.castToMongoTexts(texts)).blockLast());

        Set<Characteristic> target = new HashSet<>();
        service.findAllCharacteristics().toIterable().forEach(target::add);
        assertEquals((long) characteristics.size(), target.size());

        MongoCharacteristic found1 = service.findCharacteristicByName(characteristics.toArray(
                new MongoCharacteristic[0])[0].getName()).block();
        assertNotNull(found1);

        // TEST Vocabulary

        Set<VocabularyWord> vocabulary = new AutogenerateTestDataReader().getVocabTestData();

        assertNotNull(service.saveAllVocabulary(EntitiesConverter.castToMongoVocabulary(vocabulary)).blockLast());
        // check doubles
        assertNotNull(service.saveAllVocabulary(EntitiesConverter.castToMongoVocabulary(vocabulary)).blockLast());

        assertEquals((long) vocabulary.size(), service.findVocabularyByNgram(NGramStrategy.getNGramStrategy(
                NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM)).toStream().count());
    }

    @Test
    public void testFindByCharacteristic() {

        List<ClassifiableText> texts = new AutogenerateTestDataReader().next().getClassifiableTexts();
        assertNotNull(service.saveAllTexts(EntitiesConverter.castToMongoTexts(texts)).blockLast());

        MongoCharacteristicValue[] arr = texts.get(0).getCharacteristics().toArray(new MongoCharacteristicValue[0]);
        Set<MongoCharacteristicValue> res = service.findCharacteristicValuesByCharacteristic(arr[0].getCharacteristic()).toStream().collect(Collectors.toSet());

    }

}