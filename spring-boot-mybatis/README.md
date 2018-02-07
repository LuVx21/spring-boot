---
title: 
date: 
tags:
- 
---

<!-- TOC -->

- [DB](#db)
- [注解版本](#注解版本)
- [xml版本](#xml版本)
- [注解和xml比较](#注解和xml比较)
- [多数据源](#多数据源)

<!-- /TOC -->

# DB

```shell
use boot;
desc user;
+-----------+--------------+------+-----+---------+----------------+
| Field     | Type         | Null | Key | Default | Extra          |
+-----------+--------------+------+-----+---------+----------------+
| id        | bigint(20)   | NO   | PRI | NULL    | auto_increment |
| age       | int(11)      | NO   |     | NULL    |                |
| password  | varchar(255) | NO   |     | NULL    |                |
| user_name | varchar(255) | NO   | UNI | NULL    |                |
+-----------+--------------+------+-----+---------+----------------+
```

# 注解版本

```shell
git checkout mybatis-annotation
```

# xml版本

```shell
git checkout mybatis-xml
```

# 注解和xml比较

推荐使用注解.
遇到sql的拼接很麻烦也容易出错.
复杂sql的存在使得mapper的可读性变差.将sql移动到xml中去,使得mapper的功能一目了然,在修改了sql
内容后也不用重新编译

# 多数据源

```shell
git checkout mybatis-muldatasource
```