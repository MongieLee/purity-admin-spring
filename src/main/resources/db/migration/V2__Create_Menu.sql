drop table if exists `menu`;

create table `menu`
(
    id         bigint auto_increment comment '菜单id'
        primary key,
    name       varchar(20)         null comment '菜单名',
    parent_id  bigint              null comment '父级菜单id',
    path       varchar(50)         null comment '路由或外链',
    menu_type  char                null comment '菜单类型 取值范围M(目录Menu),C(菜单Carte),F(功能按钮Function)',
    sequence   int(4) default 0    null comment '当前层级排序',
    visible    bit    default b'1' null comment '菜单是否展示',
    comp_name  varchar(30)         null comment '组件名称',
    remark     varchar(100)        null comment '备注',
    permission varchar(100)        null comment '权限标识',
    created_by varchar(30)         null comment '创建者',
    created_at datetime            null comment '创建时间',
    updated_by varchar(30)         null comment '更新者',
    updated_at datetime            null comment '更新时间',
    is_link    bit    default b'0' null comment '是否为外链',
    state      bit    default b'1' null comment '菜单状态',
    constraint name
        unique (name)
) comment '菜单表' collate = utf8mb4_unicode_ci;

INSERT INTO `menu`(name, parent_id, comp_name, path, menu_type, sequence, visible, remark, permission,
                   created_by, created_at, updated_by, updated_at, is_link, state)
VALUES ('首页', NULL, 'board', '/dashboard', 'C', 1, b'1', NULL, NULL, 'superadmin', now(), 'superadmin',
        now(), b'0', b'1');

INSERT INTO `menu`(name, parent_id, comp_name, path, menu_type, sequence, visible, remark, permission,
                   created_by, created_at, updated_by, updated_at, is_link, state)
VALUES ('系统管理', NULL, 'system', '/system', 'M', 2, b'1', NULL, NULL, 'superadmin', now(), 'superadmin',
        now(), b'0', b'1');

INSERT INTO `menu`(name, parent_id, comp_name, path, menu_type, sequence, visible, remark, permission,
                   created_by, created_at, updated_by, updated_at, is_link, state)
VALUES ('菜单管理', 2, 'menu', '/menu', 'C', 1, b'1', NULL, NULL, 'superadmin', now(), 'superadmin',
        now(), b'0', b'1');

INSERT INTO `menu`(name, parent_id, comp_name, path, menu_type, sequence, visible, remark, permission,
                   created_by, created_at, updated_by, updated_at, is_link, state)
VALUES ('部门管理', 3, 'department', '/system/department', 'C', 2, b'1', NULL, NULL, 'superadmin', now(), 'superadmin',
        now(), b'0', b'1');


INSERT INTO `menu`(name, parent_id, comp_name, path, menu_type, sequence, visible, remark, permission,
                   created_by, created_at, updated_by, updated_at, is_link, state)
VALUES ('角色管理', 4, 'role', '/system/role', 'C', 2, b'1', NULL, NULL, 'superadmin', now(), 'superadmin',
        now(), b'0', b'1');

INSERT INTO `menu`(name, parent_id, comp_name, path, menu_type, sequence, visible, remark, permission,
                   created_by, created_at, updated_by, updated_at, is_link, state)
VALUES ('用户管理', 5, 'user', '/system/user', 'C', 2, b'1', NULL, NULL, 'superadmin', now(), 'superadmin',
        now(), b'0', b'1');

INSERT INTO `menu`(name, parent_id, comp_name, path, menu_type, sequence, visible, remark, permission,
                   created_by, created_at, updated_by, updated_at, is_link, state)
VALUES ('修改密码', 6, 'changePassword', '/system/changePassword', 'C', 2, b'1', NULL, NULL, 'superadmin', now(),
        'superadmin',
        now(), b'0', b'1');

INSERT INTO `menu`(name, parent_id, comp_name, path, menu_type, sequence, visible, remark, permission,
                   created_by, created_at, updated_by, updated_at, is_link, state)
VALUES ('新增菜单', 3, null, null, 'F', 1, b'1', NULL, 'menu:create', 'superadmin', now(), 'superadmin',
        now(), b'0', b'1');

INSERT INTO `menu`(name, parent_id, comp_name, path, menu_type, sequence, visible, remark, permission,
                   created_by, created_at, updated_by, updated_at, is_link, state)
VALUES ('编辑按钮', 3, null, null, 'F', 1, b'1', NULL, 'menu:edit', 'superadmin', now(), 'superadmin',
        now(), b'0', b'1');

INSERT INTO `menu`(name, parent_id, comp_name, path, menu_type, sequence, visible, remark, permission,
                   created_by, created_at, updated_by, updated_at, is_link, state)
VALUES ('删除按钮', 3, null, null, 'F', 1, b'1', NULL, 'menu:delete', 'superadmin', now(), 'superadmin',
        now(), b'0', b'1');
