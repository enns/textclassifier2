package org.ripreal.textclassifier2.model;

import java.util.Map;

// ABSTRACT FACTORY
public interface ClassifiableFactory {

    Characteristic newCharacteristic(String name);

    CharacteristicValue newCharacteristicValue(String value, int orderNumber, Characteristic characteristic);

    ClassifiableText newClassifiableText(String text);

    ClassifiableText newClassifiableText(String text, Map<Characteristic, CharacteristicValue> characteristics);

    VocabularyWord newVocabularyWord(String value);
}
