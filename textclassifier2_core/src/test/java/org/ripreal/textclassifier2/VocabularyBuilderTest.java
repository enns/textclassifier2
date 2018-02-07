package org.ripreal.textclassifier2;

import org.junit.Test;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.ngram.FilteredUnigram;
import org.ripreal.textclassifier2.ngram.NGramStrategy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class VocabularyBuilderTest {
    private final NGramStrategy ngram = NGramStrategy.getNGramStrategy(NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM);

    @Test
    public void getVocabulary() throws Exception {
        List<ClassifiableText> classifiableTexts = new ArrayList<>();
        classifiableTexts.add(new ClassifiableText("qw we"));
        classifiableTexts.add(new ClassifiableText("er we"));
        classifiableTexts.add(new ClassifiableText("we rt"));
        classifiableTexts.add(new ClassifiableText("er rt"));
        classifiableTexts.add(new ClassifiableText("qw we"));
        classifiableTexts.add(new ClassifiableText("er we"));
        classifiableTexts.add(new ClassifiableText("we rt"));
        classifiableTexts.add(new ClassifiableText("er rt"));

        List<VocabularyWord> vocabulary = NGramStrategy.getVocabulary(ngram, classifiableTexts);

        assertEquals(vocabulary.size(), 3);
        assertEquals(vocabulary.get(0).getValue(), "rt");
        assertEquals(vocabulary.get(1).getValue(), "er");
        assertEquals(vocabulary.get(2).getValue(), "we");
    }

    @Test
    public void getVocabularyOneValue() throws Exception {
        List<ClassifiableText> classifiableTexts = new ArrayList<>();
        classifiableTexts.add(new ClassifiableText("qw we"));

        List<VocabularyWord> vocabulary = NGramStrategy.getVocabulary(ngram, classifiableTexts);

        assertEquals(vocabulary.size(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getVocabularyNull() throws Exception {
        NGramStrategy.getVocabulary(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getVocabularyEmpty() throws Exception {
        NGramStrategy.getVocabulary(ngram, new ArrayList<>());
    }
}