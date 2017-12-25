package org.ripreal.textclassificator.data.reactive;

import org.ripreal.textclassificator.model.Characteristic;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicRepo extends ReactiveMongoRepository<Characteristic, String> {
}
