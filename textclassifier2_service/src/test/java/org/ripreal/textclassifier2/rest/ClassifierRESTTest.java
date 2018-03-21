package org.ripreal.textclassifier2.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ripreal.textclassifier2.classifier.Classifier;
import org.ripreal.textclassifier2.data.entries.PersistClassifiableText;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;

import static org.junit.Assert.*;

public class ClassifierRESTTest extends AbstractResourceTest {

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void initClassifier() throws IOException {

        ClassifierOptions options = new ClassifierOptions(
                "neural", "отдел", NGramStrategy.NGRAM_TYPES.UNIGRAM
        );

        String requestJson = mapper.writeValueAsString(options);
        ClassifierOptions pText = mapper.readValue(requestJson, ClassifierOptions.class);

        ClientResponse resp = webClient.post()
                .uri(URI.create(this.server + "/classifier/init"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .attribute("options", pText).exchange().block();

        assertTrue(resp.statusCode().is2xxSuccessful());

        webClient.post()
                .uri(URI.create(this.server + "/classifier/init"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(options), ClassifierOptions.class)
                .exchange()
                .doOnNext(body -> assertTrue(body.statusCode().is2xxSuccessful()))
                .block();
    }
}