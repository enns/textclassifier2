package org.ripreal.textclassifier2.rest;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ripreal.textclassifier2.App;
import org.ripreal.textclassifier2.model.CharactValuePair;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.service.CharacteristicService;
import org.ripreal.textclassifier2.testdata.Helper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class CharacteristicResourceTest extends RestResourceTest {

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
        Mono<Characteristic> characteristic = Helper
            .getTextTestData()
            .stream()
            .flatMap(item -> item.getCharacteristics().stream())
            .map(item -> Mono.just(item.getKey()))
            .findFirst()
            .orElse(Mono.empty());

        webClient.post()
            .uri(URI.create(this.server + "/characteristics"))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(characteristic, Characteristic.class)
            .exchange()
            .doOnNext(body -> assertTrue(body.statusCode().is2xxSuccessful()))
            .block();
    }
}