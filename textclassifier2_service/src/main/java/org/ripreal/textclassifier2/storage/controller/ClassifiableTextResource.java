package org.ripreal.textclassifier2.storage.controller;

import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.service.ClassifiableService;
import org.ripreal.textclassifier2.storage.service.MongoTextService;
import org.ripreal.textclassifier2.storage.service.decorators.LoggerClassifiableTextService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("texts")
public class ClassifiableTextResource {

    private final ClassifiableService service;

    public ClassifiableTextResource(MongoTextService service) {
        this.service = new LoggerClassifiableTextService(service);
    }

    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<MongoClassifiableText> findAll() {
        return service.findAllTexts();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody List<MongoClassifiableText> texts) {
        service.saveAllTexts(texts);
    }
}
