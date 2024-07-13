
```bash
grpcurl --plaintext localhost:9090 list
grpcurl --plaintext localhost:9090 list luvx.user.UserInfo
grpcurl --plaintext -d '{"name": "张三"}' localhost:9090 luvx.user.UserInfo/oneToOne
# 权限
grpcurl --plaintext -d '{"name": "张三"}' localhost:9090 luvx.user.UserOperate/updatePassword
```

```bash
grpcui --plaintext localhost:9090
```


# protobuf

[官网](https://protobuf.dev/)

# grpc

[官网](https://grpc.io/)

# grpc-boot-start

[Github](https://github.com/grpc-ecosystem/grpc-spring)
