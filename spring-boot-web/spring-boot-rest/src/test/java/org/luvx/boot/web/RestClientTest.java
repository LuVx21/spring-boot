package org.luvx.boot.web;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

class RestClientTest extends RestAppTests {

    @Test
    void m1() {
        var body = restClient.get()
                .uri("https://httpbin.org/get")
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(Map.class);
        System.out.println(body);
    }

    @Test
    void m2() {
        var body = restClient.post()
                .uri("https://httpbin.org/post")
                .accept(APPLICATION_JSON)
                .body(Map.of("a", 1))
                .retrieve()
                .body(String.class);
        System.out.println(body);
    }

    @Test
    void m3() {
        var body = restClient.get()
                .uri("https://httpbin.org/get")
                .accept(APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        var r = Map.of("code", response.getStatusCode(), "header", response.getHeaders());
                        throw new RuntimeException(r.toString());
                    } else {
                        return response.bodyTo(String.class);
                    }
                });
        System.out.println(body);
    }
}
