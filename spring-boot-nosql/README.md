
```bash
docker run -itd --name mongo -p 27017:27017 mongo --auth
docker exec -it mongo mongo admin
```