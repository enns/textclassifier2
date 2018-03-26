package org.ripreal.textclassifier2.storage.testdata;

import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import org.ripreal.textclassifier2.storage.service.MongoTextService;
import org.ripreal.textclassifier2.storage.service.ClassifiableTextService;
import org.ripreal.textclassifier2.storage.service.VocabularyWordService;
import org.ripreal.textclassifier2.storage.service.decorators.LoggerClassifiableTextService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@Profile("test")
@Configuration
public class TestDataRunner {

    private final ClassifiableTextService<MongoClassifiableText> textService;
    private final ClassifiableTextService<MongoVocabularyWord> vocabService;

    public TestDataRunner(MongoTextService textService, VocabularyWordService vocabService) {
        this.textService = new LoggerClassifiableTextService<>(textService);
        this.vocabService = new LoggerClassifiableTextService<>(vocabService);
    }

    @Bean
    @Transactional
    public CommandLineRunner init() {
        return args -> {
            /*
            textService
                    .deleteAll().blockLast();
            textService.saveAll(ClassifiableTestData.getTextTestData()).blockLast();
            */
        };
    }
}
