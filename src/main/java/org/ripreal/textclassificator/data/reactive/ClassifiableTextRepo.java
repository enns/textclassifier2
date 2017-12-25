package org.ripreal.textclassificator.data.reactive;

import org.ripreal.textclassificator.model.ClassifiableText;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClassifiableTextRepo extends ReactiveMongoRepository<ClassifiableText, String> {

}
