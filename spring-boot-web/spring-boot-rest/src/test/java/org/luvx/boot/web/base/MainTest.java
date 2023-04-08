package org.luvx.boot.web.base;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.luvx.RestAppTests;
import org.luvx.boot.web.entity.json.UserVo;
import org.springframework.beans.factory.annotation.Value;

public class MainTest extends RestAppTests {
    @Value("${value.test-list:}")
    private String[]     testArray1;
    @Value("#{'${value.test-list:}'.split(',')}")
    private List<String> apkTestList;

    @Value("#{${value.test-map}}")
    private Map<String, Object> apkdownloadDefaultMap1;

    @Value("#{T(org.luvx.boot.web.base.JsonDecoder).decodeMap('${value.test-user:}')}")
    private UserVo user;

    @Test
    void m1() {
        System.out.println(Arrays.toString(testArray1));
        System.out.println(apkTestList);
        System.out.println(apkdownloadDefaultMap1);
        System.out.println(user);
    }
}
