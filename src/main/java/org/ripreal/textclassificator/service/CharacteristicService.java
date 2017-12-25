package org.ripreal.textclassificator.service;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassificator.data.reactive.CharacteristicRepo;
import org.ripreal.textclassificator.model.Characteristic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class CharacteristicService {
    final CharacteristicRepo repository;

    public Flux<Characteristic> findAll() {
        return repository.findAll();
    }
}
