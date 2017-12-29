package org.ripreal.textclassifier2.rest;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.service.CharacteristicService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("chars")
public class CharacteristicResource {

    final CharacteristicService service;

    @GetMapping("all")
    public Flux<Characteristic> findAll() {
        return service.findAll();
    }

    @GetMapping("{name}")
    public Mono<Characteristic> findById(@PathVariable String name) {
        return service.findByName(name).doAfterSuccessOrError(
            (el, error) -> {
                if (el == null)
                    throw new ThereIsNoSuchCharacteristic();
            }
        );
    }
}
