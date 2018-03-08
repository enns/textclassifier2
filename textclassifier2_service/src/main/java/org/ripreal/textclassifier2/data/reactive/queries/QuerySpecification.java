package org.ripreal.textclassifier2.data.reactive.queries;

import org.springframework.data.mongodb.core.query.Query;

@FunctionalInterface
public interface QuerySpecification {
     Query get();
}
