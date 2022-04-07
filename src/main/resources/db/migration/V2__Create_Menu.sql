create table menu
(
    id         int primary key auto_increment,
    name       varchar(20),
    path       varchar(20),
    parent_id       int,
    sequence   int default 0,
    created_at datetime,
    updated_at datetime,
    UNIQUE (name)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_unicode_ci;
