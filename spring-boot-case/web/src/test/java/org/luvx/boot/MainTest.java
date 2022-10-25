package org.luvx.boot;

import org.junit.jupiter.api.Test;
import org.luvx.coding.common.more.MorePrints;
import org.springframework.util.Base64Utils;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.io.BaseEncoding;

class MainTest {
    @Test
    void m1() {
        String longUrl = UriComponentsBuilder.fromHttpUrl("https://v2ex.com")
                .path("123")
                .path("45")
                .pathSegment("foo", "67", "89", "谢谢")
                .queryParam("userName", "foo")
                .encode()
                .toUriString();

        System.out.println(longUrl);
    }

    @Test
    void m2() {
        String str = "foo****bar";
        String token = Base64Utils.encodeToString(str.getBytes());
        byte[] bytes = Base64Utils.decodeFromString(token);
        MorePrints.println(
                str,
                token,
                new String(bytes)
        );

        token = BaseEncoding.base64().encode(str.getBytes());
        bytes = BaseEncoding.base64().decode(token);
        MorePrints.println(
                str,
                token,
                new String(bytes)
        );
    }
}