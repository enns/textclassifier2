package org.ripreal.textclassificator.data.reactive;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {
    @Bean
    public CharacteristicMongoListener characteristicMongoListener() {
        return new CharacteristicMongoListener();
    }

}
