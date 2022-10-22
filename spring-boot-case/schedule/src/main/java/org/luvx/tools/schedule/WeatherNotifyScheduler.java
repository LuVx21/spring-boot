package org.luvx.tools.schedule;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.luvx.coding.common.util.JsonUtils;
import org.luvx.tools.service.tome.WeChatService;
import org.luvx.tools.service.tome.entity.City;
import org.luvx.tools.service.tome.entity.DayWeather;
import org.luvx.tools.service.tome.pojo.Content;
import org.luvx.tools.service.tome.pojo.TextType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.luvx.coding.common.util.JsonUtils;
import static org.luvx.tools.service.tome.utils.HttpClientUtils.client;

@Slf4j
@Service
public class WeatherNotifyScheduler {
    @Value("${moji.appCode}")
    private String appCode;
    @Value("${moji.token}")
    private String token;

    @Autowired
    private WeChatService weChatService;

    String weatherUrl = "https://apifreelat.market.alicloudapi.com/whapi/json/aliweather/briefforecast3days";

    @Scheduled(cron = "0 0 23 * * ?")
    public void a() throws Exception {
        getWeather("40.068387", "116.324212");
        getWeather("30.395202", "114.328222");
    }

    public void getWeather(String lat, String lon) throws Exception {
        String body = "lat=" + lat + "&lon=" + lon + "&token=" + token;
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "APPCODE " + appCode)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(BodyPublishers.ofString(body))
                .uri(URI.create(weatherUrl))
                .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        weatherJson(response.body());
    }

    public void weatherJson(String json) throws IOException, InterruptedException {
        Map<String, Object> tmpMap = JsonUtils.fromJson(json, Map.class);
        ResponseData data =
                JsonUtils.fromJson(JsonUtils.toJson(tmpMap.get("data")), ResponseData.class);
        City city = data.getCity();
        String location = city.location();
        StringBuilder sb = new StringBuilder(location);
        data.getForecast().stream()
                .filter(Objects::nonNull)
                .map(DayWeather::info)
                .forEach(sb::append);

        System.out.println(sb);

        TextType textType = new TextType(new Content(sb.toString()));
        weChatService.send(textType);
    }

    @Data
    private static class ResponseData {
        private City             city;
        private List<DayWeather> forecast;
    }
}
