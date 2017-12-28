package org.ripreal.textclassifier2.data.reactive;

import javafx.util.Pair;
import org.ripreal.textclassifier2.model.CharactValuePair;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.*;

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
                //repository.save(entry.getVal());
            }
        }
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<Object> event) {
        Object source = event.getSource();
        if (source instanceof Characteristic) {
            for(CharacteristicValue element : ((Characteristic) source).getPossibleValues())
                repository.remove(element);
        }
        else if (source instanceof ClassifiableText) {
            for(CharactValuePair entry : ((ClassifiableText) source).getCharacteristics()) {
                repository.remove(entry.getKey());
                repository.remove(entry.getVal());
            }
        }
    }
}
