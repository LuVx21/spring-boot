
* [TkMybatis](https://cloud.tencent.com/developer/article/1756079)

```bash
mkdir -p ~/docker/mysql/{conf,logs,data}
touch ~/docker/mysql/my.cnf
```

```bash
docker run -d --name mysql \
-p 3306:3306 \
--restart=always --privileged=true  \
-v ~/docker/mysql/data/:/var/lib/mysql \
-v ~/docker/mysql/logs/:/var/log/mysql \
-v ~/docker/mysql/conf/:/etc/mysql \
-e MYSQL_ROOT_PASSWORD=1121 \
mysql:5.7
```