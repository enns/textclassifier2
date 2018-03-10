package org.ripreal.textclassifier2.data.reactive.repos;

import org.ripreal.textclassifier2.data.entries.PersistClassifiableText;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClassifiableTextRepo extends ReactiveMongoRepository<PersistClassifiableText, String> {

}
