package org.ripreal.textclassifier2.storage.testdata;

import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.JiraBasicAuthClient;
import org.ripreal.textclassifier2.JiraClient;
import org.ripreal.textclassifier2.JiraIssueReader;
import org.ripreal.textclassifier2.PropertiesClient;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.ripreal.textclassifier2.model.ClassifiableText;
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
import java.util.ArrayList;
import java.util.List;

@Profile("test")
@Configuration
@Slf4j
public class ManualTestDataRunner {

    @Autowired
    private ClassifiableService textService;

    @Autowired
    private ClassifiableFactory textFactory;

    @Bean
    @Transactional
    public CommandLineRunner init() {
        return args -> {
            if (String.join(" -", args).toUpperCase().contains("CREATE_TEST_DATA")) {
                textService.deleteAll().blockLast();
                textService.saveAllTexts(ClassifiableTestData.getTextTestData()).blockLast();
            }
            else if (String.join(" -", args).toUpperCase().contains("LOAD_FORM_JIRA_TEST_DATA"))
                runJiraLoader();
        };
    }

    private void runJiraLoader() throws Exception {
        JiraClient jiraClient = new JiraBasicAuthClient(new PropertiesClient());
        try (JiraIssueReader reader = jiraClient.issueReader(100, textFactory)) {
            reader.setUpperLimit(1000);
            while(reader.next()) {
                List<ClassifiableText> texts = reader.getTexts();
                textService.saveAllTexts(castToMongoTexts(texts));
            }
        } catch (IOException e) {
            log.error("error during loading texts from Jira", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<MongoClassifiableText> castToMongoTexts(List<ClassifiableText> texts) {
        List<MongoClassifiableText> mongoTexts = new ArrayList<>();
        texts.forEach(item -> mongoTexts.add(((MongoClassifiableText) item)));
        return mongoTexts;
    }

}
