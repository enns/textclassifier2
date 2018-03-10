package org.ripreal.textclassifier2.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.testdata.ClassifiableTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.junit.Assert.assertTrue;

public class CharacteristicResourceTest extends AbstractResourceTest {

    @Autowired
    private ObjectMapper mapper;

    @Before
    @Test
    public void findAll() throws Exception {
        webClient.get()
                .uri(URI.create(this.server + "/characteristics/all"))
                .accept(MediaType.APPLICATION_JSON).exchange()
                .doOnNext(body -> assertTrue(body.statusCode().is2xxSuccessful()))
                .block();
    }

    @Test
    public void findByName() throws Exception {
        webClient.get()
                .uri(URI.create(this.server + "/characteristics/RES_NOT_EXISTS"))
                .accept(MediaType.APPLICATION_JSON).exchange()
                .doOnNext(body -> assertTrue(body.statusCode().is4xxClientError()))
                .block();
    }

    @Test
    public void save() throws Exception {
        Mono<Characteristic> characteristic = ClassifiableTestData
                .getTextTestData()
                .stream()
                .flatMap(text -> text.getCharacteristics().stream())
                .map(value -> Mono.just(value.getCharacteristic()))
                .findFirst()
                .orElse(Mono.empty());
        /*
        webClient.post()
                .uri(URI.create(this.server + "/characteristics"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(characteristic, Characteristic.class)
                .exchange()
                .doOnNext(body -> assertTrue(body.statusCode().is2xxSuccessful()))
                .block();
        */

    }
}