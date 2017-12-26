package org.ripreal.textclassifier2.service;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.data.reactive.CharacteristicRepo;
import org.ripreal.textclassifier2.model.Characteristic;
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