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


-- 一级菜单
insert into menu(id, name, parent_id, path, `icon`, menu_type, sequence, visible, remark, permission, is_link,
                 state, created_by, created_at, updated_by, updated_at)
values (1, '数据看板', null, '/dashboard', 'bug', 'C', 1, 1, null, null, 0, 1, 'admin', now(), 'admin', now());
insert into menu(id, name, parent_id, path, `icon`, menu_type, sequence, visible, remark, permission, is_link, state,
                 created_by, created_at, updated_by, updated_at)
values (2, '系统管理', null, '/system', 'fire', 'M', 2, 1, null, null, 0, 1, 'admin', now(), 'admin', now());

-- 二级菜单
insert into menu(id, name, parent_id, path, `icon`, menu_type, sequence, visible, remark, permission, is_link,
                 state, created_by, created_at, updated_by, updated_at)
values (3, '菜单管理', 2, '/system/menu', 'like', 'C', 1, 1, null, null, false, true, 'admin', now(), 'admin', now());
insert into menu(id, name, parent_id, path, `icon`, menu_type, sequence, visible, remark, permission, is_link,
                 state, created_by, created_at, updated_by, updated_at)
values (4, '资源管理', 2, '/system/resource', 'close', 'C', 2, 1, null, null, false, true, 'admin', now(), 'admin',
        now());
insert into menu(id, name, parent_id, path, `icon`, menu_type, sequence, visible, remark, permission, is_link, state,
                 created_by, created_at, updated_by, updated_at)
values (5, '角色管理', 2, '/system/role', 'message', 'C', 3, 1, null, null, false, true, 'admin', now(), 'admin',
        now());
insert into menu(id, name, parent_id, path, `icon`, menu_type, sequence, visible, remark, permission, is_link, state,
                 created_by, created_at, updated_by, updated_at)
values (6, '用户管理', 2, '/system/user', 'mobile', 'C', 4, 1, null, null, false, true, 'admin', now(), 'admin',
        now());
