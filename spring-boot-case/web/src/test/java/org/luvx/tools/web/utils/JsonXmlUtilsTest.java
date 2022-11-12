package org.luvx.tools.web.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;
import org.luvx.coding.common.util.JsonUtils;
import org.luvx.tools.web.base.web.UserVo;

class JsonXmlUtilsTest {
    @Test
    void main() throws Exception {
        UserVo user = new UserVo();
        user.setUserId(10000L);
        user.setUserName("foo");
        user.setPassword("bar");
        user.setAge(18);
        user.setValid(2);

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