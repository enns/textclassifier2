package org.ripreal.textclassifier2.storage.testdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.*;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.storage.data.mapper.EntitiesConverter;
import org.ripreal.textclassifier2.storage.service.ClassifiableService;
import org.ripreal.textclassifier2.testdata.TestDataReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Profile("test")
@Configuration
@Slf4j
public class TestDataRunner {

    @Autowired
    private ClassifiableService textService;

    @Autowired
    private ClassifiableFactory textFactory;

    @Autowired
    private ObjectMapper mapper;

    @Bean
    @Transactional
    public CommandLineRunner init() {
        return args -> {
            if (String.join(" -", args).toUpperCase().contains("CREATE_TEST_DATA")) {
                textService.deleteAll().blockLast();
                TestDataReader provider = new AutogenerateTestDataReader();
                textService.saveAllTexts(EntitiesConverter.castToMongoTexts(provider.next().getClassifiableTexts())).blockLast();
            }
            else if (String.join(" -", args).toUpperCase().contains("LOAD_FORM_JIRA_TEST_DATA"))
                runJiraLoader();
            else if (String.join(" -", args).toUpperCase().contains("LOAD_FORM_JSON_TEST_DATA"))
                runJsonLoader();
        };
    }

    private void runJsonLoader() throws IOException {
        TestDataReader reader = new InputStreamTestDataReader(
            new FileInputStream(new File("./resources/jira.json")),
            mapper,
            60
        );
        List<ClassifiableText> texts = new ArrayList<>();
        long running_read = 0;
        while(reader.hasNext()) {
            List<ClassifiableText> read = reader.next().getClassifiableTexts();
            running_read += read.size();
            log.info("read {}", running_read);
            texts.addAll(texts);
        }
    }

    private void runJiraLoader() throws Exception {
        textService.deleteAll().blockLast();
        JiraClient jiraClient = new JiraBasicAuthClient(new PropertiesClient());
        try (JiraIssueReader reader = jiraClient.newIssueReader(100, textFactory)) {
            reader.setUpperLimit(100000);
            while(reader.next()) {
                List<ClassifiableText> texts = reader.getTexts();
                textService.saveAllTexts(EntitiesConverter.castToMongoTexts(texts))
                .blockLast();
                log.info("Read {} texts", reader.getPosition());
            }
        } catch (IOException e) {
            log.error("error during loading texts from Jira", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runOtherBaseLoader() throws Exception{
        MongoClient mongoClient = new MongoClient();

    }

}
