package org.ripreal.textclassifier2.model;

import java.util.Map;

public class PersistClassifiableFactory {

    public Characteristic newCharacteristic(String name) {
        return new Characteristic(name);
    }

    public CharacteristicValue newCharacteristicValue(String value, int orderNumber, Characteristic characteristic) {
        return new CharacteristicValue(value, orderNumber, characteristic);
    }

    public ClassifiableText newClassifiableText(String text) {
        return newClassifiableText(text, null);
    }

    public ClassifiableText newClassifiableText(String text, Map<Characteristic, CharacteristicValue> characteristics) {
        return null;
                //new ClassifiableText(CLASSIFIABLE_TEXT_ID_INCREMENT++, text, characteristics);
    }

    public VocabularyWord newVocabularyWord(String value) {
        return new VocabularyWord(value);
    }
}
