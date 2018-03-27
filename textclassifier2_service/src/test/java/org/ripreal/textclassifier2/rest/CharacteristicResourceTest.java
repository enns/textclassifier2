package org.ripreal.textclassifier2.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.storage.testdata.ClassifiableTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.junit.Assert.assertTrue;

public class CharacteristicResourceTest extends AbstractResourceTest {

    @Autowired
    private ObjectMapper mapper;

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

}