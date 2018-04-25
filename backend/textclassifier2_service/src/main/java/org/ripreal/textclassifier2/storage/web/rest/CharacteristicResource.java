package org.ripreal.textclassifier2.storage.web.rest;

import org.ripreal.textclassifier2.storage.web.rest.exceptions.ResourceNotFoundException;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.service.ClassifiableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class CharacteristicResource {

    @Autowired
    private ClassifiableService service;

    @GetMapping(value = "/characteristics", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<MongoCharacteristic> getAll() {
        return service.findAllCharacteristics();
    }

    @GetMapping(value = "/characteristics/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MongoCharacteristic> findByName(@PathVariable String name) {
        return service.findCharacteristicByName(name)
            .doOnSuccess(
                    (el) -> {
                        if (el == null)
                            throw new ResourceNotFoundException("There's no such MongoCharacteristic!");
                    }
            );
    }
}