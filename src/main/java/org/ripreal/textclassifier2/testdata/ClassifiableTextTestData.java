package org.ripreal.textclassifier2.testdata;

import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.data.reactive.repos.VocabularyWordRepo;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.service.ClassifiableTextService;
import org.ripreal.textclassifier2.service.DataService;
import org.ripreal.textclassifier2.service.VocabularyWordService;
import org.ripreal.textclassifier2.service.decorators.LoggerDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Profile("test")
@Configuration
public class ClassifiableTextTestData {

    private final DataService<ClassifiableText> textService;
    private final DataService<VocabularyWord> vocabService;

    public ClassifiableTextTestData(ClassifiableTextService textService, VocabularyWordService vocabService) {
        this.textService = new LoggerDataService<>(textService);
        this.vocabService = new LoggerDataService<>(vocabService);
    }

    @Bean
    @Transactional
    public CommandLineRunner init() {
        return args -> {
            textService
                .deleteAll()
                .thenMany(textService.saveAll(ClassifiableTextTestDataHelper.getTextTestData()));
            vocabService
                .deleteAll()
                .thenMany(vocabService.saveAll(ClassifiableTextTestDataHelper.getVocabTestData()));
        };
    }
}
