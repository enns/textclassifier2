package org.ripreal.textclassifier2.storage.data.reactive.specifications;

import org.ripreal.textclassifier2.storage.data.queries.QuerySpecification;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class FindVocabularyByNgramMongoSpec implements QuerySpecification{

    private final NGramStrategy.NGRAM_TYPES ngramType;

    public FindVocabularyByNgramMongoSpec(NGramStrategy.NGRAM_TYPES ngramType) {
        this.ngramType = ngramType;
    }

    @Override
    public Query get() {
        return new Query(Criteria
                .where("ngram").is(ngramType.toString()));
    }
}
