package org.ripreal.textclassifier2.storage.web.rest;

import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.service.ClassifiableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("texts")
public class ClassifiableTextResource {

    @Autowired
    private ClassifiableService service;

    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<MongoClassifiableText> findAll() {
        return service.findAllTexts();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody List<MongoClassifiableText> texts) {
        service.saveAllTexts(texts);
    }
}
