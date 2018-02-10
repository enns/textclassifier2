package org.ripreal.textclassifier2.model.modelimp;

import org.ripreal.textclassifier2.model.*;

import java.util.Map;

public class DefCharacteristicFactory implements CharacteristicFactory {
    public Characteristic newCharacteristic(String name){
        return new DefCharacteristic(name);
    }

    public CharacteristicValue newCharacteristicValue(String value){
        return new DefCharacteristicValue(value);
    }

    public ClassifiableText newClassifiableText(String text){
        return new DefClassifiableText(text);
    }

    public ClassifiableText newClassifiableText(String text, Map<Characteristic, CharacteristicValue> characteristics) {
        return new DefClassifiableText(text, characteristics);
    }

    public VocabularyWord newVocabularyWord(String value){
        return new DefVocabularyWord(value);
    }
}
