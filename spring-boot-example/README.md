

```bash
nohup java -jar target/example.jar > ./luvx.log < /dev/null &
```

# Restful接口


# 注解

方法执行时间计测注解(注解+注解解析)
  注解的使用和解析(字节码获取注解信息)

日志记录
接口或者说一段程序，其入口要有日志，记录传入的数据
部分重要的处理逻辑要有日志输出
出口也要有日志，记录其最终的处理结果

读取属性文件(配置类+配置文件)

# Mybatis-plus

PageHelper

# druid

http://127.0.0.1:8090/druid/index.html

# swagger

http://localhost:8090/swagger-ui.html

# 动态数据源

https://github.com/baomidou/dynamic-datasource-spring-boot-starter

```xml
<dependency>
  <groupId>com.baomidou</groupId>
  <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
  <version>${version}</version>
</dependency>
```
# TiDb

# 缓存

Ehcache

Redis

Mongodb

# 任务调度

Quartz

# 安全

Shiro

# 分布式

# 配置表

# java8

使用新日期API

Java8里面新出来了一些API，LocalDate、LocalTime、LocalDateTime 非常好用 如果想要在JDBC中，使用Java8的日期LocalDate、LocalDateTime，则必须要求数据库驱动的版本不能低于4.2
mysql-connector-java版本低于5.1.37，则数据库的驱动版本低于4.2

<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-typehandlers-jsr310</artifactId>
    <version>1.0.1</version>
</dependency>
https://blog.csdn.net/weixin_38553453/article/details/75050632