-- 1. Create users table
create table if not exists users (
   name       varchar(255) not null,
   email      varchar(255) not null unique,
   password   varchar(255) not null,
   created_at timestamp not null default current_timestamp
);

-- 2. Insert a user
insert into users (
   name,
   email,
   password
) values ( 'rifqi1',
           'rifqi1@example.com',
           '123' );