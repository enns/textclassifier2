package org.ripreal.textclassifier2.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.service.CharacteristicService;
import org.ripreal.textclassifier2.service.ClassifiableTextService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("texts")
@Slf4j
public class ClassifiableTextResource {

    final ClassifiableTextService service;

    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ClassifiableText> findAll() {
        return service.findAll()
            .doOnRequest((req) -> log.info("request received: {}", req))
            .doFinally((signal) -> log.info("request completed: {}", signal));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ClassifiableText> save(@RequestBody ClassifiableText text) {
        return service.saveTextsWithCharacteristics(Collections.singletonList(text))
            .doOnRequest((req) -> log.info("request received: {}", req))
            .doFinally((signal) -> log.info("request completed: {}", signal));
    }
}
