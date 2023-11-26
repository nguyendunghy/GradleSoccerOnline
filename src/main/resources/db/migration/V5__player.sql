create table player
(
    id           integer primary key auto_increment not null,
    first_name   varchar(255) not null,
    last_name    varchar(255) not null,
    country      varchar(255) not null,
    age          integer      not null,
    market_value integer      not null,
    type         varchar(255) not null,
    team_id      integer      not null,
    created_at   datetime default now(),
    updated_at   datetime default now(),
    status       integer  default 1
);

