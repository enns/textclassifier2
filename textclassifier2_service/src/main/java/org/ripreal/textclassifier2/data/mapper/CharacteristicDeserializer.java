package org.ripreal.textclassifier2.data.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.data.entries.PersistCharacteristic;
import org.ripreal.textclassifier2.data.entries.PersistCharacteristicValue;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;

import java.io.IOException;

public class CharacteristicDeserializer extends AbstractEntityDeserializer<Characteristic> {

    public CharacteristicDeserializer() {
        super();
    }

    public CharacteristicDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Characteristic newEntity() {
        return new PersistCharacteristic();
    }

}
