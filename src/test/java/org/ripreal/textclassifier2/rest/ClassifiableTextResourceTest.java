package org.ripreal.textclassifier2.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.ripreal.textclassifier2.data.reactive.repos.ClassifiableTextRepo;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.testdata.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

import static org.junit.Assert.*;

public class ClassifiableTextResourceTest extends RestResourceTest {

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
        ClassifiableText text = Helper.getTextTestData().get(0);
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