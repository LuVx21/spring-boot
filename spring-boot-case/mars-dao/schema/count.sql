CREATE TABLE IF NOT EXISTS `count`
(
    `count_id`    bigint(20) unsigned NOT NULL,
    `count_type`  int unsigned        NOT NULL,
    `count_value` int unsigned        NOT NULL,
    `update_time` bigint(20) unsigned NOT NULL DEFAULT '0',
    PRIMARY KEY (`count_id`, `count_type`),
    KEY `idx_count_type` (`count_type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;