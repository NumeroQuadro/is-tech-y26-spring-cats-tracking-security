create table if not exists cats_friends
(
    cat_id    serial  not null,
    friend_id integer not null,
    primary key (cat_id, friend_id)
);
create table if not exists cats_main_info
(
    cat_birthday date         not null,
    cat_id       serial       not null,
    cat_breed    varchar(255) not null,
    cat_color    varchar(255) not null check (cat_color in ('white', 'semi_color', 'black', 'grey', 'undefined')),
    cat_name     varchar(255) unique,
    primary key (cat_id)
);
create table if not exists owners
(
    user_id        int       not null,
    owner_name     varchar(255) not null unique,
    owner_birthday date         not null,
    primary key (user_id)
);

create table if not exists users
(
    user_id  serial       not null,
    username varchar(100) unique not null,
    password text         not null,
    role     varchar(20)  not null,
    primary key (user_id)
);

create table if not exists owners_with_cats
(
    cat_id  integer not null,
    user_id integer not null,
    primary key (cat_id, user_id)
);
alter table if exists cats_friends
    add constraint cats_friends_cat_id_constraint foreign key (cat_id) references cats_main_info on delete cascade;
alter table if exists cats_friends
    add constraint cats_friends_friend_id_constraint foreign key (friend_id) references cats_main_info on delete cascade;
alter table if exists owners_with_cats
    add constraint owners_with_cats_cat_id_constraint foreign key (cat_id) references cats_main_info on delete cascade;
alter table if exists owners_with_cats
    add constraint owners_with_cats_owner_id_constraint foreign key (user_id) references owners on delete cascade;
alter table if exists owners
    add constraint users_constraint foreign key (user_id) references users on delete cascade;