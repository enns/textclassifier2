package org.ripreal.textclassifier2.storage.data.mapper;

import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.model.Characteristic;

public class CharacteristicDeserializer extends AbstractEntityDeserializer<Characteristic> {

    public CharacteristicDeserializer() {
        super();
    }

    public CharacteristicDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Characteristic newEntity() {
        return new MongoCharacteristic();
    }

}
