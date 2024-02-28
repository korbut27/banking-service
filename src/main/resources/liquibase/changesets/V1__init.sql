create table if not exists users
(
    id        bigserial primary key,
    username  varchar(255) not null unique,
    full_name varchar(255) not null,
    password  varchar(255) not null
);

create table if not exists accounts
(
    id      bigserial primary key,
    user_id bigint,
    balance numeric(19, 2),
    constraint fk_accounts_users foreign key (user_id) references users (id) on delete cascade on update no action
);

create table if not exists users_mobile_numbers
(
    user_id       bigint not null,
    mobile_number varchar(255) not null,
    primary key (user_id, mobile_number),
    constraint fk_users_mobile_numbers_users foreign key (user_id) references users (id) on delete cascade on update no action
);

create table if not exists users_emails
(
    user_id  bigint not null,
    email    varchar(255) not null,
    primary key (user_id, email),
    constraint fk_users_emails_users foreign key (user_id) references users (id) on delete cascade on update no action
);
