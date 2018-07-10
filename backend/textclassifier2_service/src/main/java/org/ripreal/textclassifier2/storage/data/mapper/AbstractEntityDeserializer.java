package org.ripreal.textclassifier2.storage.data.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.slf4j.Logger;
import java.io.IOException;

public abstract class AbstractEntityDeserializer <T> extends StdDeserializer<T> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AbstractEntityDeserializer.class);

    public AbstractEntityDeserializer() {
        this(null);
    }

    public AbstractEntityDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public T deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        T convertingEntity = newEntity();
        try {
            ObjectCodec codec = parser.getCodec();
            JsonNode node = codec.readTree(parser);
            EntitiesConverter.convertTo(node, codec, convertingEntity);
        } catch(IOException e) {
            log.warn("Some properties of the converting entity was ignored because of the deserialization error", e);
        }
        return convertingEntity;
    }

    public abstract T newEntity();
}
