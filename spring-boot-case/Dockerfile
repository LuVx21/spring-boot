# 设置JAVA版本
#FROM openjdk:21-slim
ARG JRE_VERSION=21-jre-alpine
#ARG JRE_VERSION=21-jre
FROM eclipse-temurin:${JRE_VERSION}
# 设置工作目录
WORKDIR /app
RUN mkdir -p ~/data/luvx/oss
# 指定存储卷, 任何向/tmp写入的信息都不会记录到容器存储层
# VOLUME /tmp
# 拷贝运行JAR包
ADD web/target/web-0.0.0.jar app.jar
# 设置JVM运行参数， 这里限定下内存大小，减少开销
ENV JAVA_OPTS="\
-server \
-Xms256m \
-Xmx512m \
-XX:MetaspaceSize=256m \
-XX:MaxMetaspaceSize=512m \
--enable-preview"
ENV TZ=Asia/Shanghai
# 空参数，方便创建容器时传参
ENV PARAMS=""
# 暴露端口
EXPOSE 9999
# 入口点， 执行JAVA运行命令
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS app.jar $PARAMS"]
