docker container stop spring-boot-case && docker container rm spring-boot-case

mvn clean package -Dmaven.test.skip=true && \

docker build -t F.LuVx/spring-boot-case:latest .

docker run -itd --name spring-boot-case \
-p 8080:8080 \
# --add-host=luvx:127.0.0.1 \
F.LuVx/spring-boot-case:latest
