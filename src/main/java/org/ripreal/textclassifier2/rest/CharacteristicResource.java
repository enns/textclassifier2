package org.ripreal.textclassifier2.rest;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.service.CharacteristicService;
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
