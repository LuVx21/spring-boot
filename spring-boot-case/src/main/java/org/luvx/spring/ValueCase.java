package org.luvx.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: org.luvx.spring
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/7/22 15:19
 */
@Component
public class ValueCase {
    @Value("${org.luvx.description}")
    private String              desc;
    @Value("#{'${org.luvx.description}'.split(' ')}")
    private List<String>        list;
    @Value("#{${value.map}}")
    private Map<String, String> map;

    @PostConstruct
    public void init() {
        System.out.println(desc);
        System.out.println(list);
        System.out.println(map);
    }
}
