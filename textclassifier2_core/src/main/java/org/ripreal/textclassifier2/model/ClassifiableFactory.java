package org.ripreal.textclassifier2.model;

import org.ripreal.textclassifier2.model.modelimp.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

// ABSTRACT FACTORY
public interface ClassifiableFactory {

    Characteristic newCharacteristic(String name);

    CharacteristicValue newCharacteristicValue(String value, int orderNumber, Characteristic characteristic);

    VocabularyWord newVocabularyWord(String value);

    ClassifiableText newClassifiableText(String text);

    ClassifiableText newClassifiableText(String text, Set<CharacteristicValue> characteristics);

}
