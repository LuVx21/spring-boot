CREATE TABLE `common_key_value`
(
    `id`           BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `biz_type`     INT(11) UNSIGNED NOT NULL COMMENT '业务标识',
    `common_key`   VARCHAR(255) NOT NULL COMMENT '键',
    `common_value` JSON         NOT NULL COMMENT '值',
    `create_time`  BIGINT(20) UNSIGNED NOT NULL COMMENT '创建时间',
    `update_time`  BIGINT(20) UNSIGNED NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_biz_type_key` (`biz_type`, `common_key`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;