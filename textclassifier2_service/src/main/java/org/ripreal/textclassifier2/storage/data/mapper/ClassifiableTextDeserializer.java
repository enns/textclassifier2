package org.ripreal.textclassifier2.storage.data.mapper;

import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.model.ClassifiableText;

public class ClassifiableTextDeserializer  extends AbstractEntityDeserializer<ClassifiableText> {

    public ClassifiableTextDeserializer() {
        super(null);
    }

    public ClassifiableTextDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ClassifiableText newEntity() {
        return new MongoClassifiableText();
    }

}
