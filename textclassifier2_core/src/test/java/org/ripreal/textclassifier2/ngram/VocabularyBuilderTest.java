package org.ripreal.textclassifier2.ngram;

import org.junit.Test;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.model.modelimp.DefClassifiableFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class VocabularyBuilderTest {
    private final NGramStrategy ngram = NGramStrategy.getNGramStrategy(NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM);
    private final ClassifiableFactory characteristicFactory = new DefClassifiableFactory();

    @Test
    public void getVocabulary() throws Exception {

        List<ClassifiableText> classifiableTexts = new ArrayList<>();
        classifiableTexts.add(characteristicFactory.newClassifiableText("qw we"));
        classifiableTexts.add(characteristicFactory.newClassifiableText("er we"));
        classifiableTexts.add(characteristicFactory.newClassifiableText("we rt"));
        classifiableTexts.add(characteristicFactory.newClassifiableText("er rt"));
        classifiableTexts.add(characteristicFactory.newClassifiableText("qw we"));
        classifiableTexts.add(characteristicFactory.newClassifiableText("er we"));
        classifiableTexts.add(characteristicFactory.newClassifiableText("we rt"));
        classifiableTexts.add(characteristicFactory.newClassifiableText("er rt"));

        VocabularyBuilder builder = new VocabularyBuilder(ngram);
        List<VocabularyWord> vocabulary = builder.getVocabulary(classifiableTexts, characteristicFactory);

        assertEquals(vocabulary.size(), 3);
        assertEquals(vocabulary.get(0).getValue(), "rt");
        assertEquals(vocabulary.get(1).getValue(), "er");
        assertEquals(vocabulary.get(2).getValue(), "we");
    }

    @Test
    public void getVocabularyOneValue() throws Exception {
        List<ClassifiableText> classifiableTexts = new ArrayList<>();
        classifiableTexts.add(characteristicFactory.newClassifiableText("qw we"));

        VocabularyBuilder builder = new VocabularyBuilder(ngram);
        List<VocabularyWord> vocabulary = builder.getVocabulary(classifiableTexts, characteristicFactory);

        assertEquals(vocabulary.size(), 0);
    }

    @Test(expected = NullPointerException.class)
    public void getVocabularyNull() throws Exception {
        VocabularyBuilder builder = new VocabularyBuilder(ngram);
        builder.getVocabulary(null, characteristicFactory);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getVocabularyEmpty() throws Exception {
        VocabularyBuilder builder = new VocabularyBuilder(ngram);
        builder.getVocabulary(new ArrayList<>(), characteristicFactory);
    }
}