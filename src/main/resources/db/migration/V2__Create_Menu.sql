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

insert into menu(id, name, path, parent_id, sequence, created_at, updated_at)
values (1, '数据看板', '/dashboard', null, 1 now(), now());
insert into menu(id, name, path, parent_id, created_at, updated_at)
values (2, '系统管理', '/system', null, 2, now(), now());
insert into menu(id, name, path, parent_id, created_at, updated_at)
values (3, '菜单管理', '/menu', 1, now(), 3, now());
insert into menu(id, name, path, parent_id, created_at, updated_at)
values (4, '资源管理', '/resource', 1, 4, now(), now());
insert into menu(id, name, path, parent_id, created_at, updated_at)
values (5, '角色管理', '/role', 1, 5, now(), now());
insert into menu(id, name, path, parent_id, created_at, updated_at)
values (6, '用户管理', '/user', 1, 6, now(), now());