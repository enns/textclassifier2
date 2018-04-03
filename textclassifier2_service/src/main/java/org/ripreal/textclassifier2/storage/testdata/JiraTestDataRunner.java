package org.ripreal.textclassifier2.storage.testdata;


import org.ripreal.textclassifier2.JiraBasicAuthClient;
import org.ripreal.textclassifier2.JiraClient;
import org.ripreal.textclassifier2.JiraIssueReader;
import org.ripreal.textclassifier2.PropertiesClient;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.service.ClassifiableService;
import org.ripreal.textclassifier2.storage.service.MongoTextService;
import org.ripreal.textclassifier2.storage.service.decorators.LoggerClassifiableTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Profile("test")
@Configuration
public class JiraTestDataRunner {

    @Autowired
    private ClassifiableService textService;

    @Autowired
    private ClassifiableFactory textFactory;

    @Bean
    @Transactional
    public CommandLineRunner init() {
        return args -> {
            if (!String.join(" -", args).toUpperCase().contains("LOAD_FORM_JIRA_TEST_DATA"))
                return;
            JiraClient jiraClient = new JiraBasicAuthClient(new PropertiesClient());
            try (JiraIssueReader reader = jiraClient.issueReader(100, textFactory)) {

            } catch (IOException e) {

            }

    }
}
