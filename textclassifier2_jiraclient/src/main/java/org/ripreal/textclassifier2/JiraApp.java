package org.ripreal.textclassifier2;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.model.modelimp.DefClassifiableFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Slf4j
public class JiraApp {

    static PropertiesClient properties;

    public static void main(String... args) throws Exception {
        JiraClient client = new JiraBasicAuthClient(properties);
        JiraIssueWriter writer = new JiraIssueWriter(client, new ObjectMapper());
        writer.write(
            client.newIssueReader(100, new DefClassifiableFactory()),
            new FileOutputStream("./jira.json")
        );
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
