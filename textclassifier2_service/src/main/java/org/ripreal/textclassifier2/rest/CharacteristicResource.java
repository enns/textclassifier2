package org.ripreal.textclassifier2.rest;

import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.rest.exceptions.ThereIsNoSuchCharacteristic;
import org.ripreal.textclassifier2.service.CharacteristicService;
import org.ripreal.textclassifier2.service.DataService;
import org.ripreal.textclassifier2.service.decorators.LoggerDataService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RestController
@RequestMapping("characteristics")
public class CharacteristicResource {

    private final DataService<Characteristic> service;

    public CharacteristicResource(CharacteristicService service) {
        this.service = new LoggerDataService<>(service);
    }

    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Characteristic> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Characteristic> findByName(@PathVariable String name) {
        return service.findById(name)
                .doOnSuccess(
                        (el) -> {
                            if (el == null)
                                throw new ThereIsNoSuchCharacteristic();
                        }
                );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Characteristic> save(@RequestBody Characteristic characteristic) {
        return service.saveAll(Collections.singletonList(characteristic));
    }
}
