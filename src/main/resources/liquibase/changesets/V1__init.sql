CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS accounts
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    balance NUMERIC(19, 2),
    CONSTRAINT fk_accounts_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE NO ACTION
);

create table if not exists users_mobile_numbers
(
    user_id bigint       not null,
    mobile_number    varchar(255) not null,
    primary key (user_id, mobile_number),
    constraint fk_users_mobile_numbers_users foreign key (user_id) references users (id) on delete cascade on update no action
);

create table if not exists users_emails
(
    user_id bigint       not null,
    email    varchar(255) not null,
    primary key (user_id, email),
    constraint fk_users_emails_users foreign key (user_id) references users (id) on delete cascade on update no action
);
