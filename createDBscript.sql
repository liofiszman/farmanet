create table if not exists drug
(
    id   int auto_increment
        primary key,
    name varchar(255) not null
);

create table if not exists hibernate_sequence
(
    next_val bigint null
);

create table if not exists location
(
    id             int auto_increment
        primary key,
    name           varchar(80)  not null,
    address_street varchar(255) not null,
    address_number int          not null,
    postal_code    varchar(80)  not null,
    city           varchar(255) not null,
    province       varchar(255) not null,
    phone_number   varchar(255) not null
);

create table if not exists position
(
    id          int auto_increment
        primary key,
    location_id int          not null,
    name        varchar(255) not null,
    constraint position_location_fk
        foreign key (location_id) references location (id)
);

create table if not exists reason
(
    id   int auto_increment
        primary key,
    name varchar(80) not null
);

create table if not exists status
(
    id   int auto_increment
        primary key,
    name varchar(80) not null
);

create table if not exists type
(
    id   int auto_increment
        primary key,
    name varchar(80) not null
);

create table if not exists category
(
    id      int auto_increment
        primary key,
    name    varchar(80) not null,
    type_id int         not null,
    constraint category_type_fk
        foreign key (type_id) references type (id)
);

create table if not exists unit
(
    id          int auto_increment
        primary key,
    description varchar(255) not null
);

create table if not exists user
(
    id                  int auto_increment
        primary key,
    email               varchar(255)         not null,
    password            varchar(255)         not null,
    first_name          varchar(255)         not null,
    last_name           varchar(255)         not null,
    active              tinyint(1) default 1 not null,
    role_id             int                  null,
    default_location_id int                  null,
    constraint user_location__fk
        foreign key (default_location_id) references location (id)
);

create table if not exists input_request
(
    id            int auto_increment
        primary key,
    notes         varchar(255) null,
    location_id   int          not null,
    requester_id  int          not null,
    approver_id   int          not null,
    status_id     int          not null,
    creation_date datetime     not null,
    update_time   datetime     not null,
    constraint input_request__approver_fk
        foreign key (approver_id) references user (id),
    constraint input_request__requester_fk
        foreign key (requester_id) references user (id),
    constraint input_request__status_fk
        foreign key (status_id) references status (id),
    constraint input_request_location_fk
        foreign key (location_id) references location (id)
);

create table if not exists output
(
    id           int auto_increment
        primary key,
    location_id  int          not null,
    requester_id int          not null,
    reason_id    int          not null,
    notes        varchar(255) null,
    update_time  datetime     not null,
    constraint output__location_fk
        foreign key (location_id) references location (id),
    constraint output__reason_fk
        foreign key (reason_id) references reason (id),
    constraint output__requester_fk
        foreign key (requester_id) references user (id)
);

create table if not exists vendor
(
    id   int auto_increment
        primary key,
    name varchar(255) not null
);

create table if not exists item
(
    id           int auto_increment
        primary key,
    name         varchar(255) not null,
    description  varchar(255) null,
    presentation varchar(255) not null,
    quatity      int          not null,
    category_id  int          not null,
    vendor_id    int          null,
    unit_id      int          null,
    constraint item_category_fk
        foreign key (category_id) references category (id),
    constraint item_unit_fk
        foreign key (unit_id) references unit (id),
    constraint item_vendor_fk
        foreign key (vendor_id) references vendor (id)
);

create table if not exists input_request_item
(
    id               int auto_increment
        primary key,
    input_request_id int      not null,
    item_id          int      not null,
    quantity         int      not null,
    status_id        int      not null,
    delivery_date    datetime null,
    constraint input_request_item__input_request_fk
        foreign key (input_request_id) references input_request (id),
    constraint input_request_item__item_fk
        foreign key (item_id) references item (id),
    constraint input_request_item__status_fk
        foreign key (status_id) references status (id)
);

create table if not exists item_drug
(
    id      int auto_increment
        primary key,
    drug_id int not null,
    item_id int not null,
    constraint item_drug__drug_fk
        foreign key (drug_id) references drug (id),
    constraint item_drug__item_fk
        foreign key (item_id) references item (id)
);

create table if not exists output_item
(
    id        int auto_increment
        primary key,
    quantity  int          not null,
    output_id int          not null,
    item_id   int          not null,
    notes     varchar(255) null,
    constraint output_item__item_fk
        foreign key (item_id) references item (id),
    constraint output_item__output_fk
        foreign key (output_id) references output (id)
);

create table if not exists stock
(
    id              int auto_increment
        primary key,
    item_id         int          not null,
    location_id     int          not null,
    current_stock   int          not null,
    last_update     datetime     not null,
    batch_number    varchar(255) not null,
    expiration_date date         not null,
    constraint stock__item_fk
        foreign key (item_id) references item (id),
    constraint stock__location_fk
        foreign key (location_id) references location (id)
);


