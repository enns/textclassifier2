package org.ripreal.textclassifier2.storage.data.mapper;

import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;

public class ClassifiableTextDeserializer  extends AbstractEntityDeserializer<MongoClassifiableText> {

    public ClassifiableTextDeserializer() {
        super(null);
    }

    public ClassifiableTextDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MongoClassifiableText newEntity() {
        return new MongoClassifiableText();
    }

}
