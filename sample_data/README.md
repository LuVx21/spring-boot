

本库由主从复制的想法产生

* mybatis
  * 使用
  * 原理分析
* mybatis使用Redis缓存(二级缓存)
  * 原理和使用
  * 非分布式下缓存的实现
* 主从复制
* AOP
  * 代理模式
    * 静态代理
    * 动态代理
  * cglib
  * aspectj
* 读写分离
  * 应用层实现(4种)
  * 中间件实现
    * MyCat
    * share
* Redis缓存
  * 分布式下缓存的实现
  * redis的主从复制实现


MyBatis 整合Redis

MyBatis配置文件中加入
```xml
<setting name="cacheEnabled" value="true"/>
```
```xml
<!-- Mapper中加入 -->
<cache type="org.luvx.redis.RedisCache"/>
```



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