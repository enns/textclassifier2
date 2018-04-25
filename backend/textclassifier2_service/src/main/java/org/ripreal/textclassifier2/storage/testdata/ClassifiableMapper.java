package org.ripreal.textclassifier2.storage.testdata;

import org.ripreal.textclassifier2.model.*;
import org.ripreal.textclassifier2.model.modelimp.DefCharacteristic;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristicValue;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassifiableMapper {

    @Autowired
    private ClassifiableFactory textFactory;

    // TO CLASSIFIABLE MODEL

    public ClassifiableMapper(ClassifiableFactory textFactory) {
        this.textFactory = textFactory;
    }

    public Characteristic toCharacteristic(MongoCharacteristic mongoCharacteristic) {
        Characteristic characteristic = textFactory.newCharacteristic(mongoCharacteristic.getName());
        mongoCharacteristic.getPossibleValues().forEach(this::toCharacteristicValues);
        return characteristic;
    }

    public Set<Characteristic> toCharacteristic(Set<MongoCharacteristic> characteristics) {
        return characteristics.stream().map(this::toCharacteristic).collect(Collectors.toSet());
    }

    public CharacteristicValue toCharacteristicValues(MongoCharacteristicValue charValue) {
        return textFactory.newCharacteristicValue(
                charValue.getValue(),
                charValue.getOrderNumber(),
                toCharacteristicWithoutPossibleValues(charValue.getCharacteristic())
        );
    }

    public Set<CharacteristicValue> toCharacteristicValues(Set<MongoCharacteristicValue> mongoCharVals) {
        return mongoCharVals.stream().map(this::toCharacteristicValues).collect(Collectors.toSet());
    }

    public ClassifiableText toClassifiableText(MongoClassifiableText mongoText) {
        return textFactory
            .newClassifiableText(
                    mongoText.getText(),
                    mongoText.getCharacteristics().stream().map(this::toCharacteristicValues).collect(Collectors.toSet())
            );
    }

    public List<ClassifiableText> toClassifiableText(List<MongoClassifiableText> texts) {
        return texts.stream().map(this::toClassifiableText).collect(Collectors.toList());
    }

    public VocabularyWord toVocabularyWord(MongoVocabularyWord mongoWord) {
        return textFactory.newVocabularyWord(mongoWord.getValue());
    }

    public Set<VocabularyWord> toVocabularyWord(Set<MongoVocabularyWord> mongoWord) {
        return mongoWord.stream().map(this::toVocabularyWord).collect(Collectors.toSet());
    }

    // FROM CLASSIFIABLE MODEL

    public MongoClassifiableText fromClassifiableText(ClassifiableText text) {
        MongoClassifiableText mongoText = new MongoClassifiableText();
        mongoText.setText(text.getText());
        mongoText.setCharacteristics(
            text.getCharacteristics().stream().map(this::fromCharacteristicValues).collect(Collectors.toSet())
        );
        return mongoText;
    }

    public List<MongoClassifiableText> fromClassifiableText(List<ClassifiableText> texts) {
        return texts.stream().map(this::fromClassifiableText).collect(Collectors.toList());
    }

    public MongoCharacteristic fromCharacteristic(Characteristic mongoCharacteristic) {
        MongoCharacteristic characteristic = new MongoCharacteristic(mongoCharacteristic.getName());
        mongoCharacteristic.getPossibleValues().forEach(this::fromCharacteristicValues);
        return characteristic;
    }

    public Set<MongoCharacteristic> fromCharacteristic(Set<Characteristic> characteristics) {
        return characteristics.stream().map(this::fromCharacteristic).collect(Collectors.toSet());
    }

    public MongoCharacteristicValue fromCharacteristicValues(CharacteristicValue charValue) {
        MongoCharacteristicValue mongoCharVal = new MongoCharacteristicValue();
        mongoCharVal.setValue(charValue.getValue());
        mongoCharVal.setOrderNumber(charValue.getOrderNumber());
        mongoCharVal.setCharacteristic(fromCharacteristicWithoutPossibleValues(charValue.getCharacteristic()));
        return mongoCharVal;
    }

    public Set<MongoCharacteristicValue> fromCharacteristicValues(Set<CharacteristicValue> mongoCharVals) {
        return mongoCharVals.stream().map(this::fromCharacteristicValues).collect(Collectors.toSet());
    }

    private Characteristic toCharacteristicWithoutPossibleValues(MongoCharacteristic mongoCharacteristic) {
        return textFactory.newCharacteristic(mongoCharacteristic.getName());
    }

    private MongoCharacteristic fromCharacteristicWithoutPossibleValues(Characteristic characteristic) {
        return new MongoCharacteristic(characteristic.getName());
    }

    public MongoVocabularyWord fromVocabularyWord(VocabularyWord word) {
        return new MongoVocabularyWord(word.getValue(), word.getNgram());
    }

    public Set<MongoVocabularyWord> fromVocabularyWord(Set<VocabularyWord> word) {
        return word.stream().map(this::fromVocabularyWord).collect(Collectors.toSet());
    }

}
