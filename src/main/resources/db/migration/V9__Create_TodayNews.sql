create table today_news
(
    id           int primary key auto_increment,
    title        varchar(100),
    content      text,
    cover_img     varchar(200),
    sequence     int default 0,
    is_publish    bit,
    created_by   varchar(20),
    updated_by   varchar(20),
    published_by varchar(20),
    created_at   dateTime,
    updated_at   datetime,
    published_at   datetime
)ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_unicode_ci;
