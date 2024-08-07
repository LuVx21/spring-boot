package org.luvx.boot.mars.service.commonkv;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.mars.service.commonkv.CommonKeyValueService;
import org.luvx.boot.mars.BaseAppTests;
import org.luvx.boot.mars.dao.entity.User;
import org.luvx.boot.mars.service.commonkv.constant.CommonKVBizType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
class CommonKeyValueServiceTest extends BaseAppTests {
    @Autowired
    private CommonKeyValueService service;

    @Test
    void m1() {
        service.setValue(CommonKVBizType.LONG, "a", 11);
        service.setOrIncrValue(CommonKVBizType.LONG, "a", 11);
        service.get(CommonKVBizType.LONG, "a").ifPresent(System.out::println);

        service.setValue(CommonKVBizType.STRING, "a", "11");
        service.getData(CommonKVBizType.STRING, "a").ifPresent(System.out::println);
    }

    @Test
    void m2() {
        Map<String, Object> map = Map.of(
                "userName", "foo",
                "password", "bar",
                "age", 17
        );

        service.setValue(CommonKVBizType.MAP, "a", map);
        service.setValueIfAbsent(CommonKVBizType.MAP, "a", Map.of("new", "new"));
        service.getData(CommonKVBizType.MAP, "a").ifPresent(System.out::println);

        service.setValueItem(CommonKVBizType.MAP, "a", Map.of("new", "new-setItem-replace"));
        service.setValueItemIfAbsent(CommonKVBizType.MAP, "a", Map.of("new", "new-setItem-non-replace"));
        service.getData(CommonKVBizType.MAP, "a").ifPresent(System.out::println);

        service.removeValueItem(CommonKVBizType.MAP, "a", "new");
    }

    @Test
    void m3() {
        User user = new User();
        user.setUserName("foo");
        user.setPassword("bar");
        user.setAge(18);

        service.setValue(CommonKVBizType.BEAN, "a", user);
        service.getData(CommonKVBizType.BEAN, "a").ifPresent(System.out::println);
    }

    @Test
    void m4() {
        User user = new User();
        user.setUserName("foo");
        user.setPassword("bar");
        user.setAge(18);
        service.setValue(CommonKVBizType.LIST, "a", List.of(user));
    }

    @Test
    void m5() {
        String[] array = {"a", "b"};
        // Integer[] array = {1, 2}; // 异常
        service.setValue(CommonKVBizType.ARRAY, "a", array);
        String[] a = (String[]) service.getData(CommonKVBizType.ARRAY, "a").get();
        System.out.println(Arrays.toString(a));
    }
}