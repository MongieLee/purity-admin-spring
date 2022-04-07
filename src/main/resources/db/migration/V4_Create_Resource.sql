create table resource
(
    id          int primary key auto_increment,
    name        varchar(20),
    url         varchar(50),
    description varchar(100),
    categroy_id Long not null,
    created_at   dateTime,
    updated_at  datetime,
    UNIQUE (name)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_unicode_ci;
