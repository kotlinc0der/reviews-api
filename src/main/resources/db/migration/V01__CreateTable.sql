
create table IF NOT EXISTS product (
    id MEDIUMINT not null primary key AUTO_INCREMENT,
    name varchar(200) not null,
    description varchar(1000) not null,
    price DOUBLE not null default 0.0,
    average_rating DOUBLE PRECISION(4, 2) default 0.0,
    created_date datetime not null
);

create table IF NOT EXISTS review (
    id MEDIUMINT not null primary key AUTO_INCREMENT,
    content varchar(2000) not null,
    rating DOUBLE PRECISION(4, 2) not null default 0.0,
    product_id MEDIUMINT not null,
    created_date datetime not null,
    foreign key (product_id) references product (id)
);

create table IF NOT EXISTS comment (
    id MEDIUMINT not null primary key AUTO_INCREMENT,
    content varchar(2000) not null,
    review_id MEDIUMINT not null,
    created_date datetime not null,
    foreign key (review_id) references review (id)
);