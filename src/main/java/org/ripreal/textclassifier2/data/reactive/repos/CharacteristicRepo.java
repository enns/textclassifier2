package org.ripreal.textclassifier2.data.reactive.repos;

import org.ripreal.textclassifier2.model.Characteristic;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface CharacteristicRepo extends ReactiveMongoRepository<Characteristic, String> {

}
