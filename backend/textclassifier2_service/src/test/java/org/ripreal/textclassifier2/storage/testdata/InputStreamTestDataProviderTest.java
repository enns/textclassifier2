package org.ripreal.textclassifier2.storage.testdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.storage.SpringTestConfig;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.testdata.TestDataReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class InputStreamTestDataProviderTest extends SpringTestConfig {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ClassifiableMapper classifiableMapper;

    private List<ClassifiableText> textsToCheck;
    private String jsonTextsToCheck;

    @Before
    public void init() throws IOException {
        TestDataReader reader = new AutogenerateTestDataReader(classifiableMapper);
        textsToCheck = reader.next().getClassifiableTexts();
        jsonTextsToCheck = mapper.writer().writeValueAsString(textsToCheck);
    }

    @Test
    public void testNext() throws IOException {
        TestDataReader reader = new JsonTestDataReader(
            new ByteArrayInputStream(jsonTextsToCheck.getBytes(StandardCharsets.UTF_8)),
            mapper, classifiableMapper,
            1
        );
        List<ClassifiableText> texts = new ArrayList<>();

        while(reader.hasNext()) {
            texts.addAll(reader.next().getClassifiableTexts());
        }
        assertEquals(textsToCheck.get(0), texts.get(0));
        assertEquals(textsToCheck.size(), texts.size());
    }
}