<details>
<summary>点击展开目录</summary>
<!-- TOC -->

- [关于](#关于)
- [官方例子](#官方例子)
- [connect rest api](#connect-rest-api)
- [自定义connect](#自定义connect)
- [Usage](#usage)
- [参考](#参考)

<!-- /TOC -->
</details>

## 关于

http://kafka.apache.org/documentation/#connect
https://docs.confluent.io/current/connect.html

## 官方例子

```shell
# 单节点
./bin/connect-standalone.sh config/connect-standalone.properties \
    config/connect-file-source.properties config/connect-file-sink.properties
# 集群
./bin/connect-distributed.sh -daemon config/connect-distributed.properties
```

## connect rest api

> https://kafka.apache.org/documentation/#connect_rest

```http
### 查询

GET http://luvx:8083/connectors HTTP/1.1

### 创建source

POST http://luvx:8083/connectors HTTP/1.1
Content-type: application/json
Accept: application/json

{
    "name": "test-file-source",
    "config": {
        "connector.class": "FileStreamSource",
        "tasks.max": "1",
        "topic": "connect-test",
        "file": "test.txt"
    }
}

### 创建sink

POST http://luvx:8083/connectors HTTP/1.1
Content-type: application/json
Accept: application/json

{
    "name": "test-file-sink",
    "config": {
        "connector.class": "FileStreamSink",
        "tasks.max": "1",
        "topics": "connect-test",
        "file": "test.sink.txt"
    }
}

### 查看

GET http://luvx:8083/connectors/test-file-source/config HTTP/1.1
```

## 自定义connect

实现connect的关键类

```Java
org.apache.kafka.connect.source.SourceConnector
org.apache.kafka.connect.source.SourceTask
org.apache.kafka.connect.source.SourceRecord
org.apache.kafka.connect.sink.SinkConnector
org.apache.kafka.connect.sink.SinkTask
org.apache.kafka.connect.sink.SinkRecord
org.apache.kafka.connect.storage.Converter
org.apache.kafka.connect.transforms.Transformation
```

一些自定义实现

https://github.com/confluentinc/kafka-connect-jdbc/

https://github.com/wushujames/kafka-mysql-connector/blob/master/src/main/java/org/wushujames/connect/mysql/MySqlSourceConnector.java

## Usage

CDC

https://rmoff.net/2018/03/24/streaming-data-from-mysql-into-kafka-with-kafka-connect-and-debezium/

```bash
echo plugin.path=/opt/kafka/plugin >> /opt/kafka/config/connect-distributed.properties
mkdir -p /opt/kafka/plugin/debezium-mysql
cp /opt/debezium/lib/antlr4-runtime-4.7.2.jar /opt/kafka/plugin/debezium-mysql
cp /opt/debezium/lib/debezium-api-1.2.0.Final.jar /opt/kafka/plugin/debezium-mysql
cp /opt/debezium/lib/debezium-connector-mysql-1.2.0.Final.jar /opt/kafka/plugin/debezium-mysql
cp /opt/debezium/lib/debezium-core-1.2.0.Final.jar /opt/kafka/plugin/debezium-mysql
cp /opt/debezium/lib/debezium-ddl-parser-1.2.0.Final.jar /opt/kafka/plugin/debezium-mysql
cp /opt/debezium/lib/mysql-binlog-connector-java-0.20.1.jar /opt/kafka/plugin/debezium-mysql
cp /opt/debezium/lib/mysql-connector-java-8.0.16.jar /opt/kafka/plugin/debezium-mysql
```

启动zk, kafka服务, connect服务, 提交connect任务

## 参考

* [1](https://my.oschina.net/hnrpf/blog/1555915)
* [2](https://blog.csdn.net/u011687037/article/details/57411790)
* [3](https://yanbin.blog/kafka-connect-how-to/#more-9655)
