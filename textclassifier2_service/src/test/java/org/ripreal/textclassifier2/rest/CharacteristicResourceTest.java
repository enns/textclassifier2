package org.ripreal.textclassifier2.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.testdata.ClassifiableTestData;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.junit.Assert.assertTrue;

public class CharacteristicResourceTest extends AbstractResourceTest {

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

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(characteristic.block());

        ClientResponse resp = webClient.post()
                .uri(URI.create(this.server + "/characteristics"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(requestJson).exchange().block();
        HttpStatus st = resp.statusCode();
    }
}