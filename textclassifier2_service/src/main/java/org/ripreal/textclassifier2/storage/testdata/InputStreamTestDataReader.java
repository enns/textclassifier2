package org.ripreal.textclassifier2.storage.testdata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristicValue;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class InputStreamTestDataReader implements TestDataReader {

    @NonNull
    private final InputStream source;
    @NonNull
    private final ObjectMapper mapper;
    private final int iteratorSize;
    private final MappingIterator<ClassifiableText> iterator;

    public InputStreamTestDataReader(InputStream source, ObjectMapper mapper, int iteratorSize) throws IOException {
        this.source = source;
        this.mapper = mapper;
        this.iteratorSize = iteratorSize;
        iterator = mapper.readerFor(ClassifiableText.class).readValues(source);
    }

    public InputStreamTestDataReader(InputStream source, ObjectMapper mapper) throws IOException {
        this(source, mapper, 1);
    }

    // INTERFACE METHODS

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public TestDataReader.ClassifiableData next() throws IOException {
        List<ClassifiableText> texts = parseTextTestData();
        if (texts.size() != 0) {
            Set<CharacteristicValue> charValues = getCharacteristicValueTetData(texts);
            Set<Characteristic> characteristics = getCharacteristicTestData(charValues);
            Set<VocabularyWord> vocabulary = getVocabTestData();

            return new TestDataReader.ClassifiableData(
                    texts, characteristics, charValues, vocabulary);
        }
        return TestDataReader.ClassifiableData.empty();
    }

    @Override
    public void close() throws Exception {
        source.close();
    }

    // PARSING JSON

    private Set<VocabularyWord> getVocabTestData() {
        return new HashSet<>();
    }

    private Set<CharacteristicValue> getCharacteristicValueTetData(List<ClassifiableText> texts) {
        return texts.stream()
            .flatMap(text ->  text.getCharacteristics().stream())
            .map(value -> (MongoCharacteristicValue) value)
            .collect(Collectors.toSet());
    }

    private Set<Characteristic> getCharacteristicTestData(Set<CharacteristicValue> charVals) {
        return charVals.stream()
            .map(CharacteristicValue::getCharacteristic)
            .collect(Collectors.toSet());
    }

    private List<ClassifiableText> parseTextTestData() throws IOException {
        List<ClassifiableText> texts = new ArrayList<>();
        while(iterator.hasNext() && iteratorSize > texts.size()) {
            texts.add(iterator.next());
        }
        return texts;
    }
}
