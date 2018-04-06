package org.ripreal.textclassifier2.storage.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.ripreal.textclassifier2.storage.data.reactive.CharacteristicMongoListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;

@Configuration
public class MongoConfig {

    /*
    //@Value("${spring.data.mongodb.uri}")
    private String mongoURI = "mongodb+srv://admin:kentdfu!@cluster0-bsfqs.mongodb.net/test";
    */

    @Bean
    public CharacteristicMongoListener characteristicMongoListener(MongoOperations mongoOperations) {
        return new CharacteristicMongoListener(mongoOperations);
    }

/*
    @Bean
    @Profile("production")
    public MongoClient mongoClientCloud() {
        MongoClientURI uri = new MongoClientURI(mongoURI);
        return new MongoClient(uri);
    }

    @Bean
    @Profile("test")
    public MongoClient mongoClientLocal() {
        return new MongoClient();
    }
    */

}
