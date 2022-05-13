create
database `security`;
use
`security`;

CREATE TABLE `oauth_client_details`
(
    `client_id`               varchar(48) NOT NULL,
    `resource_ids`            varchar(256)  DEFAULT NULL,
    `client_secret`           varchar(256)  DEFAULT NULL,
    `scope`                   varchar(256)  DEFAULT NULL,
    `authorized_grant_types`  varchar(256)  DEFAULT NULL,
    `web_server_redirect_uri` varchar(256)  DEFAULT NULL,
    `authorities`             varchar(256)  DEFAULT NULL,
    `access_token_validity`   int(11) DEFAULT NULL,
    `refresh_token_validity`  int(11) DEFAULT NULL,
    `additional_information`  varchar(4096) DEFAULT NULL,
    `autoapprove`             varchar(256)  DEFAULT NULL,
    PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
;

INSERT INTO security.oauth_client_details
VALUES ('client_app_id', 'res1', '$2a$10$wvCi1JKnkRrk//heuna.XeyRtm2LFW.lJfS3PrsKixJ3A9ikwmTs.', 'all',
        'authorization_code,refresh_token', 'http://localhost:8082/index.html', null, null, null, null, null),
       ('client_app_id1', 'res1', '$2a$10$wvCi1JKnkRrk//heuna.XeyRtm2LFW.lJfS3PrsKixJ3A9ikwmTs.', 'all',
        'authorization_code,refresh_token', 'http://localhost:8082/index.html', null, null, null, null, null)
;
