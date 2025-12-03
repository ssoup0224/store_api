create table categories
(
    id   tinyint auto_increment
        primary key,
    name varchar(255) null
);

create table products
(
    id          bigint auto_increment
        primary key,
    description varchar(255)   null,
    name        varchar(255)   null,
    price       decimal(38, 2) null,
    category_id tinyint        null,
    constraint FKog2rp4qthbtt2lfyhfo32lsw9
        foreign key (category_id) references categories (id)
);

create table users
(
    id       bigint auto_increment
        primary key,
    email    varchar(255) null,
    name     varchar(255) null,
    password varchar(255) null
);

create table addresses
(
    id      bigint auto_increment
        primary key,
    city    varchar(255) null,
    state   varchar(255) null,
    street  varchar(255) null,
    zip     varchar(255) null,
    user_id bigint       null,
    constraint FK1fa36y2oqhao3wgg2rw1pi459
        foreign key (user_id) references users (id)
);

create table profiles
(
    id             bigint       not null
        primary key,
    bio            varchar(255) null,
    date_of_birth  date         null,
    loyalty_points int          null,
    phone_number   varchar(255) null,
    constraint FK55e5d3sfwkob62wtprm633alk
        foreign key (id) references users (id),
    constraint profiles_ibfk_1
        foreign key (id) references users (id)
);

create table wishlist
(
    user_id    bigint not null,
    product_id bigint not null,
    primary key (user_id, product_id),
    constraint FK6p7qhvy1bfkri13u29x6pu8au
        foreign key (product_id) references products (id),
    constraint FKtrd6335blsefl2gxpb8lr0gr7
        foreign key (user_id) references users (id)
);

