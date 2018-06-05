


时至今日SSM的结构都已经过时了,Spring-Boot的出现确实是方便了开发的门槛和难度,但同时也让开发者不那么容易接触底层,不那么容易了解底层的配置和实现,尤其是针对初级.
但对于知识的学习却没有过时,还是要从SSM入手理解基本使用和原理,然后再向着Spring-Boot,Spring-Cloud学习.
走不稳是跑不快的.

```
cd sample_luvx_parent && mvn install
cd ../sample_luvx_commons && mvn install
cd ../sample_luvx_ssh && gradle tomcatRunWar
```
数据库名:
* sample:java
* Spring-Boot:boot


* sample_luvx_parent:
* sample_luvx_commons:
* sample_luvx_ssh:Struts+Spring+Hibernate
* sample_luvx_ssm:SpringMVC+Spring+MyBatis

* luvx_trial_Boot:Spring-Boot
* luvx_trial_Boot_module:Spring-Boot模块化