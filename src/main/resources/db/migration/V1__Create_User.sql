create table user
(
    id                 int primary key auto_increment,
    username           varchar(20),
    encrypted_password varchar(100),
    avatar             varchar(100),
    status             bit,
    created_at         datetime,
    updated_at         datetime,
    UNIQUE (username)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_unicode_ci;
