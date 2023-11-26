create table user
(
    id           integer primary key auto_increment not null,
    username     varchar(255) not null,
    password     varchar(255) not null,
    first_name   varchar(255) not null,
    last_name    varchar(255) not null,
    country      varchar(255) not null,
    age          integer      not null,
    market_value integer,
    status       integer
);
