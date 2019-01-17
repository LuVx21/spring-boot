---
title: Spring-Boot MyBatis
date: 
tags:
- Java
- SpringBoot
- MyBatis
---

<!-- TOC -->

- [DB](#db)
- [注解版本](#注解版本)
- [xml版本](#xml版本)
- [注解和xml比较](#注解和xml比较)
- [多数据源](#多数据源)

<!-- /TOC -->

# DB

```sql
use boot;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_id_age` (`id`,`age`,`password`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

CREATE TABLE `article` (
  `article_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `article_name` varchar(255) NOT NULL,
  `create_time` date DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```

# 注解版本

先使用注解实现用户的增删改查功能

```conf
# 配置实体alias
mybatis.type-aliases-package=org.luvx.entity
```

```shell
git checkout mybatis-annotation
```

使用上述命令检出注解版本

# xml版本

在注解版本的基础上向xml迁移,实际上也只是将dao的实现放到xml文件中

配置文件在注解版本上新增
```conf
# MyBatis配置文件和映射文件
mybatis.config-locations=classpath:mybatis/mybatis-config.xml
mybatis.mapper-locations=classpath:org/luvx/mapper/*.xml
```

```shell
git checkout mybatis-xml
```
使用上述命令检出xml版本

# 注解和xml比较

推荐使用注解.
使用注解时,使得编码变得方便,但遇到sql的拼接很麻烦也容易出错.
复杂sql的存在使得mapper的可读性变差.将sql移动到xml中去,使得mapper的功能一目了然
在修改了sql内容后也不用重新编译

# 多数据源

```shell
git checkout mybatis-muldatasource
```
使用上述命令检出多数据源代码实现

# todo

* myabtis 部分查询 不修改mapper 在java中指定查询的列和查询条件
* mybatis 条件查询
* 传递多个参数