package org.ripreal.textclassifier2.storage.testdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ripreal.textclassifier2.*;
import org.ripreal.textclassifier2.classifier.Classifier;
import org.ripreal.textclassifier2.classifier.ClassifierBuilder;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.storage.service.ClassifiableService;
import org.ripreal.textclassifier2.testdata.TestDataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.List;

@Profile("test")
@Configuration
public class TestDataRunner {

    private final static Logger log = LoggerFactory.getLogger(TestDataRunner.class);

    @Autowired
    private ClassifiableService textService;

    @Autowired
    private ClassifiableFactory textFactory;

    @Autowired
    private ClassifiableMapper textMapper;

    @Autowired
    private ObjectMapper mapper;

    @Bean
    @Transactional
    public CommandLineRunner init() {
        return args -> {
            if (String.join(" -", args).toUpperCase().contains("CREATE_TEST_DATA")) {
                textService.deleteAll().blockLast();
                TestDataReader provider = new AutogenerateTestDataReader(textMapper);
                textService.saveAllTexts(textMapper.fromClassifiableText(provider.next().getClassifiableTexts())).blockLast();
            }
            else if (String.join(" -", args).toUpperCase().contains("LOAD_FORM_JIRA_TEST_DATA")) {
                runJiraLoader();
                runLearning();
            }
            else if (String.join(" -", args).toUpperCase().contains("LOAD_FORM_JSON_TEST_DATA"))
                runJsonLoader();
            else if (String.join(" -", args).toUpperCase().contains("RUN_LEARNING"))
                runLearning();
        };
    }

    private void runJsonLoader() throws IOException {
        textService.deleteAll().blockLast();
        TestDataReader reader = new JsonTestDataReader(
            new FileInputStream(new File("./resources/jira.json")),
            mapper, textMapper, 100
        );
        long running_read = 0;
        while(reader.hasNext()) {
            List<ClassifiableText> texts = reader.next().getClassifiableTexts();
            running_read += texts.size();
            log.info("read {}", running_read);
            textService.saveAllTexts(textMapper.fromClassifiableText(texts)).blockLast();
        }
    }

    private void runJiraLoader() throws Exception {
        textService.deleteAll().blockLast();
        JiraClient jiraClient = new JiraBasicAuthClient(new PropertiesClient());
        try (JiraIssueReader reader = jiraClient.newIssueReader(100, textFactory)) {
            reader.setUpperLimit(100);
            while(reader.next()) {
                List<ClassifiableText> texts = reader.getTexts();
                textService.saveAllTexts(textMapper.fromClassifiableText(texts));
                textService.saveAllCharacteristics(textMapper.fromCharacteristic(reader.getCharacteristics()))
                .blockLast();
                textService.findAllCharacteristics();
                log.info("Read {} texts", reader.getPosition());
            }
        } catch (IOException e) {
            log.error("error during loading texts from Jira", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runLearning() throws IOException {

        TestDataReader reader = new MongoTestDataReader(textService, textMapper, 10000);
        Classifier classifier = ClassifierBuilder.fromReader(reader, textFactory)
            //.addNeroClassifierUnit("issueType", NGramStrategy.getNGramStrategy(NGramStrategy.NGRAM_TYPES.FILTERED_BIGRAM))
           .addNeroClassifierUnit("issueType", NGramStrategy.getNGramStrategy(NGramStrategy.NGRAM_TYPES.FILTERED_BIGRAM))
            .build();
        classifier.saveClassifiers(new File("./resources"));
    }

}
