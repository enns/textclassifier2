package org.ripreal.textclassifier2.storage.testdata;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.testdata.TestDataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class JsonTestDataReader implements TestDataReader {

    private final Logger log = LoggerFactory.getLogger(JsonTestDataReader.class);
    private final ClassifiableMapper classifiableMapper;
    private final InputStream source;
    private final ObjectMapper mapper;
    private final int iteratorSize;
    private final MappingIterator<MongoClassifiableText> iterator;

    public JsonTestDataReader(InputStream source, ObjectMapper mapper, ClassifiableMapper classifiableMapper, int
            iteratorSize) throws IOException {
        this.source = source;
        this.mapper = mapper;
        this.classifiableMapper = classifiableMapper;
        this.iteratorSize = iteratorSize;
        iterator = mapper.readerFor(MongoClassifiableText.class).readValues(source);
    }

    public JsonTestDataReader(InputStream source, ObjectMapper mapper, ClassifiableMapper classifiableMapper) throws IOException {
        this(source, mapper, classifiableMapper, 1);
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

    private List<MongoClassifiableText> readByIterator() throws IOException {
        List<MongoClassifiableText> texts = new ArrayList<>();
        while(iterator.hasNext() && iteratorSize > texts.size()) {
            texts.add(iterator.next());
            log.info("read: {}", texts.get(texts.size() - 1));
        }
        return texts;
    }

    private TestDataReader.ClassifiableData readClassifiableData(List<MongoClassifiableText> mongoTexts) {
        if (mongoTexts.size() != 0) {

            List<ClassifiableText> texts = classifiableMapper.toClassifiableText(mongoTexts);
            Set<CharacteristicValue> charValues = TestDataReader.getCharacteristicValueTetData(texts);
            Set<Characteristic> characteristics = TestDataReader.getCharacteristicTestData(charValues);
            return new TestDataReader.ClassifiableData(texts, characteristics, charValues);
        }
        return TestDataReader.ClassifiableData.empty();
    }

}
