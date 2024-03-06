package org.luvx.boot.tools.runner.cdc;

import io.debezium.connector.mysql.MySqlConnector;
import lombok.Data;
import org.apache.kafka.connect.storage.FileOffsetBackingStore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Data
@Configuration
@ConfigurationProperties(prefix = "debezium.source")
public class MysqlBinlogConfig {
    private String hostname;
    private String port;
    private String user;
    private String password;
    private String databaseIncludeList;
    private String tableIncludeList = "";
    private String storageFile;
    private String historyFile;
    private Long   flushInterval;
    private String serverId;
    private String serverName;

    @Bean("debeziumSourceConfig")
    public io.debezium.config.Configuration MysqlBinlogConfig() throws Exception {
        checkFile();
        return io.debezium.config.Configuration.create()
                .with("name", "mysql_connector")
                .with("offset.storage", FileOffsetBackingStore.class)
                .with("offset.storage.file.filename", storageFile)
                .with("offset.flush.interval.ms", flushInterval)
                .with("snapshot.mode", "Schema_only")
                .with("converter.schemas.enable", true)

                .with("connector.class", MySqlConnector.class)
                .with("database.server.id", serverId)
                .with("database.server.name", serverName)
                .with("database.hostname", hostname)
                .with("database.port", port)
                .with("database.user", user)
                .with("database.password", password)
                .with("database.include.list", databaseIncludeList)
                .with("table.include.list", tableIncludeList)
                .with("topic.prefix", "embedded")

                .with("schema.history.internal", "io.debezium.storage.file.history.FileSchemaHistory")
                .with("schema.history.internal.file.filename", "/tmp/schema.dat")
                .build();
    }

    private void checkFile() throws IOException {
        String dir = storageFile.substring(0, storageFile.lastIndexOf("/"));
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File file = new File(storageFile);
        if (!file.exists()) {
            file.createNewFile();
        }
    }
}
