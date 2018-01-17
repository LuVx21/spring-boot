# luvx_trial


模板项目.

使用Maven

* dao_luvx_trial:持久化,Hibernate,Mybatis
* service_luvx_trial:业务逻辑,Spring
* web_luvx_trial:前端控制,Struts,SpringMVC

现在同时使用Struts和SpringMVC,在`web.xml`中配置有前端控制器.


使用Hibernate,放开`applicationContext-service.xml`中事务管理配置,注释掉Mybatis的实物配置.

同时在`applicationContext.xml`中放开对`applicationContext-dao_Hibernate.xml`的引用