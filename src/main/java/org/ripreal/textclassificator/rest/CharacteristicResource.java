package org.ripreal.textclassificator.rest;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassificator.model.Characteristic;
import org.ripreal.textclassificator.service.CharacteristicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class CharacteristicResource {

    final CharacteristicService service;

    @GetMapping("/all")
    public Flux<Characteristic> findAll() {
        return service.findAll();
    }
}
