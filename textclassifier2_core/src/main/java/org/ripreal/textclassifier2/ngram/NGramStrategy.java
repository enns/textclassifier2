package org.ripreal.textclassifier2.ngram;

import java.util.Set;

public interface NGramStrategy {

    public enum NGRAM_TYPES {UNIGRAM, FILTERED_UNIGRAM, BIGRAM, FILTERED_BIGRAM}

    public static NGramStrategy getNGramStrategy(NGRAM_TYPES type) {
        switch (type) {
            case UNIGRAM:
                return new Unigram();
            case FILTERED_UNIGRAM:
                return new FilteredUnigram();
            case BIGRAM:
                return new Bigram(new Unigram());
            case FILTERED_BIGRAM:
                return new Bigram(new FilteredUnigram());
            default:
                return null;
        }
    }

    Set<String> getNGram(String text);
}