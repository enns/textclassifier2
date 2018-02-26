package org.ripreal.textclassifier2.rest;

import org.junit.Test;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.testdata.ClassifiableTestData;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.junit.Assert.assertTrue;

public class ClassifiableTextResourceTest extends AbstractResourceTest {

    @Test
    public void findAll() throws Exception {
        /*
        webClient.get()
            .uri(URI.create(this.server + "/texts/all"))
            .accept(MediaType.APPLICATION_JSON).exchange()
            .doOnNext(body -> assertTrue(body.statusCode().is2xxSuccessful()))
            .block();
            */
    }

    @Test
    public void save() throws Exception {
        ClassifiableText text = ClassifiableTestData.getTextTestData().get(0);
        webClient.post()
                .uri(URI.create(this.server + "/texts"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(text), ClassifiableText.class)
                .exchange()
                .doOnNext(body -> assertTrue(body.statusCode().is2xxSuccessful()))
                .block();
    }

    @Test
    public void saveAll() throws Exception {
        /*
        List<ClassifiableText> texts = Helper.getTextTestData();

        webClient.post()
            .uri(URI.create(this.server + "/texts/all"))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Flux.fromIterable(texts), ClassifiableText.class)
            .exchange()
            .doOnNext(body -> assertTrue(body.statusCode().is2xxSuccessful()))
            .block();
            */
    }
}