package org.luvx.boot.tools.web.tome;

import lombok.extern.slf4j.Slf4j;

import org.luvx.boot.tools.runner.WeatherNotifyScheduler;
import org.luvx.boot.tools.web.utils.JsonXmlUtils;
import org.luvx.boot.tools.service.tome.wechat.WXBizJsonMsgCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

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

    @GetMapping(value = "/index", produces = "application/json")
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
        String json = JsonXmlUtils.xmlToJson(xml).toJSONString();
        String s = wxBizJsonMsgCrypt.DecryptMsg(msg_signature, timestamp, nonce, json);
        log.info("明文:{}", s);
        String s1 = wxBizJsonMsgCrypt.EncryptMsg(JsonXmlUtils.xmlToJson(s).toJSONString(), timestamp, nonce);
        log.info("被动回复消息:{}", s1);
        return s1;
    }
}
