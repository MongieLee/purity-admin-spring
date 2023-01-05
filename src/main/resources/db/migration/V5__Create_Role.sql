create table role
(
    id          int primary key auto_increment,
    name        varchar(20),
    code        varchar(30),
    description varchar(100),
    state       bit,
    created_at  dateTime,
    updated_at  datetime,
    UNIQUE (name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

insert into role (id, name, code, description, created_at, updated_at)
values (1, '管理员', 'admin', '系统管理员，拥有系统全部权限', now(), now());
