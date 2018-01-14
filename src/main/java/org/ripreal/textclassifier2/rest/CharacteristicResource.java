package org.ripreal.textclassifier2.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.rest.exceptions.ThereIsNoSuchCharacteristic;
import org.ripreal.textclassifier2.service.CharacteristicService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("characteristics")
@Slf4j
public class CharacteristicResource {

    final CharacteristicService service;

    @GetMapping("all")
    public Flux<Characteristic> findAll() {
        return service.findAll();
    }

    @GetMapping("{name}")
    public Mono<Characteristic> findByName(@PathVariable String name) {
        return service.findByName(name)
            .doOnSuccess(
                (el) -> {
                    if (el == null)
                        throw new ThereIsNoSuchCharacteristic();
                }
            )
            .doOnRequest((req) -> log.info("request received: {}", name))
            .doFinally((signal) -> log.info("request completed: {}", signal));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Characteristic> save(@RequestBody Characteristic characteristic) {
        return service.save(characteristic)
            .doOnRequest((req) -> log.info("request received: {}", characteristic))
            .doFinally((signal) -> log.info("request completed: {}", signal));
    }
}
