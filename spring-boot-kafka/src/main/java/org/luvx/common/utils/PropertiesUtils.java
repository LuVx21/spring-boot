package org.luvx.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName: org.luvx.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/12/2 10:57
 */
@Slf4j
public class PropertiesUtils {

    public static Properties getProperties(String path) {
        Resource resource = new ClassPathResource(path);
        Properties properties = getProperties(resource);
        if (properties == null) {
            InputStream is = PropertiesUtils.class.getClassLoader().getResourceAsStream(path);
            return getProperties(is);
        }
        return properties;
    }

    public static Properties getProperties(InputStream is) {
        try {
            Properties props = new Properties();
            props.load(is);
            return props;
        } catch (IOException e) {
            log.error("加载配置文件异常", e);
        }
        return null;
    }

    public static Properties getProperties(Resource resource) {
        try {
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            return properties;
        } catch (IOException e) {
            log.error("加载配置文件异常", e);
        }
        return null;
    }
}
