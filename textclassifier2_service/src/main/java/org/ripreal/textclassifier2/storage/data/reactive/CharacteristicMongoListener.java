package org.ripreal.textclassifier2.storage.data.reactive;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristicValue;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.model.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@RequiredArgsConstructor
public class CharacteristicMongoListener extends AbstractMongoEventListener<Object> {

    private final MongoOperations mongoOperations;

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
                for (CharacteristicValue entry : text.getCharacteristics()) {
                    checkNSave(entry); // checking doubles
                }
            }
        }
    }

    public void checkNSave(@NonNull CharacteristicValue valueRequest) {

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

        /*

        return mongoOperations.finfindOne(valueRequest).map(existingMovie -> {

            if(movieRequest.getDescription() != null){
                existingMovie.setDescription(movieRequest.getDescription());
            }
            if(movieRequest.getRating() != null){
                existingMovie.setRating(movieRequest.getRating());
            }
            if(movieRequest.getTitle() != null) {
                existingMovie.setTitle(movieRequest.getTitle());
            }

            return existingMovie;

        }).then(movieRepository::save);
        */
    }

}
