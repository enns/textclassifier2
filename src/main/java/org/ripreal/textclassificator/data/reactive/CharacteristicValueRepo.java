package org.ripreal.textclassificator.data.reactive;

import org.ripreal.textclassificator.model.CharacteristicValue;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicValueRepo extends ReactiveMongoRepository<CharacteristicValue, String> {
}
