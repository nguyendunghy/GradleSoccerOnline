create table team
(
    id         integer primary key auto_increment not null,
    name       varchar(255) not null,
    country    varchar(255) not null,
    value      integer      not null,
    budget     integer      not null,
    created_at datetime default now(),
    updated_at datetime default now(),
    status     integer  default 1
);

