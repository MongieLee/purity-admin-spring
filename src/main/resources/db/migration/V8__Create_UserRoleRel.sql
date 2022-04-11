create table resource_categroy
(
    user_id    int,
    role_id    int,
    created_at dateTime,
    updated_at datetime,
    primary key (user_id, role_id)
)ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_unicode_ci;
