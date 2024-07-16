package org.luvx.boot.mars.runner.cdc;

import io.debezium.config.Configuration;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;

import jakarta.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@Slf4j
// @org.springframework.context.annotation.Configuration
public class MysqlBinlogListener implements SmartLifecycle {
    @Resource
    private Executor         taskExecutor;
    @Resource
    private DebeziumConsumer debeziumConsumer;
    @Resource(name = "debeziumSourceConfig")
    private Configuration    configuration;

    private final List<DebeziumEngine<ChangeEvent<String, String>>> engineList = new ArrayList<>();

    public void init(
            // @Qualifier("debeziumSourceConfig") Configuration configuration
    ) {
        this.engineList.add(DebeziumEngine.create(Json.class)
                .using(configuration.asProperties())
                .notifying(debeziumConsumer)
                .build());
    }

    @Override
    public void start() {
        init();
        for (DebeziumEngine<ChangeEvent<String, String>> engine : engineList) {
            taskExecutor.execute(engine);
        }
    }

    @Override
    public void stop() {
        for (DebeziumEngine<ChangeEvent<String, String>> engine : engineList) {
            if (engine != null) {
                try {
                    engine.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
