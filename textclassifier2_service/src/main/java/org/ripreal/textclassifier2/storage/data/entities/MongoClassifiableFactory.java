package org.ripreal.textclassifier2.storage.data.entities;

import org.ripreal.textclassifier2.model.*;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

public class MongoClassifiableFactory implements ClassifiableFactory {

    @Override
    public Characteristic newCharacteristic(String name) {
        return new MongoCharacteristic(name);
    }

    @Override
    public CharacteristicValue newCharacteristicValue(String value, int orderNumber, Characteristic characteristic) {
        return new MongoCharacteristicValue(value, orderNumber, characteristic);
    }

    @Override
    public MongoVocabularyWord newVocabularyWord(String value) {
        return new MongoVocabularyWord(value, NGramStrategy.NGRAM_TYPES.FILTERED_BIGRAM.toString());
    }

    @Override
    public ClassifiableText newClassifiableText(String text, Set<CharacteristicValue> characteristics) {
        return new MongoClassifiableText(text, characteristics);
    }
}
