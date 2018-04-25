package org.ripreal.textclassifier2.storage.data.mapper;

import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;


public class CharacteristicDeserializer extends AbstractEntityDeserializer<MongoCharacteristic> {

    public CharacteristicDeserializer() {
        super();
    }

    public CharacteristicDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MongoCharacteristic newEntity() {
        return new MongoCharacteristic();
    }

}
