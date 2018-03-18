package org.ripreal.textclassifier2.data.reactive.repos;

import org.ripreal.textclassifier2.data.entries.PersistVocabularyWord;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
@Repository
public interface VocabularyWordRepo extends ReactiveMongoRepository<PersistVocabularyWord, String> {
    @Query("{'ngram' : ?0 }")
    public Flux<PersistVocabularyWord> findByNGram(String ngram);
}
