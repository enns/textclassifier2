package org.ripreal.textclassifier2.data.reactive.repos;

import org.ripreal.textclassifier2.model.ClassifiableText;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClassifiableTextRepo extends ReactiveMongoRepository<ClassifiableText, String> {

}
