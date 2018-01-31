package org.ripreal.textclassifier2.ngram;

import java.util.Set;

public interface NGramStrategy {

    public enum NGRAM_TYPES {UNIGRAM, FILTERED_UNIGRAM,  }

    static NGramStrategy getNGramStrategy(String type) {
        switch (type) {
            case "unigram":
                return new Unigram();
            case "filtered_unigram":
                return new FilteredUnigram();
            case "bigram":
                return new Bigram(new Unigram());
            case "filtered_bigram":
                return new Bigram(new FilteredUnigram());
            default:
                return null;
        }
    }

    Set<String> getNGram(String text);
}