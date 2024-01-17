package org.luvx.boot.tools.web.app;

import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.tools.service.oss.OssScopeEnum;
import org.luvx.boot.tools.service.oss.OssService;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Path;

@Slf4j
@Configuration
public class Init implements SmartLifecycle {
    @Override
    public void start() {
        for (OssScopeEnum e : OssScopeEnum.values()) {
            File file = Path.of(OssService.IMG_HOME, e.getCode()).toFile();
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
