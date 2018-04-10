package org.ripreal.textclassifier2.storage.config;

import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableFactory;
import org.ripreal.textclassifier2.storage.service.ClassifiableService;
import org.ripreal.textclassifier2.storage.testdata.MongoTestDataReader;
import org.ripreal.textclassifier2.testdata.TestDataReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TextClassifierConfig {

    @Bean
    public MongoClassifiableFactory textFactory() {
        return new MongoClassifiableFactory();
    }

}
