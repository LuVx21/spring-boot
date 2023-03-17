package org.luvx.boot.tools.service.tome.utils;

import java.net.http.HttpClient;
import java.time.Duration;

public class HttpClientUtils {
    public static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofMillis(5000))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();
}
