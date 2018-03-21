package org.ripreal.textclassifier2.rest;

import org.ripreal.textclassifier2.classifier.Classifier;
import org.ripreal.textclassifier2.data.entries.PersistClassifiableText;
import org.ripreal.textclassifier2.service.ClassifiableTextService;
import org.ripreal.textclassifier2.service.DataService;
import org.ripreal.textclassifier2.service.decorators.LoggerDataService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Collections;

@RestController
@RequestMapping("texts")
public class ClassifiableTextResource {

    private final DataService<PersistClassifiableText> service;

    public ClassifiableTextResource(ClassifiableTextService service) {
        this.service = new LoggerDataService<>(service);
    }

    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<PersistClassifiableText> findAll() {
        return service.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<PersistClassifiableText> save(@RequestBody PersistClassifiableText text) {
        return service.saveAll(Collections.singletonList(text));
    }

}
