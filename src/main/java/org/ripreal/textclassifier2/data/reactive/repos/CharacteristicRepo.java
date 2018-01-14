package org.ripreal.textclassifier2.data.reactive.repos;

import org.ripreal.textclassifier2.model.Characteristic;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicRepo extends ReactiveMongoRepository<Characteristic, String> {
}
