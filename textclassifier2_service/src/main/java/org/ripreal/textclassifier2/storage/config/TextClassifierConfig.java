package org.ripreal.textclassifier2.storage.config;

import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableFactory;
import org.ripreal.textclassifier2.storage.translators.MongoClassifiableTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TextClassifierConfig {

    @Bean
    public MongoClassifiableTranslator textTranslator() {
        return new MongoClassifiableTranslator(textFactory());
    }

    @Bean
    public MongoClassifiableFactory textFactory() {
        return new MongoClassifiableFactory();
    }

}
