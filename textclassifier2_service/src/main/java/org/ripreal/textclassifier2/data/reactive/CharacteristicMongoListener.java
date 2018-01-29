package org.ripreal.textclassifier2.data.reactive;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.CharactValuePair;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
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

        if (source instanceof ClassifiableText) {
            ClassifiableText text = (ClassifiableText) source;
            if (text.getCharacteristics() != null) {
                for (CharactValuePair entry : text.getCharacteristics()) {
                    mongoOperations.save(entry.getKey());
                    checkNSave(entry.getVal()); // checking doubles
                }
            }
        }

    }

    public void checkNSave(CharacteristicValue valueRequest) {

        CharacteristicValue valueExisting = mongoOperations.findAndModify(
                new Query(Criteria
                        .where("value").is(valueRequest.getValue())
                        .and("characteristic").is(valueRequest.getCharacteristic())
                ),
                Update.update("orderNumber", valueRequest.getOrderNumber()),
                CharacteristicValue.class
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
