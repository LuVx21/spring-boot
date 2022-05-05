package org.luvx.tome.schedule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.luvx.tome.entity.City;
import org.luvx.tome.entity.DayWeather;
import org.luvx.tome.pojo.Content;
import org.luvx.tome.pojo.Message;
import org.luvx.tome.pojo.TextType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WeatherNotifyScheduler {
    @Value("${wechat.corpid}")
    private String corpid;
    @Value("${wechat.corpsecret}")
    private String corpsecret;
    @Value("${wechat.agentid}")
    private long   agentid;

    @Value("${moji.appCode}")
    private String appCode;
    @Value("${moji.token}")
    private String token;

    String getTokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
    String sendMsgUrl  = "https://qyapi.weixin.qq.com/cgi-bin/message/send";
    String weatherUrl  = "https://apifreelat.market.alicloudapi.com/whapi/json/aliweather/briefforecast3days";

    ObjectMapper mapper = new ObjectMapper();
    HttpClient   client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofMillis(5000))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    @Scheduled(cron = "0 0 23 * * ?")
    public void a() throws Exception {
        getWeather("116.324212", "40.068387");
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
        Map<String, Object> tmpMap = mapper.readValue(json, Map.class);
        ResponseData data = mapper.readValue(mapper.writeValueAsString(tmpMap.get("data")), ResponseData.class);
        City city = data.getCity();
        String location = city.location();
        StringBuilder sb = new StringBuilder(location);
        List<DayWeather> forecast = data.getForecast();
        ImmutableMap<Integer, String> map = ImmutableMap.of(0, "今", 1, "明", 2, "后");
        for (int i = 0; i < forecast.size(); i++) {
            DayWeather w = forecast.get(i);
            sb.append("----" + map.get(i) + "天(" + w.getPredictDate() + ")----\n");
            sb.append("状况: " + w.getConditionNight() + " ~ " + w.getConditionDay() + "\n");
            sb.append("温度: " + w.getTempNight() + " ~ " + w.getTempDay() + "\n");
            sb.append("风向: " + w.getWindDirNight() + " ~ " + w.getWindDirDay() + "\n");
            sb.append("风级: " + w.getWindLevelNight() + " ~ " + w.getWindLevelDay() + "\n");
        }

        System.out.println(sb);

        TextType textType = new TextType(new Content(sb.toString()));
        send(textType);
    }

    public void send(Message message) throws IOException, InterruptedException {
        String s = sendMsgUrl + "?access_token=" + getToken();
        message.setTouser("@all");
        message.setAgentid(agentid);
        String requestBody = mapper.writeValueAsString(message);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(requestBody))
                .uri(URI.create(s))
                .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        log.info("响应:{}", response.body());
        WeChatResponse1 weCahtResponse = mapper.readValue(response.body(), WeChatResponse1.class);
        if (40014 == weCahtResponse.getErrcode()) {
        }
    }

    public String getToken() throws IOException, InterruptedException {
        String s = getTokenUrl + "?corpid=" + corpid + "&corpsecret=" + corpsecret;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(s))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        log.info("响应:{}", response.body());
        WeChatResponse weCahtResponse = mapper.readValue(response.body(), WeChatResponse.class);
        return weCahtResponse.getAccess_token();
    }

    @Data
    private static class ResponseData {
        private City             city;
        private List<DayWeather> forecast;
    }

    @Data
    private static class WeChatResponse {
        private int    errcode;
        private String errmsg;
        private String access_token;
        private int    expires_in;
    }

    @Data
    private static class WeChatResponse1 {
        private int    errcode;
        private String errmsg;
        private String msgid;
    }
}
