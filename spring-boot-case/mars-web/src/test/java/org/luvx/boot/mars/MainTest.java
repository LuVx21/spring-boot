package org.luvx.boot.mars;

import com.google.common.io.BaseEncoding;
import org.junit.jupiter.api.Test;
import org.luvx.coding.common.more.MorePrints;
import org.springframework.web.util.UriComponentsBuilder;

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
        var token = BaseEncoding.base64().encode(str.getBytes());
        var bytes = BaseEncoding.base64().decode(token);
        MorePrints.println(
                str,
                token,
                new String(bytes)
        );
    }

    @Test
    void m3() {
    }
}