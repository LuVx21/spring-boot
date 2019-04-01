package org.luvx.module.app.mapper;

import org.junit.Test;
import org.luvx.base.BaseTest;
import org.luvx.module.app.entity.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;

public class AppConfigMapperTest extends BaseTest {
    @Autowired
    AppConfigMapper mapper;

    @Test
    public void method() {
        AppConfig config = AppConfig.builder().configKey("app").configValue("aa").valid(true)
                .createUser("")
                .updateUser("")
                .build();
        // mapper.insertBatch(Arrays.asList(config));
        mapper.insert(config);
    }
}