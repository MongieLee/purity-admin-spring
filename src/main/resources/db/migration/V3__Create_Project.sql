create table project
(
    id         int primary key auto_increment,
    name       varchar(20),
    u_id        int,
    created_at datetime,
    updated_at datetime,
    UNIQUE (name)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_unicode_ci;;
