package org.ripreal.textclassifier2.storage.data.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.ripreal.textclassifier2.storage.SpringTestConfig;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristicValue;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import org.ripreal.textclassifier2.storage.testdata.AutogenerateTestDataReader;
import org.ripreal.textclassifier2.storage.testdata.ClassifiableMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.Assert.*;

public class DeserializationTest extends SpringTestConfig {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ClassifiableMapper classifiableMapper;

    @Test
    public void deserializeClassifiableTextTest() throws IOException {
        deserialize(classifiableMapper.fromClassifiableText(new AutogenerateTestDataReader(classifiableMapper).next().getClassifiableTexts
                ()).get(0), MongoClassifiableText.class);
    }

    @Test
    public void deserializeCharacteristic() throws IOException {
        deserialize(classifiableMapper.fromCharacteristic(new AutogenerateTestDataReader(classifiableMapper).next()
                .getCharacteristics())
            .toArray(new MongoCharacteristic[0])[0], MongoCharacteristic.class);
    }

    @Test
    public void deserializeCharacteristicValue() throws IOException {
        deserialize(classifiableMapper.fromCharacteristicValues(new AutogenerateTestDataReader(classifiableMapper).next()
                .getCharacteristicValues())
            .toArray(new MongoCharacteristicValue[0])[0], MongoCharacteristicValue.class);
    }

    @Test
    public void deserializeVocabularyWord() throws IOException {
        deserialize(new AutogenerateTestDataReader(classifiableMapper).getVocabTestData()
            .toArray(new MongoVocabularyWord[0])[0], MongoVocabularyWord.class);
    }

    public <T> void deserialize(T data, Class<T> dataClass) throws IOException {
        String beforeJson = mapper.writeValueAsString(data);
        String afterJson =  mapper.writeValueAsString(mapper.readValue(beforeJson, dataClass));

        assertEquals(beforeJson, afterJson);
    }

}