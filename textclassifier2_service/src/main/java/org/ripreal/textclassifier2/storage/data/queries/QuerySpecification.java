package org.ripreal.textclassifier2.storage.data.queries;

import org.springframework.data.mongodb.core.query.Query;

@FunctionalInterface
public interface QuerySpecification {
    Query get();
}
