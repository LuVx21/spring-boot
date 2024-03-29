package org.luvx.boot.tools.web.app;

import com.google.common.collect.Streams;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.tools.service.oss.OssScopeEnum;
import org.luvx.boot.tools.service.oss.OssService;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.luvx.boot.tools.dao.config.SqliteConfig.SQLITE_HOME;

@Slf4j
@Configuration
public class Init implements SmartLifecycle {
    @Override
    public void start() {
        Stream<Path> pathStream = Stream.of(SQLITE_HOME).map(Path::of);
        Stream<Path> pathStream1 = Arrays.stream(OssScopeEnum.values())
                .map(e -> Path.of(OssService.IMG_HOME, e.getCode()));
        Streams.concat(pathStream, pathStream1)
                .map(Path::toFile)
                .forEachOrdered(f -> {
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                });
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
