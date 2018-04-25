package org.ripreal.textclassifier2.storage.data.reactive.repos;

import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClassifiableTextRepo extends ReactiveMongoRepository<MongoClassifiableText, String> {

}
