create table customer
(
    customer_id      varchar(10) not null
        primary key,
    customer_name    varchar(20) not null,
    customer_address varchar(50) not null,
    customer_contact varchar(12) not null
);

create table item
(
    item_id         varchar(10)   not null
        primary key,
    item_name       varchar(20)   not null,
    item_stock      int           not null,
    item_unit_price decimal(6, 2) not null
);

create table `order`
(
    order_id    varchar(10)    not null
        primary key,
    order_date  date           not null,
    customer_id varchar(10)    null,
    total       decimal(10, 2) not null,
    constraint order_ibfk_1
        foreign key (customer_id) references customer (customer_id)
);

create index customer_id
    on `order` (customer_id);

create table order_detail
(
    order_id      varchar(10)   not null,
    item_id       varchar(10)   not null,
    item_quantity int           not null,
    item_total    decimal(6, 2) not null,
    primary key (order_id, item_id),
    constraint order_detail_ibfk_1
        foreign key (order_id) references `order` (order_id),
    constraint order_detail_ibfk_2
        foreign key (item_id) references item (item_id)
);

create index item_id
    on order_detail (item_id);

create table user
(
    user_id   int auto_increment
        primary key,
    user_name varchar(20) not null,
    password  varchar(10) not null
);


