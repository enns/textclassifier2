package org.ripreal.textclassifier2.rest;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.service.CharacteristicService;
import org.ripreal.textclassifier2.service.ClassifiableTextService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class ClassifiableTextResource {

    final ClassifiableTextService service;

    @GetMapping("text/all")
    public Flux<ClassifiableText> searchAll() {
        return service.findAll();
    }
}
