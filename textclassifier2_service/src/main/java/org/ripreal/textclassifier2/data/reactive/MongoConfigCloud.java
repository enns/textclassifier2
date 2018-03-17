package org.ripreal.textclassifier2.data.reactive;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

public class MongoConfigCloud extends AbstractMongoConfiguration {

    @Override
    public MongoClient mongoClient() {
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://admin:kentdfu!@cluster0-bsfqs.mongodb.net/test");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("test");
        return mongoClient;
    }

    @Override
    protected String getDatabaseName() {
        return "test";
    }
}