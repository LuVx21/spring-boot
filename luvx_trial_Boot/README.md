---
title: Spring-Boot Web
date:
tags:
- Java
- SpringBoot
---

<!-- TOC -->

- [DB](#db)
- [命令行启动](#命令行启动)
- [多配置文件](#多配置文件)
- [Redis](#redis)

<!-- /TOC -->

# DB

数据库:boot

# 命令行启动

Gradle:
```
gradle bootRun [--debug-jvm]
```

Maven:
```shell
# 方式1
mvn package
java -jar target/xx.jar
# 方式2
mvn spring-boot:run
```

# 多配置文件

为满足不同的环境下使用不同的配置,如下使用:

* application-dev.properties: 开发环境
* application-prod.properties: 生产环境

> 命名满足`application-{profile}.properties`

```
java -jar target/xx.jar --spring.profiles.active=dev
```

引入其他配置文件
```conf
spring.profiles.include=dev1,dev2
```

# Redis

使用Redis需要添加依赖:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

session共享需要添加依赖:
```xml
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
```

> 放在缓存中只是session共享的方案之一



