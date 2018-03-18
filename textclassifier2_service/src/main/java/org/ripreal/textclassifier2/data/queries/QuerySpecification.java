package org.ripreal.textclassifier2.data.queries;

import org.ripreal.textclassifier2.data.reactive.repos.VocabularyWordRepo;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface QuerySpecification {
    Query get();
}
