package org.ripreal.textclassifier2.storage.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.testdata.AutogenerateTestDataReader;
import org.ripreal.textclassifier2.storage.testdata.ClassifiableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;

public class CharacteristicResourceTest extends AbstractResourceTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private ClassifiableMapper clasisfiableMapper;

    @Test
    public void findAll() throws Exception {
        ClientResponse response = webClient.get()
            .uri(URI.create(this.server + "/api/v1/characteristics/test"))
            .accept(TEXT_PLAIN)
            .exchange()
            .block();
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode());
    }

    @Test
    public void save() throws Exception {

        Set<Characteristic> characteristics = new AutogenerateTestDataReader(clasisfiableMapper).next().getCharacteristics();

        webClient.post()
                .uri(URI.create(this.server + "/api/characteristics"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Flux.fromIterable(clasisfiableMapper.fromCharacteristic(characteristics)), MongoCharacteristic.class)
                .exchange()
                .doOnNext(body -> {
                    assertTrue(body.statusCode().is2xxSuccessful());
                })
                .block();

        // repeat test with other method

        String requestJson = mapper.writeValueAsString(characteristics);

        ClientResponse resp = webClient.post()
                .uri(URI.create(this.server + "/api/characteristics"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(requestJson).exchange().block();

        assertTrue(resp.statusCode().is2xxSuccessful());
    }

    @Test
    public void findByName() throws Exception {
        webClient.get()
                .uri(URI.create(this.server + "/characteristics/RES_NOT_EXISTS"))
                .accept(APPLICATION_JSON).exchange()
                .doOnNext(body -> assertTrue(body.statusCode().is4xxClientError()))
                .block();
    }

}