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
VALUES ('client-jwt', 'res1', '$2a$10$rYoZxlF/LeT.SITl2RNSpu78InBDatr6mP/O34y.WOYTz2e8zSafK', 'all', 'authorization_code,refresh_token', 'http://localhost:8082/login', null, null, null, null, null);
 ('client-sso1', null, '$2a$10$rYoZxlF/LeT.SITl2RNSpu78InBDatr6mP/O34y.WOYTz2e8zSafK', 'all', 'authorization_code,refresh_token', 'http://localhost:8083/login', null, null, null, null, null),
 ('client-sso2', null, '$2a$10$rYoZxlF/LeT.SITl2RNSpu78InBDatr6mP/O34y.WOYTz2e8zSafK', 'all', 'authorization_code,refresh_token', 'http://localhost:8084/login', null, null, null, null, null),
 ('user-server', null, '$2a$10$rYoZxlF/LeT.SITl2RNSpu78InBDatr6mP/O34y.WOYTz2e8zSafK', 'all', 'authorization_code,refresh_token', null, null, null, null, null, null)
;

INSERT INTO security.oauth_client_details
VALUES ('code-client', null, '$2a$10$jENDQZRtqqdr6sXGQK.L0OBADGIpyhtaRfaRDTeLKI76I/Ir1FDn6', 'all', 'authorization_code,refresh_token', 'http://localhost:6102/client-authcode/login', null, 3600, 36000, null, '1'),
 ('order-client', null, '$2a$10$GoIOhjqFKVyrabUNcie8d.ADX.qZSxpYbO6YK4L2gsNzlCIxEUDlW', 'all', 'authorization_code,refresh_token,password', null, null, 3600, 36000, null, '1'),
 ('user-client', null, '$2a$10$o2l5kA7z.Caekp72h5kU7uqdTDrlamLq.57M1F6ulJln9tRtOJufq', 'all', 'authorization_code,refresh_token,password', null, null, 3600, 36000, null, '1')
;
