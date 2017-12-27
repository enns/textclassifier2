package org.ripreal.textclassifier2.data.reactive;

import javafx.util.Pair;
import org.ripreal.textclassifier2.model.CharactValuePair;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;

import java.util.Map;

public class CharacteristicMongoListener extends AbstractMongoEventListener<Object> {

    @Autowired
    private MongoOperations repository;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        if (source instanceof Characteristic) {
            for(CharacteristicValue element : ((Characteristic) source).getPossibleValues())
                repository.save(element);
        }
        else if (source instanceof ClassifiableText) {
            for(CharactValuePair entry : ((ClassifiableText) source).getCharacteristics()) {
                repository.save(entry.getKey());
                repository.save(entry.getVal());
            }

        }
    }




}
