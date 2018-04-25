package org.ripreal.textclassifier2;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.*;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.model.modelimp.DefClassifiableFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JiraApp {

    static PropertiesClient properties;

    public static void main(String... args) throws Exception {
        JiraClient client = new JiraBasicAuthClient(properties);
        JiraIssueWriter writer = new JiraIssueWriter(client, new ObjectMapper());
        JiraIssueReader reader = client.newIssueReader(1000, new DefClassifiableFactory());
        reader.setUpperLimit(100000);
        writer.write(reader, new FileOutputStream("./jira.json"));
    }

    static {
        try {
            properties = new PropertiesClient();
        } catch (Exception e) {
            log.error("properties not found", e);
            System.exit(1);
        }
    }
}
