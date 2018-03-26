package org.ripreal.textclassifier2.storage.data.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public abstract class AbstractEntityDeserializer <T> extends StdDeserializer<T> {

    public AbstractEntityDeserializer() {
        this(null);
    }

    public AbstractEntityDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public T deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {

        T charValue = newEntity();
        try {
            ObjectCodec codec = parser.getCodec();
            JsonNode node = codec.readTree(parser);
            EntitiesConverter.convertTo(node, codec, charValue);
        } catch(IOException e) {
            log.warn("Some properties of the CharacteristicValue was ignored because of the deserialization error", e);
        }
        return charValue;
    }

    public abstract T newEntity();
}
