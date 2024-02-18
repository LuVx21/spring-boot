
```bash
docker run -itd --name mongo -p 27017:27017 mongo --auth
docker exec -it mongo mongo admin
db.createUser({ user:'admin',pwd:'1121',roles:[ { role:'userAdminAnyDatabase', db: 'admin'},"readWriteAnyDatabase"]});
db.auth('admin', '1121')
```

```bash
show dbs;
use boot
db.user.insertOne({"userName":"foo", "password":"bar", "age":18})
show dbs
db.createUser({user:"luvx",pwd:"1121",roles:[{role:"readWrite",db:"boot"}]})
```