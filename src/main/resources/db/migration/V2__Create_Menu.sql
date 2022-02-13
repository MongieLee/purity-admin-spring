create table menu
(
    id         int primary key auto_increment,
    name       varchar(20),
    path       varchar(20),
    pid        int,
    created_at datetime,
    updated_at datetime,
    UNIQUE (name)
);
