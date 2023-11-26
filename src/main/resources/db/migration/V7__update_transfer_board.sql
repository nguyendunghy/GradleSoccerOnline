alter table transfer_history
change from_team from_team_id integer not null;

alter table transfer_history
    change to_team to_team_id integer not null;