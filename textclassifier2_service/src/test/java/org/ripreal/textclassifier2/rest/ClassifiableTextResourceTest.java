package org.ripreal.textclassifier2.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.testdata.ClassifiableTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static java.awt.SystemColor.text;
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

        List<MongoClassifiableText> texts = ClassifiableTestData.getTextTestData();

        webClient.post()
                .uri(URI.create(this.server + "/texts"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Flux.fromIterable(texts), MongoClassifiableText.class)
                .exchange()
                .doOnNext(body -> {
                    assertTrue(body.statusCode().is2xxSuccessful());
                }
                )
                .block();

        // repeat test with other method

        String requestJson = mapper.writeValueAsString(texts);

        ClientResponse resp = webClient.post()
                .uri(URI.create(this.server + "/texts"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(requestJson).exchange().block();

        assertTrue(resp.statusCode().is2xxSuccessful());
    }

}