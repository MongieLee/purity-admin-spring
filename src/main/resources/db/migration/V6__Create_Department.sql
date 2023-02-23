drop table if exists t_department;

create table t_department
(
    id           int auto_increment,
    name         varchar(40),
    parent_id     int,
    principal    varchar(20),
    phone_number varchar(20),
    sequence     int,
    state        bit,
    created_at   datetime,
    updated_at   datetime,
    primary key (id),
    unique (name)
) comment '部门表' collate = utf8mb4_unicode_ci;


insert into t_department(name, parent_id, principal, phone_number, sequence, state, created_at, updated_at)
values ('技术研发部', null, 'Lei', '13112311212', 1, b'1', now(), now());

# insert into t_department(name, parent_id, principal, phone_number, sequence, state, created_at, updated_at)
# values ('技术研发部', 1, '黄晓明', '13412312112', 2, b'1', now(), now());
