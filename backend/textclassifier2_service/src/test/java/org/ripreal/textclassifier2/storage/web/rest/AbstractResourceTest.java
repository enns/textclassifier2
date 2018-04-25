package org.ripreal.textclassifier2.storage.web.rest;

import org.junit.Before;
import org.ripreal.textclassifier2.storage.SpringTestConfig;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;

public abstract class AbstractResourceTest extends SpringTestConfig {

    protected WebClient webClient;
    @LocalServerPort
    private int port;
    protected String server;

    @Before
    public void setup() {
        this.webClient = WebClient.create();
        this.server = "localhost:" + port;
    }
}
