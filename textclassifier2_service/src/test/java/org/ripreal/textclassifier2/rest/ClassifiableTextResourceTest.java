package org.ripreal.textclassifier2.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.testdata.ClassifiableTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.junit.Assert.assertTrue;

public class ClassifiableTextResourceTest extends AbstractResourceTest {

    @Autowired
    ObjectMapper mapper;

    @Test
    public void findAll() throws Exception {
        webClient.get()
            .uri(URI.create(this.server + "/texts/all"))
            .accept(MediaType.APPLICATION_JSON).exchange()
            .doOnNext(body -> assertTrue(body.statusCode().is2xxSuccessful()))
            .block();
    }

    @Test
    public void save() throws Exception {

        MongoClassifiableText text = ClassifiableTestData.getTextTestData().get(0);

        webClient.post()
                .uri(URI.create(this.server + "/texts"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(text), MongoClassifiableText.class)
                .exchange()
                .doOnNext(body -> {
                    assertTrue(body.statusCode().is2xxSuccessful());
                }
                )
                .block();

        // repeat test with other method

        String requestJson = mapper.writeValueAsString(text);
        MongoClassifiableText pText = mapper.readValue(requestJson, MongoClassifiableText.class);

        ClientResponse resp = webClient.post()
                .uri(URI.create(this.server + "/texts"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(requestJson).exchange().block();

        assertTrue(resp.statusCode().is2xxSuccessful());
    }

}