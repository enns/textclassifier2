package org.ripreal.textclassifier2.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.ripreal.textclassifier2.entries.PersistClassifiableText;
import org.ripreal.textclassifier2.testdata.ClassifiableTestData;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.junit.Assert.assertTrue;

public class ClassifiableTextResourceTest extends AbstractResourceTest {

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

        PersistClassifiableText text = ClassifiableTestData.getTextTestData().get(0);

        webClient.post()
                .uri(URI.create(this.server + "/texts"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(text), PersistClassifiableText.class)
                .exchange()
                .doOnNext(body -> {
                    assertTrue(body.statusCode().is2xxSuccessful());
                }
                )
                .block();
        // repeat test with other method
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(text);

        ClientResponse resp = webClient.post()
                .uri(URI.create(this.server + "/texts"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(requestJson).exchange().block();

        assertTrue(resp.statusCode().is2xxSuccessful());
    }

}