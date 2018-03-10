package org.ripreal.textclassifier2.data.mapper;

import org.ripreal.textclassifier2.data.entries.PersistCharacteristic;
import org.ripreal.textclassifier2.data.entries.PersistVocabularyWord;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.VocabularyWord;

public class VocabularyWordDeserializer extends AbstractEntityDeserializer<VocabularyWord> {

    public VocabularyWordDeserializer() {
        super();
    }

    public VocabularyWordDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public VocabularyWord newEntity() {
        return new PersistVocabularyWord();
    }

}
