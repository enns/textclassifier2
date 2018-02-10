package org.ripreal.textclassifier2.classifier;

import lombok.Getter;
import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.Propagation;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.persist.PersistError;
import org.ripreal.textclassifier2.actions.ClassifierAction;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.model.modelimp.DefVocabularyWord;
import org.ripreal.textclassifier2.ngram.NGramStrategy;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.encog.persist.EncogDirectoryPersistence.loadObject;
import static org.encog.persist.EncogDirectoryPersistence.saveObject;

// todo: add other types of Classifiers (Naive Bayes classifier for example)
class NeroClassifierUnit implements ClassifierUnit {

    @Getter
    private  Characteristic characteristic;
    @Getter
    private List<VocabularyWord> vocabulary;
    private int inputLayerSize;
    private int outputLayerSize;
    private BasicNetwork network;
    private NGramStrategy nGramStrategy;
    private List<ClassifierAction> listeners = new ArrayList<>();

    public NeroClassifierUnit(File trainedNetwork, @NotNull Characteristic characteristic, @NotNull List<VocabularyWord> vocabulary, @NotNull NGramStrategy nGramStrategy) {
        this(characteristic, nGramStrategy);
        setVocabulary(vocabulary);
        initInternal(trainedNetwork);
    }

    NeroClassifierUnit(@NotNull Characteristic characteristic, @NotNull NGramStrategy nGramStrategy) {
        this.characteristic = characteristic;
        this.outputLayerSize = characteristic.getPossibleValues().size();
        this.nGramStrategy = nGramStrategy;
    }

    public NGramStrategy getNGramStrategy() {
        return nGramStrategy;
    }

    private void initInternal(File trainedNetwork) {

        if (this.characteristic == null ||
                this.characteristic.getName().equals("") ||
                this.characteristic.getPossibleValues() == null ||
                this.characteristic.getPossibleValues().size() == 0 ||
                this.nGramStrategy == null) {
            throw new IllegalArgumentException();
        }

        if (this.network == null) {
            this.network = createNeuralNetwork();
        } else {
            // load neural network from file
            try {
                this.network = (BasicNetwork) loadObject(trainedNetwork);
            } catch (PersistError e) {
                throw new IllegalArgumentException();
            }
        }
    }

    public void shutdown() {
        Encog.getInstance().shutdown();
    }

    public CharacteristicValue classify(ClassifiableText classifiableText) {
        double[] output = new double[outputLayerSize];

        // calculate output vector
        network.compute(getTextAsVectorOfWords(classifiableText), output);
        Encog.getInstance().shutdown();

        return convertVectorToCharacteristic(output);
    }

    public void saveTrainedClassifier(File file) {
        saveObject(file, network);
        dispatch("Trained Classifier for '" + characteristic.getName() + "' characteristic saved. Wait...");
    }

    public void saveTrainedClassifier(OutputStream stream) {
        saveObject(stream, network);
        dispatch("Trained Classifier for '" + characteristic.getName() + "' characteristic saved. Wait...");
    }

    public void build(List<ClassifiableText> classifiableTexts) {

        initInternal(null);
        // prepare input and ideal vectors
        // input <- ClassifiableText text vector
        // ideal <- characteristicValue vector
        //

        double[][] input = getInput(classifiableTexts);
        double[][] ideal = getIdeal(classifiableTexts);

        // train
        //

        Propagation train = new ResilientPropagation(network, new BasicMLDataSet(input, ideal));
        train.setThreadCount(16);

        // todo: throw exception if iteration count more than 1000
        do {
            train.iteration();
            dispatch("Training Classifier for '" + characteristic.getName() + "' characteristic. Errors: " + String.format("%.2f", train.getError() * 100) + "%. Wait...");
        } while (train.getError() > 0.01);

        train.finishTraining();
        dispatch("Classifier for '" + characteristic.getName() + "' characteristic trained. Wait...");
    }

    public void setVocabulary(List<VocabularyWord> vocabulary) {
        this.vocabulary = vocabulary;
        this.inputLayerSize = this.vocabulary.size();
    }

    private BasicNetwork createNeuralNetwork() {
        BasicNetwork network = new BasicNetwork();

        // input layer
        network.addLayer(new BasicLayer(null, true, inputLayerSize));

        // hidden layer
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, inputLayerSize / 6));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, inputLayerSize / 6 / 4));

        // output layer
        network.addLayer(new BasicLayer(new ActivationSigmoid(), false, outputLayerSize));

        network.getStructure().finalizeStructure();
        network.reset();

        return network;
    }

    private CharacteristicValue convertVectorToCharacteristic(double[] vector) {
        int idOfMaxValue = getIdOfMaxValue(vector);

        // find CharacteristicValue with found Id
        //

        for (CharacteristicValue c : characteristic.getPossibleValues()) {
            if (c.getOrderNumber() == idOfMaxValue) {
                return c;
            }
        }

        return null;
    }

    private int getIdOfMaxValue(double[] vector) {
        int indexOfMaxValue = 0;
        double maxValue = vector[0];

        for (int i = 1; i < vector.length; i++) {
            if (vector[i] > maxValue) {
                maxValue = vector[i];
                indexOfMaxValue = i;
            }
        }

        return indexOfMaxValue + 1;
    }

    private double[][] getInput(List<ClassifiableText> classifiableTexts) {
        double[][] input = new double[classifiableTexts.size()][inputLayerSize];

        // convert all classifiable texts to vectors
        //

        int i = 0;

        for (ClassifiableText classifiableText : classifiableTexts) {
            input[i++] = getTextAsVectorOfWords(classifiableText);
        }

        return input;
    }

    private double[][] getIdeal(List<ClassifiableText> classifiableTexts) {
        double[][] ideal = new double[classifiableTexts.size()][outputLayerSize];

        // convert all classifiable text characteristics to vectors
        //

        int i = 0;

        for (ClassifiableText classifiableText : classifiableTexts) {
            ideal[i++] = getCharacteristicAsVector(classifiableText);
        }

        return ideal;
    }

    // example:
    // count = 5; id = 4;
    // vector = {0, 0, 0, 1, 0}
    private double[] getCharacteristicAsVector(ClassifiableText classifiableText) {
        double[] vector = new double[outputLayerSize];
        vector[classifiableText.getCharacteristicValue(characteristic.getName()).getOrderNumber() - 1] = 1;
        return vector;
    }

    private double[] getTextAsVectorOfWords(ClassifiableText classifiableText) {
        double[] vector = new double[inputLayerSize];

        // convert text to nGramStrategy
        Set<String> uniqueValues = nGramStrategy.getNGram(classifiableText.getText());

        // create vector
        //

        for (String word : uniqueValues) {
            VocabularyWord vw = findWordInVocabulary(word);

            if (vw != null) { // word found in vocabulary
                vector[vw.getId() - 1] = 1;
            }
        }

        return vector;
    }

    private VocabularyWord findWordInVocabulary(String word) {
        try {
            // todo: need more effective searching method
            return vocabulary.get(vocabulary.indexOf(new DefVocabularyWord(word)));
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return characteristic.getName() + "NeuralNetworkClassifier";
    }

    @Override
    public void subscribe(ClassifierAction action) {
        listeners.add(action);
    }

    @Override
    public void dispatch(String text) {
        listeners.forEach(action -> action.dispatch(ClassifierAction.EventTypes.NERO_CLASSIFIER_EVENT, text));
    }
}