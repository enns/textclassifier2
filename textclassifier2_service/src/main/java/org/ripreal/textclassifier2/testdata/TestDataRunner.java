package org.ripreal.textclassifier2.testdata;

import org.ripreal.textclassifier2.entries.PersistClassifiableText;
import org.ripreal.textclassifier2.entries.PersistVocabularyWord;
import org.ripreal.textclassifier2.service.ClassifiableTextService;
import org.ripreal.textclassifier2.service.DataService;
import org.ripreal.textclassifier2.service.VocabularyWordService;
import org.ripreal.textclassifier2.service.decorators.LoggerDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@Profile("test")
@Configuration
public class TestDataRunner {

    private final DataService<PersistClassifiableText> textService;
    private final DataService<PersistVocabularyWord> vocabService;

    public TestDataRunner(ClassifiableTextService textService, VocabularyWordService vocabService) {
        this.textService = new LoggerDataService<>(textService);
        this.vocabService = new LoggerDataService<>(vocabService);
    }

    @Bean
    @Transactional
    public CommandLineRunner init() {
        return args -> {
            textService
                    .deleteAll().blockLast();

            textService.saveAll(ClassifiableTestData.getTextTestData()).blockLast();

            /*
            textService
                    .deleteAll()
                    .thenMany(textService.saveAll(ClassifiableTestData.getTextTestData()));

            vocabService
                    .deleteAll()
                    .thenMany(vocabService.saveAll(ClassifiableTestData.getVocabTestData()));
            */
        };
    }
}
