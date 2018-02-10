package org.ripreal.textclassifier2.model;

import java.util.Map;

// ABSTRACT FACTORY
public interface CharacteristicFactory {

    Characteristic newCharacteristic(String name);

    CharacteristicValue newCharacteristicValue(String value);

    ClassifiableText newClassifiableText(String text);

    ClassifiableText newClassifiableText(String text, Map<Characteristic, CharacteristicValue> characteristics);

    VocabularyWord newVocabularyWord(String value);
}
