package org.ripreal.textclassifier2.storage.controller;

import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.service.CharacteristicService;
import org.ripreal.textclassifier2.storage.service.ClassifiableTextService;
import org.ripreal.textclassifier2.storage.service.decorators.LoggerClassifiableTextService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.ripreal.textclassifier2.storage.controller.exceptions.ThereIsNoSuchCharacteristic;

import java.util.Collections;

@RestController
@RequestMapping("characteristics")
public class CharacteristicResource {

    private final ClassifiableTextService<MongoCharacteristic> service;

    public CharacteristicResource(CharacteristicService service) {
        this.service = new LoggerClassifiableTextService<>(service);
    }

    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<MongoCharacteristic> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MongoCharacteristic> findByName(@PathVariable String name) {
        return service.findById(name)
                .doOnSuccess(
                        (el) -> {
                            if (el == null)
                                throw new ThereIsNoSuchCharacteristic();
                        }
                );
    }

}
