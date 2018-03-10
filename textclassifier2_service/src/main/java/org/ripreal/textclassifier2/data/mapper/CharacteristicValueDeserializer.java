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
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.Field;

public class CharacteristicValueDeserializer extends AbstractEntityDeserializer<CharacteristicValue> {

    public CharacteristicValueDeserializer() {
        super(null);
    }

    public CharacteristicValueDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CharacteristicValue newEntity() {
        return new PersistCharacteristicValue();
    }

}
