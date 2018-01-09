package org.ripreal.textclassifier2.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ripreal.textclassifier2.App;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {CharacteristicService.class, App.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CharacteristicServiceTest {

    private WebClient webClient;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        this.webClient = WebClient.create();
    }

    @Test
    public void findAll() throws Exception {
        /*
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Mono<ClientResponse> resp = this.webClient.get()
            .uri(URI.create("http://localhost:{port}/location/address"))
                .accept(MediaType.APPLICATION_JSON).exchange();

        resp.subscribe((ev) -> {
            String res = ev.toString();
            countDownLatch.countDown();
        });
        countDownLatch.await();
        */
    }

    @Test
    public void findByName() throws Exception {
    }

}