引入了 spring-boot-starter-jdbc 之后, 会自动注入一个 DataSourceTransactionManager 类型 bean 对象, 这个对象有两个名称, 分别为 transactionManager 和 platformTransactionManager .
引入了 spring-boot-starter-data-jpa 依赖后, 会自动注入一个 JpaTransactionManager 类型 bean 对象, 这个对象有两个名称, 分别为 transactionManager 和 platformTransactionManager.


DataSourceTransactionManager



```sql
CREATE TABLE `t_config` (
  `host` varchar(255) NOT NULL,
  `port` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `isvalid` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```