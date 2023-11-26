alter table user
    add created_at datetime default now();

alter table user
    add updated_at datetime default now();