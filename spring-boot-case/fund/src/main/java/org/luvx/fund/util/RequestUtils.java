package org.luvx.fund.util;

import static org.luvx.fund.constant.CommonKey.URL_PREFIX;
import static org.luvx.fund.constant.CommonKey.rateLimiter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.luvx.fund.constant.CommonKey;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestUtils {

    @SneakyThrows
    public static String getJson(String code) {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(5))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        HttpRequest getRequest = HttpRequest.newBuilder()
                .GET()
                .header("User-Agent", CommonKey.userAgent)
                .uri(URI.create(URL_PREFIX.formatted(code)))
                .build();

        rateLimiter.acquire();
        HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        return responseBody + CommonKey.postJs;
    }
}
