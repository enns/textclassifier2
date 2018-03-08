package org.ripreal.textclassifier2.data.reactive.repos;

import org.ripreal.textclassifier2.entries.PersistCharacteristicValue;
import org.ripreal.textclassifier2.entries.PersistVocabularyWord;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface VocabularyWordRepo extends ReactiveMongoRepository<PersistVocabularyWord, String> {
    @Query("{'ngram' : ?0 }")
    public Flux<PersistVocabularyWord> findByNGram(String ngram);
}
