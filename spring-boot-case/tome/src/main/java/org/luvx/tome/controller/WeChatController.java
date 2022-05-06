package org.luvx.tome.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import org.luvx.tome.schedule.WeatherNotifyScheduler;
import org.luvx.tome.utils.XmlUtils;
import org.luvx.tome.wechat.WXBizJsonMsgCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/tome/wechat")
public class WeChatController {
    @Value("${wechat.corpid}")
    private String corpid;
    @Value("${wechat.token}")
    String token;
    @Value("${wechat.encodingAesKey}")
    String encodingAesKey;

    @Autowired
    private WeatherNotifyScheduler weatherNotifyScheduler;

    @GetMapping("/index")
    public Object index() {
        return Map.of(
                "app", "toMe",
                "time", LocalDateTime.now()
        );
    }

    @GetMapping("/weather")
    public String weather() throws Exception {
        weatherNotifyScheduler.a();
        return "index";
    }

    @GetMapping("/doMsg1")
    public String a(String msg_signature, String timestamp, String nonce, String echostr) throws Exception {
        WXBizJsonMsgCrypt wxBizJsonMsgCrypt = new WXBizJsonMsgCrypt(token, encodingAesKey, corpid);
        return wxBizJsonMsgCrypt.VerifyURL(msg_signature, timestamp, nonce, echostr);
    }

    @PostMapping("doMsg")
    public String b(String msg_signature, String timestamp, String nonce, @RequestBody String xml) throws Exception {
        log.info("参数:{} {} {} {}", msg_signature, timestamp, nonce, xml);
        WXBizJsonMsgCrypt wxBizJsonMsgCrypt = new WXBizJsonMsgCrypt(token, encodingAesKey, corpid);
        String json = XmlUtils.convertXmlToJson(xml);
        String s = wxBizJsonMsgCrypt.DecryptMsg(msg_signature, timestamp, nonce, json);
        log.info("明文:{}", s);
        String s1 = wxBizJsonMsgCrypt.EncryptMsg(XmlUtils.convertXmlToJson(s), timestamp, nonce);
        log.info("被动回复消息:{}", s1);
        return s1;
    }
}
