---
title: Spring-Boot Redis
date:
tags:
- Java
- SpringBoot
- Redis
---

<!-- TOC -->


<!-- /TOC -->

JPA+Redis


运行项目
```
mvn spring-boot:run
```
[增](http://localhost:8080/save?userName=test&weight=test&age=25)
[查](http://localhost:8080/find/1)
[删](http://localhost:8080/delete/1)

缓存使用的配置关键在于注解的使用
```Java
# 可缓存,#p0代表实体的第一个属性
@Cacheable(key = "#p0")
# 更新后放入缓存(更新缓存)
@CachePut(key = "#p0.id")
# 删除缓存
@CacheEvict(key = "#p0")
```

> 参考`UserRepository`类