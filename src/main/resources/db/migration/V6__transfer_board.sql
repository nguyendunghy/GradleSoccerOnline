create table transfer_player
(
    id         integer primary key auto_increment not null,
    player_id  integer not null,
    price      integer not null,
    created_at datetime default now(),
    updated_at datetime default now(),
    status     integer  default 1
);


create table transfer_history
(
    id         integer primary key auto_increment not null,
    player_id  integer not null,
    price      integer not null,
    from_team  integer not null,
    to_team    integer not null,
    created_at datetime default now(),
    updated_at datetime default now(),
    status     integer  default 1
);
