package org.luvx.boot.web.base;

import com.alibaba.fastjson2.JSONObject;

import org.luvx.boot.web.entity.json.UserVo;

public class JsonDecoder {
    public static UserVo decodeMap(String json) {
        try {
            return JSONObject.parseObject(json, UserVo.class);
        } catch (Exception e) {
            return null;
        }
    }
}
