package org.luvx.boot.mars.web.utils;

import java.io.Serializable;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.ImmutableMap;

import org.junit.jupiter.api.Test;
import org.luvx.coding.common.util.JsonUtils;

class JsonXmlUtilsTest {
    @Test
    void main() throws Exception {
        ImmutableMap<String, ? extends Serializable> user = ImmutableMap.of(
                "userId", 10000L,
                "userName", "foo"
        );

        String jsonStr = JsonUtils.toJson(user);
        JSONObject json1 = JSON.parseObject(jsonStr);
        JSONObject passWord = json1.getJSONObject("passWord");

        String xml = JsonXmlUtils.jsonToXml(json1);
        System.out.println(xml);

        JSONObject json = JsonXmlUtils.xmlToJson(xml);
        // System.out.println(JSON.toJSONString(json, true));

        System.out.println("----------------------------------------\n\n");
        xml = JsonXmlUtils.jsonToPrettyXml(json);
        System.out.println(xml);
    }
}