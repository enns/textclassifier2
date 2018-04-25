package org.ripreal.textclassifier2.storage.data.mapper;

import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import org.ripreal.textclassifier2.model.VocabularyWord;

public class VocabularyWordDeserializer extends AbstractEntityDeserializer<MongoVocabularyWord> {

    public VocabularyWordDeserializer() {
        super();
    }

    public VocabularyWordDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MongoVocabularyWord newEntity() {
        return new MongoVocabularyWord();
    }

}
