CREATE TABLE `user`
(
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_name`   VARCHAR(255)        NOT NULL DEFAULT '',
    `password`    VARCHAR(255)        NOT NULL DEFAULT '',
    `age`         TINYINT UNSIGNED    NULL     DEFAULT 0,
    `birthday`    TIMESTAMP           NULL,
    `update_time` TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_user_name_age` (`user_name`, `age`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
