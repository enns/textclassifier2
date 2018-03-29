package org.ripreal.textclassifier2.storage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.storage.controller.ClassifierREST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ClassifierRESTTest extends AbstractResourceTest {

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void initClassifier() throws IOException {

        List<ClassifierREST.ClassifierOptions> options = Collections.singletonList(
                new ClassifierREST.ClassifierOptions(
                "neural", "отдел", NGramStrategy.NGRAM_TYPES.UNIGRAM
        ));

        webClient.post()
                .uri(URI.create(this.server + "/classifier/init"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Flux.fromIterable(options), ClassifierREST.ClassifierOptions.class)
                .exchange()
                .doOnNext(body -> assertTrue(body.statusCode().is2xxSuccessful()))
                .block();
    }

    public void initClassifierWithIllegalParameters() throws IOException {

        List<ClassifierREST.ClassifierOptions> options = Collections.singletonList(
                new ClassifierREST.ClassifierOptions(
                        "wrong", "отдел", NGramStrategy.NGRAM_TYPES.UNIGRAM
                ));

        webClient.post()
                .uri(URI.create(this.server + "/classifier/init"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Flux.fromIterable(options), ClassifierREST.ClassifierOptions.class)
                .exchange()
                .doOnNext(body -> assertTrue(body.statusCode().is4xxClientError()))
                .block();
    }
}