package org.ripreal.textclassifier2.ngram;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

class FilteredUnigram implements NGramStrategy {

    @NonNull
    private final NGRAM_TYPES ngramType;

    FilteredUnigram(NGRAM_TYPES ngramType) {
        this.ngramType = ngramType;
    }

    @Override
    public Set<String> getNGram(String text) {
        // get all significant words
        String[] words = clean(text).split("[ \n\t\r$+<>№=]");

        // remove endings of words
        for (int i = 0; i < words.length; i++) {
            words[i] = PorterStemmer.doStem(words[i]);
        }

        Set<String> uniqueValues = new LinkedHashSet<>(Arrays.asList(words));
        uniqueValues.removeIf(s -> s.equals(""));

        return uniqueValues;
    }

    @Override
    public NGRAM_TYPES getNGramType() {
        return ngramType;
    }

    private String clean(String text) {
        // remove all digits and punctuation marks
        if (text != null) {
            return text.toLowerCase().replaceAll("[\\pP\\d]", " ");
        } else {
            return "";
        }
    }

}