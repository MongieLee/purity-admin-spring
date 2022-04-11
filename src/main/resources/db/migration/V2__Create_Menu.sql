create table menu
(
    id         int primary key auto_increment,
    name       varchar(20),
    path       varchar(20),
    parent_id  int,
    sequence   int default 0,
    created_at datetime,
    updated_at datetime,
    UNIQUE (name)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

insert into menu(id, name, path, parent_id, created_at, updated_at)
values (1, '系统管理', '/system', null, now(), now());
insert into menu(id, name, path, parent_id, created_at, updated_at)
values (2, '菜单管理', '/menu', 1, now(), now());
insert into menu(id, name, path, parent_id, created_at, updated_at)
values (3, '资源管理', '/resource', 1, now(), now());
insert into menu(id, name, path, parent_id, created_at, updated_at)
values (4, '角色管理', '/role', 1, now(), now());
insert into menu(id, name, path, parent_id, created_at, updated_at)
values (5, '用户管理', '/user', 1, now(), now());