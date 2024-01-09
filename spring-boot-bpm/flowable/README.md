## flowable-ui

```bash
docker run -d --name flowable-ui \
-p 50000:8080 \
-e spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver \
-e spring.datasource.url="jdbc:mysql://luvx:53306/flowable-ui?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=true&nullCatalogMeansCurrent=true" \
-e spring.datasource.username=root \
-e spring.datasource.password=1121 \
--restart=always flowable/flowable-ui:6.7.2

mvn dependency:get -DgroupId=mysql -DartifactId=mysql-connector-java -Dversion=8.0.28

docker cp ~/.m2/repository/mysql/mysql-connector-java/8.0.28/mysql-connector-java-8.0.28.jar \
flowable-ui:/app/WEB-INF/lib/mysql-connector-java-8.0.28.jar
```

url: http://luvx:50000/flowable-ui/idm/#/login

## 表

### 工作流部署 - RepositoryService

| table             | 名称       | 作用  |
|:------------------|:---------|:----|
| act_re_procdef    | 流程定义数据表  |     |
| act_re_model      | 流程设计模型部署 |     |
| act_re_deployment | 部署信息表    |     |

### 工作流运行表 - RuntimeService

| table               | 名称           | 作用  |
|:--------------------|:-------------|:----|
| act_ru_identitylink | 运行时流程人员表     |     |
| act_ru_event_subscr | Event时间监听信息表 |     |
| act_ru_execution    | 运行时流程执行实例表   |     |
| act_ru_job          | 运行时定时任务数据表   |     |
| act_ru_task         | 运行时任务节点表     |     |
| act_ru_variable     | 运行时流程变量数据表   |     |

### 工作流历史表 - HistoryService

| table             | 名称      | 作用            |
|:------------------|:--------|:--------------|
| act_hi_actinst    | 历史节点表   | 记录流程流转过的所有节点  |
| act_hi_taskinst   | 历史任务实例表 | 只记录usertask内容 |
| act_hi_attachment | 历史附件表   |               |
| act_hi_comment    | 历史意见表   |               |
| act_hi_detail     | 历史详情表   | 流程中产生的变量详情    |
| act_hi_varinst    | 历史变量表   |               |

#### 工作流组织机构表 - IdentityService

| table             | 名称          | 作用  |
|:------------------|:------------|:----|
| act_id_user       | 用户信息表       |     |
| act_id_info       | 用户扩展信息表     |     |
| act_id_group      | 用户组信息表      |     |
| act_id_membership | 用户与用户组对应信息表 |     |

### 全局属性表

| table            | 名称     | 作用  |
|:-----------------|:-------|:----|
| act_ge_bytearray | 二进制数据表 |     |
| act_ge_property  | 全局属性   |     |
