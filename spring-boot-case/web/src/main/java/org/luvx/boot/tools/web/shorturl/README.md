

## 短域名的生成

1. 域名内容由0-9,a-z,A-Z组成, 共 62 个, 使用 62 进制进行递增(IP暂不考虑)

不能多对一


## 域名映射的存储

`http://www.baidu.com`  `abc`

字典树

长->短: 生成/映射
短->长: 映射

缩减内存的方案
1. 删除长对短的映射