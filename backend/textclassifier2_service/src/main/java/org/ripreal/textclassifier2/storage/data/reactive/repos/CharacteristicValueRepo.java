package org.ripreal.textclassifier2.storage.data.reactive.repos;

import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristicValue;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CharacteristicValueRepo extends ReactiveMongoRepository<MongoCharacteristicValue, String> {
    @Query("{'characteristic.$id' : ?0 }")
    public Flux<MongoCharacteristicValue> findByCharacteristicName(String MongoCharacteristic);
}
