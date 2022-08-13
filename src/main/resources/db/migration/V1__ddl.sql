create table users
(
    id         serial primary key,
    first_name varchar(255)         not null,
    last_name  varchar(255)         not null,
    login      varchar(255)         not null
        constraint login unique,
    password   varchar(255)         not null,
    enabled    boolean default true not null,
    role       varchar(255)         not null
);

create table orders
(
    id            serial primary key,
    description   varchar(255),
    creation_date timestamp not null default current_timestamp,
    users_id      integer   not null references users
);
/*
 Подумать, нужно ли orders_id сделать not null
 */
create table pancakes
(
    id        serial primary key,
    orders_id integer references orders
);
/*
 Подумать, нужно ли pancakes_id сделать not null
 */
create table ingredients
(
    id       serial primary key,
    name     varchar(255) not null,
    price    float8       not null,
    category varchar(255) not null,
    healthy  boolean      not null
);

create table pancake_ingredient
(
    pancakes_id    integer not null references pancakes,
    ingredients_id integer not null references ingredients
);
