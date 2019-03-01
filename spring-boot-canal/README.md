

# Usage








# 数据同步方案

`createtime timestamp not null default current_timestamp comment 'xxx'`
`updatetime timestamp not null default current_timestamp on update current_timestamp comment 'xxx'`



# canal

https://github.com/alibaba/canal
https://github.com/alibaba/canal/wiki/ClientExample

实时Binlog采集 + 离线处理Binlog + 还原业务数据



# 处理Binlog

合并对同一条数据的处理


方案:
insert 操作:
直接新增数据即可



update 操作:


Linkedin的开源项目Camus, --> gobblin






面临的问题:

* 服务所在的主机和DB Server 时间不一致
* 首次全量数据同步过程中产生的增量数据



阅读资料

1. [](https://tech.meituan.com/2018/12/06/binlog-dw.html)
2. [](https://www.dozer.cc/2015/03/etl.html)


[![](https://static.segmentfault.com/v-5b1df2a7/global/img/creativecommons-cc.svg)](https://creativecommons.org/licenses/by-nc-nd/4.0/)
