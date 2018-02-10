package org.ripreal.textclassifier2.classifier;

import org.ripreal.textclassifier2.actions.ClassifierAction;
import org.ripreal.textclassifier2.actions.ClassifierEventsDispatcher;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.ngram.NGramStrategy;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.OutputStream;
import java.util.List;

interface ClassifierUnit extends ClassifierEventsDispatcher {

    // PROPERTIES

    Characteristic getCharacteristic();

    List<VocabularyWord> getVocabulary();

    void setVocabulary(List<VocabularyWord> vocabulary);

    NGramStrategy getNGramStrategy();

    // BUILDING

    void subscribe(ClassifierAction action);

    void build(List<ClassifiableText> classifiableTexts);

    // COMPOSITE METHODS

    CharacteristicValue classify(ClassifiableText classifiableText);

    void saveTrainedClassifier(File file);

    void saveTrainedClassifier(OutputStream stream);

    void shutdown();
}
