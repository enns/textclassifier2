package org.ripreal.textclassifier2.rest;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.ripreal.textclassifier2.App;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {App.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles(profiles = "unit_test")
public abstract class AbstractResourceTest {

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
