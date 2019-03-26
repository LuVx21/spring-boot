package org.luvx.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName: org.luvx.common.properties
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/26 11:12
 */
@Data
@PropertySource("classpath:config/user.properties")
@ConfigurationProperties(prefix = "user")
@Component
public class UserProperties {
    private String name;
}
