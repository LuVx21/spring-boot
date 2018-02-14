---
title: 
date: 
tags:
- 
---

<!-- TOC -->

- [](#)

<!-- /TOC -->
# 

官方不推荐是用jsp,但jsp还是占有一大片市场,特别是那些旧项目.

使用jsp,首先需要引入jsp依赖:
```xml
```

其次,将项目的打包方式配置为`war`

然后在`src/main/java/`下创建`webapp/WEB-INF`目录,在其中新建目录`views`用于放置jsp文件,
此处新建文件'index.jsp'

```jsp
```

修改项目配置文件:application.properties
```conf
=.jsp
```




