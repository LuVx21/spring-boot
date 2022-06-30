

```bash
docker run -d --name flowable-ui \
-p 50000:8080 \
-e spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver \
-e spring.datasource.url="jdbc:mysql://luvx:53306/flowable-ui?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=true&nullCatalogMeansCurrent=true" \
-e spring.datasource.username=root \
-e spring.datasource.password=1121 \
--restart=always flowable/flowable-ui:6.7.2

mvn dependency:get -DgroupId=mysql -DartifactId=mysql-connector-java -Dversion=8.0.28

docker cp ~/.m2/repository/mysql/mysql-connector-java/8.0.28/mysql-connector-java-8.0.28.jar \
flowable-ui:/app/WEB-INF/lib/mysql-connector-java-8.0.28.jar
```

url: http://luvx:50000/flowable-ui/idm/#/login


