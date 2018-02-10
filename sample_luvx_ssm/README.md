# sample_luvx_ssm

<!-- TOC -->

- [sample_luvx_ssm](#sample_luvx_ssm)
- [数据库](#数据库)

<!-- /TOC -->

模板项目.

* entity_luvx_trial:
* dao_luvx_trial:持久化,Hibernate,Mybatis
* service_luvx_trial:业务逻辑,Spring
* web_luvx_trial:前端控制,Struts,SpringMVC

由下至上的依赖关系


现在同时使用Struts和SpringMVC,在`web.xml`中配置有前端控制器.

持有化使用MyBatis,同时添加有Hibernate的配置和测试例.

若使用Hibernate,需要
在`applicationContext.xml`中放开对`applicationContext-dao_Hibernate.xml`的引用,注释掉`applicationContext-dao_MyBatis.xml`的引用
同时在`applicationContext-service.xml`中放开Hibernate的事务管理配置,注释掉MyBatis的事物配置.
service层中不用修改

项目同时具有Maven,Gradle构建配置,集成有Tomcat插件
```shell
# mvn tomcat7:deploy
```
```shell
# gradle tomcatRunWar
# tomcat->Gretty
gradle appRun
```
即可运行模板项目


# 数据库

```sql
create table `user` (
  `userid`    bigint(20) not null auto_increment,
  `user_name` varchar(255)         default null,
  `password`  varchar(255)         default null,
  `age`       int(11)             default null,
  primary key (`userid`)
)
  engine = innodb
  auto_increment = 2
  default charset = utf8;
```