create table token
(
    id        integer primary key auto_increment not null,
    raw_token varchar(255) not null,
    created_at datetime default now(),
    status    integer default 1
);

create unique index idx_token_raw_token on token (`raw_token`);
