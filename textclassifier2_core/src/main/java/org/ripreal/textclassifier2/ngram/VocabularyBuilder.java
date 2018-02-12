package org.ripreal.textclassifier2.ngram;

import org.ripreal.textclassifier2.model.CharacteristicFactory;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VocabularyBuilder {
    private final NGramStrategy nGramStrategy;

    public VocabularyBuilder(@NotNull NGramStrategy nGramStrategy) {
        this.nGramStrategy = nGramStrategy;
    }

    public List<VocabularyWord> getVocabulary(@NotEmpty List<ClassifiableText> classifiableTexts, @NotNull CharacteristicFactory factory) {

        Map<String, Integer> uniqueValues = new HashMap<>();

        // count frequency of use each word (converted to n-gram) from all Classifiable Texts
        //

        for (ClassifiableText classifiableText : classifiableTexts) {
            for (String word : nGramStrategy.getNGram(classifiableText.getText())) {
                if (uniqueValues.containsKey(word)) {
                    // increase counter
                    uniqueValues.put(word, uniqueValues.get(word) + 1);
                } else {
                    // add new word
                    uniqueValues.put(word, 1);
                }
            }
        }

        // convert uniqueValues to Vocabulary, excluding infrequent
        //

        List<VocabularyWord> vocabulary = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : uniqueValues.entrySet()) {
            if (entry.getValue() > 3) {
                vocabulary.add(factory.newVocabularyWord(entry.getKey()));
            }
        }

        // todo: throw exception if vocabulary is empty
        return vocabulary;
    }
}