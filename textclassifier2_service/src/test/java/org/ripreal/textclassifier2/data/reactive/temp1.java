package org.ripreal.textclassifier2.data.reactive;

public class temp1 {
/*
    @RunWith(SpringRunner.class)
    @SpringBootTest(
            classes = {LocationController.class, Spring5DemoApplication.class},
            webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    public class Spring5DemoApplicationTests {

        private WebClient webClient;

        @LocalServerPort
        private int port;

        @Before
        public void setup() {
            this.webClient = WebClient.create(new ReactorClientHttpConnector());
        }

        @Test
        public void homeController() {
            LatLng ll = new LatLng(20d, 52d);

            ClientRequest<Flux<LatLng>> request = ClientRequest
                    .POST("http://localhost:{port}/location/address", this.port)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Flux.just(ll), LatLng.class);

            Flux<Address> result = this.webClient
                    .exchange(request)
                    .flatMap(response -> response.bodyToFlux(Address.class));

            ScriptedSubscriber.<Address>create()
                    .consumeNextWith(address -> {
                        assertEquals("Rome", address.getCity());
                    })
                    .expectComplete()
                    .verify(result);
        }
    }
*/
}
