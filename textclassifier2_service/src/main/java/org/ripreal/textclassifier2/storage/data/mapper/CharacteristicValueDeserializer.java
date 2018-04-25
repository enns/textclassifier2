package org.ripreal.textclassifier2.storage.data.mapper;

import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristicValue;

public class CharacteristicValueDeserializer extends AbstractEntityDeserializer<MongoCharacteristicValue> {

    public CharacteristicValueDeserializer() {
        super(null);
    }

    public CharacteristicValueDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MongoCharacteristicValue newEntity() {
        return new MongoCharacteristicValue();
    }

}
