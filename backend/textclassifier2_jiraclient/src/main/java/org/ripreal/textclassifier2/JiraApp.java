package org.ripreal.textclassifier2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ripreal.textclassifier2.model.modelimp.DefClassifiableFactory;
import org.slf4j.Logger;

import java.io.FileOutputStream;

public class JiraApp {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JiraApp.class);
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
