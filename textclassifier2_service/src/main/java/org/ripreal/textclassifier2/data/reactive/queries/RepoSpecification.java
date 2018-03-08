package org.ripreal.textclassifier2.data.reactive.queries;

import org.ripreal.textclassifier2.entries.PersistCharacteristic;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface RepoSpecification<T>  {
    Flux<T> get();
}
