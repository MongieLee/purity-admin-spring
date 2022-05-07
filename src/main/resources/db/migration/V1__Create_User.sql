create table user
(
    id                 int primary key auto_increment,
    username           varchar(20),
    encrypted_password varchar(100),
    nickname           varchar(20),
    avatar             varchar(100),
    status             bit,
    created_at         datetime,
    updated_at         datetime,
    UNIQUE (username)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

insert into user(id, username, encrypted_password, status, created_at, updated_at)
values ('1', '123', '$2a$10$yyXdZpW5iJX2MJx2Rmx4zufln1Dd9M8QZ..sqLcvC4d4.qa9hHAV6', 1, now(), now())