# drop table tvbox_live;
CREATE TABLE `tvbox_live`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `group_name`  varchar(255)        NOT NULL COMMENT '组',
    `name`        varchar(255)        NOT NULL COMMENT '名',
    `url`         varchar(700)        NOT NULL COMMENT 'url',
    `status`      varchar(255)        NOT NULL DEFAULT '' COMMENT '状态',
    `create_time` timestamp           NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time` timestamp           NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_url` (`url`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

