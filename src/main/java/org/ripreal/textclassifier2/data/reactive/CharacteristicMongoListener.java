package org.ripreal.textclassifier2.data.reactive;

import com.mongodb.client.result.UpdateResult;
import javafx.util.Pair;
import org.ripreal.textclassifier2.model.CharactValuePair;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Map;

public class CharacteristicMongoListener extends AbstractMongoEventListener<Object> {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeSave(BeforeSaveEvent<Object> event) {
        super.onBeforeSave(event);
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {

        Object source = event.getSource();
        /*
        if (source instanceof Characteristic) {
            for(CharacteristicValue element : ((Characteristic) source).getPossibleValues())
                repository.save(element);
        }
        */
        if (source instanceof ClassifiableText) {
            ClassifiableText text = (ClassifiableText) source;
            if (text.getCharacteristics() != null) {
                for(CharactValuePair entry : text.getCharacteristics()) {
                    mongoOperations.save(entry.getKey());
                    //mongoOperations.save(entry.getVal());
                    checkNSave(entry.getVal());
                }
            }

            /*
            // CharacteristicVal in CharactValuePair may be undefined due the json to object serialization.
            // In that case set it manually from a list of the possible values.
            for(CharactValuePair entry : ((ClassifiableText) source).getCharacteristics()) {
               for(CharacteristicValue possible : entry.getKey().getPossibleValues()) {
                   if (entry.getVal().equals(possible)) {
                       entry.getVal().setId(possible.getId());
                   }
               }
                //repository.save();
            }
            */
        }

    }

    public void checkNSave(CharacteristicValue valueRequest) {
        // TODO VALUE CERTAINLY SHOULD NoT BE ID
        UpdateResult res = mongoOperations.upsert(
            new Query(Criteria
                .where("value").is(valueRequest.getValue())
                .and("characteristic").is(valueRequest.getCharacteristic())
            ),
            Update.update("orderNumber", valueRequest.getOrderNumber()),
            CharacteristicValue.class
        );
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

    @Override
    public void onAfterDelete(AfterDeleteEvent<Object> event) {
        Object source = event.getSource();
        /*
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
        */
    }
}
