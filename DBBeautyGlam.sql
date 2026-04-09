CREATE DATABASE makeup_store;
USE makeup_store;


CREATE TABLE user_role (
id bigint auto_increment primary key,
role_name VARCHAR(50) not null
);

insert into user_role (role_name) values ('ADMIN'), ('CLIENT');

CREATE TABLE users (
id bigint auto_increment primary key,
username varchar(100) not null,
email varchar(150) not null unique,
password varchar(255) not null,
role_id bigint,
foreign key (role_id) references user_role(id)
);

CREATE TABLE product(
id int primary key auto_increment,
product_name varchar(50),
price double,
description varchar(100),
stock int
);



CREATE TABLE orderProduct(
id int primary key auto_increment,
fk_user_id bigint,
foreign key (fk_user_id) references users (id),
order_date date,
total double
);

CREATE TABLE orderDetails(
id int primary key auto_increment,
fk_orderProduct_id int,
foreign key (fk_orderProduct_id) references orderProduct(id),
fk_product_id int,
foreign key (fk_product_id) references product(id),
quantity int
);
