package org.ripreal.textclassifier2.storage.testdata;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristicValue;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class InputStreamTestDataProvider implements TestDataProvider {
    @Getter
    private List<MongoClassifiableText> classifiableTexts;
    @Getter
    private Set<MongoCharacteristic> characteristics;
    @Getter
    private Set<MongoCharacteristicValue> characteristicValues;
    @Getter
    private Set<MongoVocabularyWord> vocabulary;

    @NonNull
    private final InputStream source;
    @NonNull
    private final ObjectMapper mapper;
    private final int iteratorSize;

    // INTERFACE METHODS

    @Override
    public TestDataProvider.ClassifiableData next() throws IOException {
        if (parseTextTestData()) {
            characteristics = getCharacteristicTestData();
            characteristicValues = getCharacteristicValueTetData();
            vocabulary = getVocabTestData();
            return new TestDataProvider.ClassifiableData(
                classifiableTexts, characteristics, characteristicValues, vocabulary);
        }
        return TestDataProvider.ClassifiableData.empty();
    }

    @Override
    public void close() throws Exception {
        source.close();
    }

    // PARSING JSON

    private Set<MongoVocabularyWord> getVocabTestData() {
        return new HashSet<>();
    }

    private Set<MongoCharacteristicValue> getCharacteristicValueTetData() {
        return classifiableTexts.stream()
            .flatMap(text ->  text.getCharacteristics().stream())
            .map(value -> (MongoCharacteristicValue) value)
            .collect(Collectors.toSet());
    }

    private Set<MongoCharacteristic> getCharacteristicTestData() {
        return classifiableTexts.stream()
            .flatMap(text -> text.getCharacteristics().stream())
            .map((characteristic) -> (MongoCharacteristic) characteristic)
            .distinct()
            .collect(Collectors.toSet());
    }

    private boolean parseTextTestData() throws IOException {
        JsonNode nodes = mapper.readTree(source);
        JsonFactory fact = new JsonFactory();
        JsonParser parser = fact.createParser(source);
        MappingIterator<String> iterator = mapper.reader().readValues(parser);
        while (iterator.hasNext() && iteratorSize > classifiableTexts.size()) {
            ClassifiableText text = mapper.readValue(iterator.next(), ClassifiableText.class);
            classifiableTexts.add((MongoClassifiableText) text);
        }
        return iterator.hasNext();
    }
}
