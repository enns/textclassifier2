package org.ripreal.textclassifier2.ngram;

public class FilteredUnigramTest extends NGramStrategyTest {
    @Override
    protected void initializeIdeal() {
        idealCyrillicText = new String[]{"привет", "хотел", "бы", "сдела", "тест", "метод"};
        idealLatinText = new String[]{"hello", "this", "is", "method", "test", "methods"};
    }

    @Override
    protected NGramStrategy getNGramStrategy() {
        return new FilteredUnigram(NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM);
    }
}