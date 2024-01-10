



### 官方例子

```bash
# 创建topic
bin/kafka-topics.sh --create \
    --zookeeper localhost:2181 \
    --replication-factor 1 \
    --partitions 1 \
    --topic streams-plaintext-input

bin/kafka-topics.sh --create \
    --zookeeper localhost:2181 \
    --replication-factor 1 \
    --partitions 1 \
    --topic streams-wordcount-output \
    --config cleanup.policy=compact

# 启动stream例子
./bin/kafka-run-class.sh org.apache.kafka.streams.examples.wordcount.WordCountDemo

# 生产数据
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic streams-plaintext-input

# 消费数据
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 \
    --topic streams-wordcount-output \
    --from-beginning \
    --formatter kafka.tools.DefaultMessageFormatter \
    --property print.key=true \
    --property print.value=true \
    --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
    --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
```

### 实现stream

```shell
mvn clean package
mvn exec:java -Dexec.mainClass=org.luvx.kafka.streams.examples.WordCountDemo
```

## 资料

https://www.cnblogs.com/huxi2b/p/11525660.html