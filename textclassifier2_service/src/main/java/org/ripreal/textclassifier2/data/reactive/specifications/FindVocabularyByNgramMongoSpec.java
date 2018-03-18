package org.ripreal.textclassifier2.data.reactive.specifications;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.data.queries.QuerySpecification;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@RequiredArgsConstructor
public class FindVocabularyByNgramMongoSpec implements QuerySpecification{

    private final NGramStrategy.NGRAM_TYPES ngramType;

    @Override
    public Query get() {
        return new Query(Criteria
                .where("ngram").is(ngramType.toString()));
    }
}
