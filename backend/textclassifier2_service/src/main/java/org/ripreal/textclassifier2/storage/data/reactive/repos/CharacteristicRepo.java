package org.ripreal.textclassifier2.storage.data.reactive.repos;

import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicRepo extends ReactiveMongoRepository<MongoCharacteristic, String> {

}
