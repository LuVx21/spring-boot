package org.luvx.boot.mars.runner.cdc;

import io.debezium.config.Configuration;
import io.debezium.connector.mysql.MySqlConnector;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import io.debezium.storage.file.history.FileSchemaHistory;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.storage.FileOffsetBackingStore;
import org.jetbrains.annotations.NotNull;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Main {
    public void pull() {
        final Properties props = getProperties();

        try (DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
                .using(props)
                .notifying(record -> {
                    System.out.println(record.key());
                    System.out.println(record.value());
                })
                .notifying((records, committer) -> {
                    for (ChangeEvent<String, String> r : records) {
                        log.info("cdc数据-key:{}", r.key());
                        log.info("cdc数据-value:{}", r.value());
                        committer.markProcessed(r);
                    }
                    committer.markBatchFinished();
                })
                // 加上回调代码，查看错误信息
                .using((success, message, error) -> {
                    if (!success && error != null) {
                        System.out.println("----------error------");
                        System.out.println(message);
                        error.printStackTrace();
                    }
                })
                .build()
        ) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(engine);

            Thread.sleep(60_000);
            executor.shutdown();

            // 等待引擎处理剩余事件
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    log.info("The embedded engine cant shut down");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private Properties getProperties() {
        return Configuration.create()
                .with("name", "engine")
                .with("offset.storage", FileOffsetBackingStore.class)
                .with("offset.storage.file.filename", "/tmp/offsets.dat")
                .with("offset.flush.interval.ms", "60000")
                .with("converter.schemas.enable", "true")
                /* begin connector properties */
                .with("connector.class", MySqlConnector.class)
                .with("database.server.id", "223344")
                .with("database.hostname", "luvx.rx")
                .with("database.port", "53306")
                .with("database.user", "root")
                .with("database.password", "1121")
                .with("database.include.list", "boot")
                .with("table.include.list", "boot.user")
                // .with("snapshot.mode", "schema_only")
                // .with("decimal.handling.mode", "double")

                .with("topic.prefix", "embedded")
                .with("schema.history.internal", FileSchemaHistory.class)
                .with("schema.history.internal.file.filename", "/tmp/schema.dat")

                .build()
                .asProperties();
    }

    public static void main(String[] args) {
        new Main().pull();
    }
}
