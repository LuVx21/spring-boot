CREATE TABLE `oss_file`
(
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `scope`       VARCHAR(255)        NOT NULL DEFAULT '' COMMENT 'scope',
    `file`        VARCHAR(255)        NOT NULL DEFAULT '' COMMENT '文件名',
    `visit_count` BIGINT(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
    `ext_data`    JSON                NOT NULL COMMENT '扩展数据',

    `create_time` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_file_scope` (`file`, `scope`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment 'oss'
;
