 create table if not exists wallet(
    id uuid primary key,
    username varchar(250),
    mobile varchar(10),
    balance float default 0);