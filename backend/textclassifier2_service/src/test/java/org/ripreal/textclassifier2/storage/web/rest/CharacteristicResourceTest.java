package org.ripreal.textclassifier2.storage.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;

public class CharacteristicResourceTest extends AbstractResourceTest {

    @Autowired
    private ObjectMapper mapper;

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
    public void findByName() throws Exception {
        webClient.get()
                .uri(URI.create(this.server + "/characteristics/RES_NOT_EXISTS"))
                .accept(APPLICATION_JSON).exchange()
                .doOnNext(body -> assertTrue(body.statusCode().is4xxClientError()))
                .block();
    }

}