package org.ripreal.textclassifier2.storage.testdata;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.testdata.TestDataReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
public class JsonTestDataReader implements TestDataReader {

    @NonNull
    private final InputStream source;
    @NonNull
    private final ObjectMapper mapper;
    private final int iteratorSize;
    private final MappingIterator<ClassifiableText> iterator;

    public JsonTestDataReader(InputStream source, ObjectMapper mapper, int iteratorSize) throws IOException {
        this.source = source;
        this.mapper = mapper;
        this.iteratorSize = iteratorSize;
        iterator = mapper.readerFor(ClassifiableText.class).readValues(source);
    }

    public JsonTestDataReader(InputStream source, ObjectMapper mapper) throws IOException {
        this(source, mapper, 1);
    }

    // ITERATOR METHODS

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public TestDataReader.ClassifiableData next() throws IOException {
        return readClassifiableData(readByIterator());
    }

    @Override
    public ClassifiableData readAll() throws IOException {
        return readClassifiableData(iterator.readAll());
    }

    @Override
    public void close() throws Exception {
        source.close();
    }

    // PARSING JSON

    private List<ClassifiableText> readByIterator() throws IOException {
        List<ClassifiableText> texts = new ArrayList<>();
        while(iterator.hasNext() && iteratorSize > texts.size()) {
            texts.add(iterator.next());
            log.info("read: {}", texts.get(texts.size() - 1));
        }
        return texts;
    }

    private TestDataReader.ClassifiableData readClassifiableData(List<ClassifiableText> texts) {
        if (texts.size() != 0) {
            Set<CharacteristicValue> charValues = TestDataReader.getCharacteristicValueTetData(texts);
            Set<Characteristic> characteristics = TestDataReader.getCharacteristicTestData(charValues);
            return new TestDataReader.ClassifiableData(texts, characteristics, charValues);
        }
        return TestDataReader.ClassifiableData.empty();
    }

}
