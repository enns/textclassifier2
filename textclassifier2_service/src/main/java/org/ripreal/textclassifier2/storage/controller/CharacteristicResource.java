package org.ripreal.textclassifier2.storage.controller;

import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.service.ClassifiableService;
import org.ripreal.textclassifier2.storage.service.decorators.LoggerClassifiableTextService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.ripreal.textclassifier2.storage.controller.exceptions.ThereIsNoSuchCharacteristic;

@RestController
@RequestMapping("characteristics")
public class CharacteristicResource {

    private final ClassifiableService service;

    public CharacteristicResource(ClassifiableService service) {
        this.service = new LoggerClassifiableTextService(service);
    }
    
    @GetMapping(value = "{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MongoCharacteristic> findByName(@PathVariable String name) {
        return service.findCharacteristicByName(name)
                .doOnSuccess(
                        (el) -> {
                            if (el == null)
                                throw new ThereIsNoSuchCharacteristic();
                        }
                );
    }

}
