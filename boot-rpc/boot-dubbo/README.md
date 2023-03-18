

本库整合Spring-Boot和Dubbo

* spring-boot-dubbo-api:服务接口
* spring-boot-dubbo-provider:服务提供者
* spring-boot-dubbo-consumer:服务消费者


com.alibaba.spring.boot
dubbo-spring-boot-starter

有2个仓库:


1. [alibaba-Dubbo](https://github.com/alibaba/dubbo-spring-boot-starter)
2. [Apache-Dubbo](https://github.com/apache/incubator-dubbo-spring-boot-project)

在2018年2月15日,dubbo最终成为Apache基金会孵化项目,因此2也就是官方库,dubbo最终能否成为Apache顶级项目,开发者们就吃着瓜子,啃着西瓜等着吧.

1的版本目前有四个:
v1.0.0 -> 1.0.1 -> 1.0.2 -> v2.0.0
```xml
<groupId>com.alibaba.spring.boot</groupId>
<artifactId>dubbo-spring-boot-starter</artifactId>
<version>2.0.0</version>
```

2的版本目前只有:
0.1.0

```xml
<groupId>com.alibaba.boot</groupId>
<artifactId>dubbo-spring-boot-starter</artifactId>
<version>0.1.0</version>
```

使用时,一眼就能看出的不同就是配置文件中,前者多是`spring.dubbo`开头,后者则是`dubbo.`开头.虽说2才是官方的版本,但Spring-Boot本身就大大减轻了配置工作,
两种都会使用也不会很麻烦.

> 服务的注解是`import com.alibaba.dubbo.config.annotation.Service;`,开发时常会被IDE自动导入spring的.