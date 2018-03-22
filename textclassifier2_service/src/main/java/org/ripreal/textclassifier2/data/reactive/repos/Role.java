package org.ripreal.textclassifier2.data.reactive.repos;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface Role extends ReactiveMongoRepository<Role, String> {
}
