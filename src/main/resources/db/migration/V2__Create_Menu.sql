drop table if exists menu;
-- auto-generated definition
create table menu
(
    id         bigint auto_increment comment '菜单id'
        primary key,
    name       varchar(20) null comment '菜单名',
    parent_id  bigint null comment '父级菜单id',
    path       varchar(50) null comment '路由或外链',
    icon       varchar(50) null comment '菜单图标代码',
    menu_type  char null comment '菜单类型 取值范围M(目录Menu),C(菜单Carte),F(功能按钮Function)',
    sequence   int(4) default 0 null comment '当前层级排序',
    visible    bit default b'1' null comment '菜单是否展示',
    comp_name  varchar(30) null comment '组件名称',
    remark     varchar(100) null comment '备注',
    permission varchar(100) null comment '权限标识',
    created_by varchar(30) null comment '创建者',
    created_at datetime null comment '创建时间',
    updated_by varchar(30) null comment '更新者',
    updated_at datetime null comment '更新时间',
    is_link    bit default b'0' null comment '是否为外链',
    state      bit default b'1' null comment '菜单状态',
    constraint name
        unique (name)
) comment '菜单表' collate = utf8mb4_unicode_ci;


INSERT INTO `menu`
VALUES (1, '首页', NULL, 'board', '/dashboard', 'bug', 'C', 1, b'1', NULL, NULL, 'admin', '2022-12-28 14:41:20', 'admin',
        '2022-12-28 14:41:20', b'0', b'1');
INSERT INTO `menu`
VALUES (2, '系统管理', NULL, 'system', '/system', 'fire', 'M', 2, b'1', NULL, NULL, 'admin', '2022-12-28 14:41:20', 'admin',
        '2022-12-28 14:41:20', b'0', b'1');
INSERT INTO `menu`
VALUES (3, '菜单管理', 2, 'menu', '/system/menu', 'like', 'C', 1, b'1', NULL, NULL, 'admin', '2022-12-28 14:41:20', 'admin',
        '2022-12-28 14:41:20', b'0', b'1');
INSERT INTO `menu`
VALUES (4, '部门管理', 2, 'department', '/system/department', 'close', 'C', 2, b'1', NULL, NULL, 'admin',
        '2022-12-28 14:41:20', 'admin', '2022-12-28 14:41:20', b'0', b'1');
INSERT INTO `menu`
VALUES (5, '角色管理', 2, 'role', '/system/role', 'message', 'C', 3, b'1', NULL, NULL, 'admin', '2022-12-28 14:41:20',
        'admin', '2022-12-28 14:41:20', b'0', b'1');
INSERT INTO `menu`
VALUES (6, '用户管理', 2, 'user', '/system/user', 'mobile', 'C', 4, b'1', NULL, NULL, 'admin', '2022-12-28 14:41:20',
        'admin', '2022-12-28 14:41:20', b'0', b'1');
INSERT INTO `menu`
VALUES (7, '修改密码', 2, 'changePassword', '/system/changePassword', NULL, 'C', 5, b'1', NULL, NULL, 'superadmin',
        '2023-01-06 13:39:01', 'superadmin', '2023-01-06 13:39:01', b'0', b'1');
INSERT INTO `menu`
VALUES (8, '新增菜单', 3, NULL, NULL, NULL, 'F', 1, b'1', NULL, 'menu:create', 'superadmin', '2023-01-09 11:45:36',
        'superadmin', '2023-01-09 11:45:36', b'0', b'1');
INSERT INTO `menu`
VALUES (10, '编辑按钮', 3, NULL, NULL, NULL, 'F', 2, b'1', NULL, 'menu:edit', 'superadmin', '2023-01-09 15:53:10',
        'superadmin', '2023-01-09 15:53:10', b'0', b'1');
INSERT INTO `menu`
VALUES (11, '删除按钮', 3, NULL, NULL, NULL, 'F', 3, b'1', NULL, 'menu:delete', 'superadmin', '2023-01-09 17:00:52',
        'superadmin', '2023-01-09 17:00:52', b'0', b'1');
