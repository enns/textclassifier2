package org.ripreal.textclassifier2.data.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.data.entries.PersistCharacteristic;
import org.ripreal.textclassifier2.data.entries.PersistCharacteristicValue;
import org.ripreal.textclassifier2.data.entries.PersistClassifiableText;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ClassifiableTextDeserializer  extends AbstractEntityDeserializer<ClassifiableText> {

    public ClassifiableTextDeserializer() {
        super(null);
    }

    public ClassifiableTextDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ClassifiableText newEntity() {
        return new PersistClassifiableText();
    }

}
