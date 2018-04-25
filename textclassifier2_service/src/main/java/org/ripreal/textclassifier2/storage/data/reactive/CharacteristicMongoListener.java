package org.ripreal.textclassifier2.storage.data.reactive;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristicValue;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.model.*;
import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

@RequiredArgsConstructor
public class CharacteristicMongoListener extends AbstractMongoEventListener<Object> {

    private final MongoOperations mongoOperations;

    // EVENT HANDLERS

    @Override
    public void onBeforeSave(BeforeSaveEvent<Object> event) {
        super.onBeforeSave(event);
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {

        Object source = event.getSource();

        if (source instanceof MongoClassifiableText) {
            MongoClassifiableText text = (MongoClassifiableText) source;
            if (text.getCharacteristics() != null) {
                for (MongoCharacteristicValue entry : text.getCharacteristics()) {
                    checkNSaveCharacteristicValue(entry); // checking doubles
                }
            }
        }
        else if (source instanceof MongoCharacteristicValue) {
            MongoCharacteristicValue value = (MongoCharacteristicValue) source;
            if (value.getCharacteristic() != null) {
                checkNSaveCharacteristic(value.getCharacteristic()); // checking doubles
            }
        }
        else if (source instanceof VocabularyWord) {
            checkNSaveVocabulary((MongoVocabularyWord) source);
        }
    }


    // CASCADE SAVINGS

    private void checkNSaveVocabulary(MongoVocabularyWord vocabulary) {
        // prevent doubles
        mongoOperations.findAllAndRemove(
                new Query(Criteria
                        .where("value").is(vocabulary.getValue())
                        .and("ngram").is(vocabulary.getNgram())
                ),
                MongoVocabularyWord.class
        );
    }

    private void checkNSaveCharacteristic(@NonNull MongoCharacteristic MongoCharacteristic) {
        // When being saved from texts rhere is no need to check due MongoCharacteristic
        // name is considered  also a ID field
        mongoOperations.save(MongoCharacteristic);
    }

    private void checkNSaveCharacteristicValue(@NonNull MongoCharacteristicValue valueRequest) {

        MongoCharacteristicValue valueExisting = mongoOperations.findAndModify(
                new Query(Criteria
                        .where("value").is(valueRequest.getValue())
                        .and("characteristic").is(valueRequest.getCharacteristic())
                ),
                Update.update("orderNumber", valueRequest.getOrderNumber()),
                MongoCharacteristicValue.class
        );

        if (valueExisting != null)
            valueRequest.setId(valueExisting.getId());
        else
            mongoOperations.save(valueRequest);
    }

}
