package org.ripreal.textclassifier2.storage.testdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.storage.SpringTestConfig;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class InputStreamTestDataProviderTest extends SpringTestConfig {

    @Autowired
    private ObjectMapper mapper;
    private List<MongoClassifiableText> textsToCheck;
    private String jsonTextsToCheck;

    @Before
    public void init() throws IOException {
        TestDataProvider reader = new AutogenerateTestDataProvider();
        textsToCheck = reader.next().getClassifiableTexts();
        jsonTextsToCheck = mapper.writer().writeValueAsString(textsToCheck);
    }

    @Test
    public void testNext() throws IOException {
        TestDataProvider reader = new InputStreamTestDataProvider(
            new ByteArrayInputStream(jsonTextsToCheck.getBytes(StandardCharsets.UTF_8)),
            mapper,
            1
        );
        List<MongoClassifiableText> texts = new ArrayList<>();

        TestDataProvider.ClassifiableData data;
        while(!(data = reader.next()).isEmpty()) {
            texts.addAll(data.getClassifiableTexts());
        }
        assertEquals(textsToCheck.get(0), texts.get(0));
        assertEquals(textsToCheck.size(), texts.size());
    }
}