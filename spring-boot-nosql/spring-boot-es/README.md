
## es

```bash
mkdir -p ~/docker/es/{data,config,plugins}
echo "http.host: 0.0.0.0" >> ~/docker/es/config/elasticsearch.yml

docker run -d --name es \
-p 59200:9200 \
-p 59300:9300 \
-e "discovery.type=single-node" \
-e ES_JAVA_OPTS="-Xms1024m -Xmx1024m" \
-v ~/docker/es/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
-v ~/docker/es/data:/usr/share/elasticsearch/data \
-v ~/docker/es/plugins:/usr/share/elasticsearch/plugins \
elasticsearch:7.2.0
#curl localhost:9200
```

启动出现问题可尝试
```conf
#config/elasticsearch.yml
xpack.security.enabled: true
```

配置跨域

```bash
docker exec -it es /bin/bash
cd config
# #elasticsearch.yml 添加
# http.cors.enabled: true
# http.cors.allow-origin: "*"
```

## kibana

```bash
mkdir -p ~/docker/kibana/config
touch ~/docker/kibana/config/kibana.yml

docker run -d --name kibana \
--link es \
# -e ELASTICSEARCH_HOSTS="http://172.17.0.2:59200"
#-e ELASTICSEARCH_URL=http://172.17.0.2:59200
-v ~/docker/kibana/config/kibana.yml:/usr/share/kibana/config/kibana.yml \
-p 55601:5601 \
kibana:7.2.0
```

启动出现问题可尝试
```
kibana.yml
修改es的hosts的ip为服务器的ip
设置
xpack.monitoring.ui.container.elasticsearch.enabled: false
```

可视化插件:

```bash
docker run -d --name es_admin \
-p 59100:9100 \
mobz/elasticsearch-head:5-alpine
```

---

## 资料

https://www.devglan.com/apache-kafka/kafka-elasticsearch-logstash-example


```bash
./bin/zookeeper-server-start.sh config/zookeeper.properties
./bin/kafka-server-start.sh config/server.properties
./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic dev-log-test

# vim logstash-kafka.conf
cat >> logstash-kafka.conf << EOF
input {
    kafka {
            bootstrap_servers => "localhost:9092"
            topics => ["dev-log-test"]
    }
}

output {
   elasticsearch {
      hosts => ["localhost:9200"]
      index => "dev-log-test"
      workers => 1
    }
}
EOF
./bin/logstash -f ./config/logstash-kafka.conf

.bin/elasticsearch

.bin/kibana
```