package org.ripreal.textclassifier2.storage.service;

import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import reactor.core.publisher.Flux;

public interface ClassifiableService {

    public Flux<MongoClassifiableText> findAllTexts();
}
