package org.ripreal.textclassifier2.data.reactive.queries;

import reactor.core.publisher.Flux;

@FunctionalInterface
public interface RepoSpecification<T>  {
    Flux<T> get();
}
