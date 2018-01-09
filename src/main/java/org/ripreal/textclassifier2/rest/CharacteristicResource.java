package org.ripreal.textclassifier2.rest;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.service.CharacteristicService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CountDownLatch;

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
        CountDownLatch countDownLatch = new CountDownLatch(1);
        return service.findByName(name).doAfterSuccessOrError(
            (el, error) -> {
                if (el == null)
                    throw new ThereIsNoSuchCharacteristic();
            }
        )
        .doFinally(System.out::println);
    }
}
