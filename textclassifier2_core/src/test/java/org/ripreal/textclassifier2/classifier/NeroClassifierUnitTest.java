package org.ripreal.textclassifier2.classifier;

import org.junit.Before;
import org.junit.Test;
import org.ripreal.textclassifier2.model.*;
import org.ripreal.textclassifier2.model.modelimp.DefClassifiableFactory;
import org.ripreal.textclassifier2.ngram.NGramStrategy;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

public class NeroClassifierUnitTest {

    private final File trainedClassifier = new File("./test_db/TestNeuralNetworkClassifier");
    private final NGramStrategy nGramStrategy = NGramStrategy.getNGramStrategy(NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM);
    private NeroClassifierUnit classifier;
    private Characteristic characteristic;
    private List<VocabularyWord> vocabulary;
    private ClassifiableFactory characteristicFactory;

    @Before
    public void init() {
        // create characteristic
        //
        characteristicFactory = new DefClassifiableFactory();

        characteristic = characteristicFactory.newCharacteristic("Method");
        characteristic.addPossibleValue(characteristicFactory.newCharacteristicValue("get", 1, characteristic));
        characteristic.addPossibleValue(characteristicFactory.newCharacteristicValue("set", 2, characteristic));
        characteristic.addPossibleValue(characteristicFactory.newCharacteristicValue("add", 3, characteristic));

        // create vocabulary
        //

        vocabulary = new ArrayList<>();
        vocabulary.add(characteristicFactory.newVocabularyWord("their"));
        vocabulary.add(characteristicFactory.newVocabularyWord("specified"));
        vocabulary.add(characteristicFactory.newVocabularyWord("that"));
        vocabulary.add(characteristicFactory.newVocabularyWord("and"));
        vocabulary.add(characteristicFactory.newVocabularyWord("shifts"));
        vocabulary.add(characteristicFactory.newVocabularyWord("subsequent"));
        vocabulary.add(characteristicFactory.newVocabularyWord("if"));
        vocabulary.add(characteristicFactory.newVocabularyWord("element"));
        vocabulary.add(characteristicFactory.newVocabularyWord("adds"));
        vocabulary.add(characteristicFactory.newVocabularyWord("in"));
        vocabulary.add(characteristicFactory.newVocabularyWord("replaces"));
        vocabulary.add(characteristicFactory.newVocabularyWord("one"));
        vocabulary.add(characteristicFactory.newVocabularyWord("this"));
        vocabulary.add(characteristicFactory.newVocabularyWord("optional"));
        vocabulary.add(characteristicFactory.newVocabularyWord("right"));
        vocabulary.add(characteristicFactory.newVocabularyWord("list"));
        vocabulary.add(characteristicFactory.newVocabularyWord("any"));
        vocabulary.add(characteristicFactory.newVocabularyWord("the"));
        vocabulary.add(characteristicFactory.newVocabularyWord("with"));
        vocabulary.add(characteristicFactory.newVocabularyWord("indices"));
        vocabulary.add(characteristicFactory.newVocabularyWord("at"));
        vocabulary.add(characteristicFactory.newVocabularyWord("currently"));
        vocabulary.add(characteristicFactory.newVocabularyWord("elements"));
        vocabulary.add(characteristicFactory.newVocabularyWord("returns"));
        vocabulary.add(characteristicFactory.newVocabularyWord("position"));
        vocabulary.add(characteristicFactory.newVocabularyWord("to"));
        vocabulary.add(characteristicFactory.newVocabularyWord("operation"));
        vocabulary.add(characteristicFactory.newVocabularyWord("inserts"));

        classifier = new NeroClassifierUnit(trainedClassifier, characteristic, vocabulary, nGramStrategy);
    }

    @Test(expected = NullPointerException.class)
    public void nullCharacteristic() {
        new NeroClassifierUnit(trainedClassifier, null, vocabulary, nGramStrategy);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyCharacteristic() {
        new NeroClassifierUnit(trainedClassifier, characteristicFactory.newCharacteristic("Test"), vocabulary, nGramStrategy);
    }

    @Test(expected = NullPointerException.class)
    public void nullVocabulary() {
        new NeroClassifierUnit(trainedClassifier, characteristic, null, nGramStrategy);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyVocabulary() {
        new NeroClassifierUnit(trainedClassifier, characteristic, new ArrayList<>(), nGramStrategy);
    }

    @Test(expected = NullPointerException.class)
    public void nullNGram() {
        new NeroClassifierUnit(trainedClassifier, characteristic, vocabulary, null);
    }

    @Test
    public void classify() throws Exception {

        ClassifiableText ctGet = characteristicFactory.newClassifiableText("Returns the element at the specified position in this list");
        Optional<CharacteristicValue> cvGet = classifier.classify(ctGet);

        assertEquals(cvGet.get().getOrderNumber(), 1);
        assertEquals(cvGet.get().getValue(), "get");

        //

        ClassifiableText ctSet = characteristicFactory.newClassifiableText("Replaces the element at the specified position in this list with the specified element (optional operation)");
        Optional<CharacteristicValue> cvSet = classifier.classify(ctSet);

        assertEquals(cvSet.get().getOrderNumber(), 2);
        assertEquals(cvSet.get().getValue(), "set");

        //

        ClassifiableText ctAdd = characteristicFactory.newClassifiableText("Inserts the specified element at the specified position in this list (optional operation). Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices)");
        Optional<CharacteristicValue> cvAdd = classifier.classify(ctAdd);

        assertEquals(cvAdd.get().getOrderNumber(), 3);
        assertEquals(cvAdd.get().getValue(), "add");

        characteristic.getPossibleValues().clear();

        List<ClassifiableText> classifiableTexts = new ArrayList<>();

        Set<CharacteristicValue> characteristics = new HashSet<>();
        characteristics.add(characteristicFactory.newCharacteristicValue("add", 56, characteristic));
        classifiableTexts.add(characteristicFactory.newClassifiableText("that at returns", characteristics));

        Optional<CharacteristicValue> val = classifier.classify(classifiableTexts.get(0));
        assertTrue(!val.isPresent());

    }

    @Test
    public void saveClassifier() throws Exception {
        classifier.saveClassifier(new File("./test_db/TestSave"));
        assertEquals(new File("./test_db/TestSave/NeroClassifierUnit_save").delete(), true);
    }

    @Test
    public void build() throws Exception {

        List<ClassifiableText> classifiableTexts = new ArrayList<>();

        Set<CharacteristicValue> characteristics = new HashSet<>();
        characteristics.add(characteristicFactory.newCharacteristicValue("get", 1, characteristic));
        classifiableTexts.add(characteristicFactory.newClassifiableText("shifts right any this operation", characteristics));

        characteristics = new HashSet<>();
        characteristics.add(characteristicFactory.newCharacteristicValue("add", 3, characteristic));

        classifiableTexts.add(characteristicFactory.newClassifiableText("that at returns", characteristics));

        // make sure classifier is stupid
        //

        assertNotEquals(classifier.classify(classifiableTexts.get(0)).get().getValue(), "get");
        assertNotEquals(classifier.classify(classifiableTexts.get(1)).get().getValue(), "add");

        // train
        classifier.build(classifiableTexts);

        // make sure classifier became smart
        //

        assertEquals(classifier.classify(classifiableTexts.get(0)).get().getValue(), "get");
        assertEquals(classifier.classify(classifiableTexts.get(1)).get().getValue(), "add");
        assertEquals(classifier.classify(characteristicFactory.newClassifiableText("shifts right sdawwda any this operation")).get().getValue(), "get");
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildZeroOrderNumber() {
        List<ClassifiableText> classifiableTexts = new ArrayList<>();
        
        Set<CharacteristicValue> characteristics = new HashSet<>();
        CharacteristicValue pair = characteristicFactory.newCharacteristicValue
            ("add", 0, characteristic);
        characteristics.add(pair);
        classifiableTexts.add(characteristicFactory.newClassifiableText("that at returns", characteristics));

        classifier.build(classifiableTexts);
    }

    @Test
    public void shutdown() throws Exception {
        classifier.shutdown();
    }

}