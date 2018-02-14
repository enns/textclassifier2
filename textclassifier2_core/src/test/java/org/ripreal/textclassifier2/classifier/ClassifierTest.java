package org.ripreal.textclassifier2.classifier;

import org.junit.Before;
import org.junit.Test;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicFactory;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.model.modelimp.DefCharacteristicFactory;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.textreaders.ClassifiableReaderBuilder;
import org.ripreal.textclassifier2.textreaders.ClassifiableTextReader;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ClassifierTest {

    private final File trainedClassifier = new File("./test_db/TestNeuralNetworkClassifier");
    private final NGramStrategy nGramStrategy = NGramStrategy.getNGramStrategy(NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM);
    private Classifier classifier;
    private Characteristic characteristic;
    private List<VocabularyWord> vocabulary;
    private final CharacteristicFactory characteristicFactory = new DefCharacteristicFactory();
    private final ClassifiableTextReader reader = new ClassifiableTextReader();

    @Before
    public void loadFromFile() {
        // create characteristic
        //

        characteristic = characteristicFactory.newCharacteristic("Method");
        characteristic.addPossibleValue(characteristicFactory.newCharacteristicValue("get"));
        characteristic.addPossibleValue(characteristicFactory.newCharacteristicValue("set"));
        characteristic.addPossibleValue(characteristicFactory.newCharacteristicValue("add"));

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

        ClassifierBuilder builder = ClassifierBuilder.fromReader(reader, characteristicFactory);
        builder.addNeroClassifierUnit(trainedClassifier, characteristic.getName(), vocabulary, nGramStrategy);

    }

    @Test(expected = IllegalArgumentException.class)
    public void nonexistentFile() {
        ClassifierBuilder
            .fromReader((builder) -> builder.newExcelFileReader(
                new File("./test_db/nonexistentFile"), 1),
                new DefCharacteristicFactory())
            .build();
    }
    /*
    @Test(expected = IllegalArgumentException.class)
    public void nullCharacteristic() {
        new ClassifierUnit(trainedClassifier, null, vocabulary, nGramStrategy);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyCharacteristic() {
        new ClassifierUnit(trainedClassifier, new Characteristic("Test"), vocabulary, nGramStrategy);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullVocabulary() {
        new ClassifierUnit(trainedClassifier, characteristic, null, nGramStrategy);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyVocabulary() {
        new ClassifierUnit(trainedClassifier, characteristic, new ArrayList<>(), nGramStrategy);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullNGram() {
        new ClassifierUnit(trainedClassifier, characteristic, vocabulary, null);
    }

    @Test
    public void classify() throws Exception {
        ClassifiableText ctGet = new ClassifiableText("Returns the element at the specified position in this list");
        CharacteristicValue cvGet = classifier.classify(ctGet);

        assertEquals(cvGet.getOrderNumber(), 1);
        assertEquals(cvGet.getValue(), "get");

        //

        ClassifiableText ctSet = new ClassifiableText("Replaces the element at the specified position in this list with the specified element (optional operation)");
        CharacteristicValue cvSet = classifier.classify(ctSet);

        assertEquals(cvSet.getOrderNumber(), 2);
        assertEquals(cvSet.getValue(), "set");

        //

        ClassifiableText ctAdd = new ClassifiableText("Inserts the specified element at the specified position in this list (optional operation). Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices)");
        CharacteristicValue cvAdd = classifier.classify(ctAdd);

        assertEquals(cvAdd.getOrderNumber(), 3);
        assertEquals(cvAdd.getValue(), "add");
    }

    @Test
    public void saveTrainedClassifier() throws Exception {
        classifier.saveTrainedClassifier(new File("./test_db/TestSave"));
        assertEquals(new File("./test_db/TestSave").delete(), true);
    }

    @Test
    public void getCharacteristicName() throws Exception {
        assertEquals(classifier.getCharacteristic().getName(), "Method");
    }

    @Test
    public void train() throws Exception {
        // create list for train
        //

        List<ClassifiableText> classifiableTexts = new ArrayList<>();

        Map<Characteristic, CharacteristicValue> characteristics = new HashMap<>();
        characteristics.put(new Characteristic("Method"), new CharacteristicValue(1, "get"));
        classifiableTexts.add(new ClassifiableText("shifts right any this operation", characteristics));

        characteristics = new HashMap<>();
        characteristics.put(new Characteristic("Method"), new CharacteristicValue(3, "add"));
        classifiableTexts.add(new ClassifiableText("that at returns", characteristics));

        // make sure classifier is stupid
        //

        assertNotEquals(classifier.classify(classifiableTexts.get(0)).getValue(), "get");
        assertNotEquals(classifier.classify(classifiableTexts.get(1)).getValue(), "add");

        // train
        classifier.build(classifiableTexts);

        // make sure classifier became smart
        //

        assertEquals(classifier.classify(classifiableTexts.get(0)).getValue(), "get");
        assertEquals(classifier.classify(classifiableTexts.get(1)).getValue(), "add");
        assertEquals(classifier.classify(new ClassifiableText("shifts right sdawwda any this operation")).getValue(), "get");
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals(classifier.toString(), "MethodNeuralNetworkClassifier");
    }

    @Test
    public void shutdown() throws Exception {
        ClassifierUnit.shutdown();
    }
    */
}