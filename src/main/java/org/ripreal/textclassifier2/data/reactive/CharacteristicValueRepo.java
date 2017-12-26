package org.ripreal.textclassifier2.data.reactive;

import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicValueRepo extends ReactiveMongoRepository<CharacteristicValue, String> {
}
