drop table if exists `user`;

create table `user`
(
    id                 int primary key auto_increment,
    username           varchar(20),
    encrypted_password varchar(100),
    nickname           varchar(20),
    avatar             varchar(100),
    status             bit,
    dept_id            int,
    created_at         datetime,
    updated_at         datetime,
    UNIQUE (username)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

# 默认账号密码superadmin 123qwe
insert into user(id, dept_id, username, nickname, encrypted_password, status, created_at, updated_at)
values (1, 1, 'superadmin', '超级管理员', '$2a$10$SFCrQlssKpxsJ5hEidE82.ke88vQO9qQ2ha4ztZFULKnup3guZ5.a', 1, now(),
        now());
