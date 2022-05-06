package org.luvx.tome;

import static org.luvx.tome.utils.HttpClientUtils.client;
import static org.luvx.tome.utils.XmlUtils.objectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.luvx.tome.pojo.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WeChatService {
    @Value("${wechat.corpid}")
    private String corpid;
    @Value("${wechat.corpsecret}")
    private String corpsecret;
    @Value("${wechat.agentid}")
    private long   agentid;

    private final String getTokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
    private final String sendMsgUrl  = "https://qyapi.weixin.qq.com/cgi-bin/message/send";

    public void send(Message message) throws IOException, InterruptedException {
        String s = sendMsgUrl + "?access_token=" + getToken();
        message.setTouser("@all");
        message.setAgentid(agentid);
        String requestBody = objectMapper.writeValueAsString(message);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(requestBody))
                .uri(URI.create(s))
                .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        log.info("响应:{}", response.body());
        WeChatResponse1 weCahtResponse = objectMapper.readValue(response.body(), WeChatResponse1.class);
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
        WeChatResponse weCahtResponse = objectMapper.readValue(response.body(), WeChatResponse.class);
        return weCahtResponse.getAccess_token();
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
