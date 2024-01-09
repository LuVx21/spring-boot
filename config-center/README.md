
```bash
docker run \
-e PARAMS="--spring.datasource.url=jdbc:mysql://192.168.1.10:53306/xxl-conf?Unicode=true&characterEncoding=UTF-8 --spring.datasource.password=1121" \
-p 8080:8080 \
-v /tmp/xxl-conf:/data/applogs \
--name xxl-conf-admin \
-d xuxueli/xxl-conf-admin:1.6.1
```

```bash
gcl https://github.com/xuxueli/xxl-conf/
mvn install -DskipTests -f ./xxl-conf/pom.xml
```

1. 使用配置值的变量不会随着变化
2. 支持的数据类型只有String
3. 全局参数不能引用变量值
