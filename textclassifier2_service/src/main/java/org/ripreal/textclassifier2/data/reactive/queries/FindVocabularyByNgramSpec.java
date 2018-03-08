package org.ripreal.textclassifier2.data.reactive.queries;

import org.ripreal.textclassifier2.data.reactive.repos.VocabularyWordRepo;
import org.ripreal.textclassifier2.entries.PersistCharacteristic;
import org.ripreal.textclassifier2.entries.PersistVocabularyWord;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public class FindVocabularyByNgramSpec implements RepoSpecification<PersistVocabularyWord>{

    @Autowired
    private VocabularyWordRepo repo;
    private NGramStrategy.NGRAM_TYPES ngramType;

    public FindVocabularyByNgramSpec(NGramStrategy.NGRAM_TYPES ngramType){
        this.ngramType = ngramType;
    }

    @Override
    public Flux<PersistVocabularyWord> get() {
        return repo.findByNGram(ngramType.toString());
    }
}
