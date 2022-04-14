drop table if exists menu;
create table menu
(
    id         bigint(20) not null auto_increment COMMENT '菜单id',
    name       varChar(20) COMMENT '菜单名',
    parent_id  bigint(20) COMMENT '父级菜单id',
    path       varChar(50) COMMENT '路由或外链',
    icon       VARCHAR(50) COMMENT '菜单图标代码',
    menu_type  char(1) COMMENT '菜单类型 取值范围M(目录Menu),C(菜单Carte),F(功能按钮Function)',
    sequence   int(4) DEFAULT 0 COMMENT '当前层级排序',
    visible    bit default TRUE COMMENT '菜单是否展示',
    remark     VARCHAR(100) COMMENT '备注',
    permission VARCHAR(100) COMMENT '权限标识',
    created_by VARCHAR(30) COMMENT '创建者',
    created_at datetime COMMENT '创建时间',
    updated_by VARCHAR(30) COMMENT '更新者',
    updated_at datetime COMMENT '更新时间',
    UNIQUE (name),
    primary key (id)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_unicode_ci COMMENT = '菜单表';

-- 一级菜单
insert into menu(id, name, parent_id, path, `icon`, menu_type, sequence, visible, remark, permission, created_by,
                 created_at, updated_by, updated_at)
values (1, '数据看板', null, '/dashboard', 'bug', 'C', 1, 1, null, null, 'admin', now(), 'admin', now());
insert into menu(id, name, parent_id, path, `icon`, menu_type, sequence, visible, remark, permission, created_by,
                 created_at, updated_by, updated_at)
values (2, '系统管理', null, '/system', 'fire', 'M', 2, 1, null, null, 'admin', now(), 'admin', now());

-- 二级菜单
insert into menu(id, name, parent_id, path, `icon`, menu_type, sequence, visible, remark, permission, created_by,
                 created_at, updated_by, updated_at)
values (3, '菜单管理', 2, '/system/menu', 'like', 'C', 1, 1, null, null, 'admin', now(), 'admin', now());
insert into menu(id, name, parent_id, path, `icon`, menu_type, sequence, visible, remark, permission, created_by,
                 created_at, updated_by, updated_at)
values (4, '资源管理', 2, '/system/resource', 'close', 'C', 2, 1, null, null, 'admin', now(), 'admin', now());
insert into menu(id, name, parent_id, path, `icon`, menu_type, sequence, visible, remark, permission, created_by,
                 created_at, updated_by, updated_at)
values (5, '角色管理', 2, '/system/role', 'message', 'C', 3, 1, null, null, 'admin', now(), 'admin', now());
insert into menu(id, name, parent_id, path, `icon`, menu_type, sequence, visible, remark, permission, created_by,
                 created_at, updated_by, updated_at)
values (6, '用户管理', 2, '/system/user', 'mobile', 'C', 4, 1, null, null, 'admin', now(), 'admin', now());
