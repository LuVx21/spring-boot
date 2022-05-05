package org.luvx.tome.controller;

import org.luvx.tome.wechat.WXBizJsonMsgCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
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

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/doMsg1")
    public String a(String msg_signature, String timestamp, String nonce, String echostr) throws Exception {
        WXBizJsonMsgCrypt wxBizJsonMsgCrypt = new WXBizJsonMsgCrypt(token, encodingAesKey, corpid);
        return wxBizJsonMsgCrypt.VerifyURL(msg_signature, timestamp, nonce, echostr);
    }

    @PostMapping("doMsg")
    public String b(String msg_signature, String timestamp, String nonce, @RequestBody A data) throws Exception {
        log.info("参数:{} {} {} {}", msg_signature, timestamp, nonce, data);
        WXBizJsonMsgCrypt wxBizJsonMsgCrypt = new WXBizJsonMsgCrypt(token, encodingAesKey, corpid);
        String json = (new ObjectMapper()).writeValueAsString(data);
        String s = wxBizJsonMsgCrypt.DecryptMsg(msg_signature, timestamp, nonce, json);
        log.info("明文:{}", s);
        return s;
    }

    @Data
    private static class A {
        private String ToUserName;
        private String AgentID;
        private String Encrypt;
    }
}
