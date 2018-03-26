package org.ripreal.textclassifier2.storage.config;

import org.ripreal.textclassifier2.storage.data.reactive.CharacteristicMongoListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

@Configuration
public class MongoConfig {

    @Bean
    public CharacteristicMongoListener characteristicMongoListener(MongoOperations mongoOperations) {
        return new CharacteristicMongoListener(mongoOperations);
    }
}
