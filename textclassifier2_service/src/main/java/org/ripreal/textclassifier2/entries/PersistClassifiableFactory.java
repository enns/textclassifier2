package org.ripreal.textclassifier2.entries;

import org.ripreal.textclassifier2.model.*;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PersistClassifiableFactory implements ClassifiableFactory {

    @Override
    public Characteristic newCharacteristic(String name) {
        return new PersistCharacteristic(name);
    }

    @Override
    public CharacteristicValue newCharacteristicValue(String value, int orderNumber, Characteristic characteristic) {
        return new PersistCharacteristicValue(value, orderNumber, characteristic);
    }

    @Override
    public PersistVocabularyWord newVocabularyWord(String value) {
        return new PersistVocabularyWord(value, NGramStrategy.NGRAM_TYPES.FILTERED_BIGRAM.toString());
    }

    @Override
    public CharacteristicValuePair newCharacteristicValuePair(Characteristic characteristic, CharacteristicValue characteristicValue) {
        return new PersistCharactValuePair(characteristic, characteristicValue);
    }

    @Override
    public ClassifiableText newClassifiableText(String text) {
        return new PersistClassifiableText(text, new HashSet<>());
    }

    @Override
    public ClassifiableText newClassifiableText(String text, Set<CharacteristicValuePair> characteristics) {
        return new PersistClassifiableText(text, characteristics);
    }
}
