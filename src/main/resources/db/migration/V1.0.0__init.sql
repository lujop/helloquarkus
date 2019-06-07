create table words
(
    id serial constraint table_name_pk primary key,
    word text not null,
    count int default 0 not null
);