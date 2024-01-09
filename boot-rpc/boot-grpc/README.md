
```bash
grpcurl --plaintext localhost:9090 list
grpcurl --plaintext localhost:9090 list luvx.user.UserInfo
grpcurl --plaintext -d '{"name": "test"}' localhost:9090 luvx.user.UserInfo/selectUserInfo
# 权限
grpcurl --plaintext -d '{"name": "test"}' localhost:9090 luvx.user.UserOperate/updatePassword
```

```bash
grpcui --plaintext localhost:9090
```
