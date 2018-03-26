package org.ripreal.textclassifier2.storage.data.reactive.repos;

import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
@Repository
public interface VocabularyWordRepo extends ReactiveMongoRepository<MongoVocabularyWord, String> {
    @Query("{'ngram' : ?0 }")
    public Flux<MongoVocabularyWord> findByNGram(String ngram);
}
