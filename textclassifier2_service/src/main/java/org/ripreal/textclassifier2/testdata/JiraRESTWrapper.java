package org.ripreal.textclassifier2.testdata;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.net.URI;
import java.util.List;

@Configuration
public class JiraRESTWrapper {

    @NonNull
    private final String server = "http://jira.avilon.ru:7890";

    private final WebClient webClient;

    @NonNull
    private final ClassifiableFactory textFactory;

    public JiraRESTWrapper(ClassifiableFactory factory) {
        this.webClient = WebClient
            .builder()
            .baseUrl(server)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
        textFactory = factory;
    }

    @Bean
    public List<ClassifiableText> listOfClassifiableTexts() {

        ClientResponse response = webClient.get()
            .uri("/rest/api/2/issue/ag-52")
            .exchange()
            .block();
        Mono<String> res = response.bodyToMono(String.class);
        String res2 = res.block();
        return null;

    }

}
