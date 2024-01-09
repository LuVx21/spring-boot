## 模块

| 服务       | 端口    | 作用  |
|:---------|:------|:----|
| auth     | 50001 |     |
| resource | 50002 |     |
| client1  | 50003 |     |
| client1  | 50004 |     |

## 接口

| uri                | 位置                                                                                        | 作用  |
|:-------------------|:------------------------------------------------------------------------------------------|:----|
| /oauth/authorize   | org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint#approveOrDeny ||
| /oauth/token       | org.springframework.security.oauth2.provider.endpoint.TokenEndpoint#postAccessToken       ||
| /oauth/check_token | org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint#checkToken       ||

`/oauth/token`:
client_secret 可以放在请求的 url 中, 但这样不安全, 可以在请求头中添加:
`Authorization: Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW`
其中后半部分是 `clientId:secret`的Base64值

## 资料
