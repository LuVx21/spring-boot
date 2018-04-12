---
title: Spring-Boot JSP
date: 
tags:
- Java
- SpringBoot
---

<!-- TOC -->

- [使用](#使用)

<!-- /TOC -->

# 使用

官方不推荐是用jsp,但jsp还是占有一大片市场,特别是那些旧项目.

使用jsp,首先需要引入jsp依赖:
```groovy
compile('org.apache.tomcat.embed:tomcat-embed-jasper')
compile('javax.servlet:javax.servlet-api')
compile('javax.servlet:jstl')
compileOnly('org.springframework.boot:spring-boot-starter-tomcat')
```

其次,将项目的打包方式配置为`war`

然后在`src/main/`下创建`webapp/WEB-INF`目录,静态文件和jsp文件等放在该目录下

修改项目配置文件:application.properties
```conf
# 配置跳转的前后缀
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp
```

主程序Application.java文件中
继承自SpringBootServletInitializer并重写configure()方法
```Java
@Override
protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(Application.class);
}
```