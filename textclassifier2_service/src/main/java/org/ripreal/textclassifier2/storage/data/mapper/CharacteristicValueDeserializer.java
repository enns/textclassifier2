package org.ripreal.textclassifier2.storage.data.mapper;

import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristicValue;
import org.ripreal.textclassifier2.model.CharacteristicValue;

public class CharacteristicValueDeserializer extends AbstractEntityDeserializer<CharacteristicValue> {

    public CharacteristicValueDeserializer() {
        super(null);
    }

    public CharacteristicValueDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CharacteristicValue newEntity() {
        return new MongoCharacteristicValue();
    }

}
