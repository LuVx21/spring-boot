CREATE TABLE `common_key_value`
(
    `id`           BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `biz_type`     INT(11) UNSIGNED    NOT NULL COMMENT '业务标识',
    `common_key`   VARCHAR(255)        NOT NULL COMMENT '键',
    `common_value` JSON                NOT NULL COMMENT '值',
    `invalid`      SMALLINT            NOT NULL DEFAULT 0 COMMENT '失效',
    `create_time`  BIGINT(20) UNSIGNED NOT NULL COMMENT '创建时间',
    `update_time`  BIGINT(20) UNSIGNED NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_key_biz_type` (`common_key`, `biz_type`, `invalid`),
    KEY `idx_biz_type` (`biz_type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '通用kv结构'
;

CREATE TABLE `common_table_field`
(
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `table`       VARCHAR(255)        NOT NULL COMMENT '表',
    `field`       VARCHAR(255)        NOT NULL COMMENT '字段',
    `value`       JSON                NOT NULL COMMENT '值',
    `create_time` BIGINT(20) UNSIGNED NOT NULL COMMENT '创建时间',
    `update_time` BIGINT(20) UNSIGNED NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_key_table_field` (`table`, `field`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
;

CREATE TABLE `common_mapping`
(
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `biz_type`    INT UNSIGNED        NOT NULL COMMENT '业务标识',
    `key`         BIGINT(20)          NOT NULL COMMENT '键',
    `value`       BIGINT(20)          NOT NULL COMMENT '值',
    `create_time` BIGINT(20) UNSIGNED NOT NULL COMMENT '创建时间',
    `update_time` BIGINT(20) UNSIGNED NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_key_biz_type` (`key`, `biz_type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
;