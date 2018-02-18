package org.ripreal.textclassifier2.model.modelimp;

import org.ripreal.textclassifier2.model.*;

import java.util.Map;

public class DefCharacteristicFactory implements CharacteristicFactory {

    // Increment this var manually each time when a new characteristic is produced.
    // Normally you would implement auto-increment and enforced it by database property.
    private int VOCABULARY_ID_INCREMENT = 1;
    private int CLASSIFIABLE_TEXT_ID_INCREMENT = 1;

    public Characteristic newCharacteristic(String name){
        return new DefCharacteristic(name);
    }

    public CharacteristicValue newCharacteristicValue(String value, int orderNumber, Characteristic characteristic) {
        return new DefCharacteristicValue(value, orderNumber, characteristic);
    }

    public ClassifiableText newClassifiableText(String text){
        return newClassifiableText(text, null);
    }

    public ClassifiableText newClassifiableText(String text, Map<Characteristic, CharacteristicValue> characteristics) {
        return new DefClassifiableText(CLASSIFIABLE_TEXT_ID_INCREMENT++, text, characteristics);
    }

    public VocabularyWord newVocabularyWord(String value){
        return new DefVocabularyWord(VOCABULARY_ID_INCREMENT++, value);
    }
}
