package org.ripreal.textclassifier2.classifier;

import lombok.*;
import org.ripreal.textclassifier2.actions.ClassifierAction;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.ngram.NGramStrategy;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

@RequiredArgsConstructor
public class ProxyClassifierUnit implements ClassifierUnit{

    private final Characteristic characteristic;
    private final NGramStrategy nGramStrategy;
    @Getter
    @Setter
    private List<VocabularyWord> vocabulary;


    public Classifier init(Classifier unit) {
        return new NeroClassifierUnit(characteristic, nGramStrategy, vocabulary);
    }

    @Override
    public Characteristic getCharacteristic() {
        return null;
    }

    @Override
    public void subscribe(ClassifierAction action) {

    }

    @Override
    public void dispatch(String text) {

    }

    @Override
    public void build(List<ClassifiableText> classifiableTexts) {

    }

    @Override
    public CharacteristicValue classify(ClassifiableText classifiableText) {
        return null;
    }

    @Override
    public void saveTrainedClassifier(File file) {

    }

    @Override
    public void saveTrainedClassifier(OutputStream stream) {

    }

    @Override
    public void shutdown() {

    }
}
