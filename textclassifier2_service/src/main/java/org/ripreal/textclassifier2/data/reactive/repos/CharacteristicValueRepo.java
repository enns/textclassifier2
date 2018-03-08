package org.ripreal.textclassifier2.data.reactive.repos;

import org.ripreal.textclassifier2.entries.PersistCharacteristicValue;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CharacteristicValueRepo extends ReactiveMongoRepository<PersistCharacteristicValue, String> {
    @Query("{'characteristicName' : ?0 }")
    public Flux<PersistCharacteristicValue> findByCharacteristicName(String characteristicName);

}
