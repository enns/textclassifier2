package org.ripreal.textclassifier2.storage.config;

import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.ripreal.textclassifier2.model.modelimp.DefClassifiableFactory;
import org.ripreal.textclassifier2.storage.testdata.ClassifiableMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TextClassifierConfig {

    @Bean
    public ClassifiableFactory textFactory() {
        return new DefClassifiableFactory();
    }

    @Bean
    public ClassifiableMapper mapper() {
        return new ClassifiableMapper(textFactory());
    }

}