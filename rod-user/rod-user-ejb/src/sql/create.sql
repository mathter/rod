create table users
(
	id			integer		primary key,
	login		varchar(64)	not null,
	password	bytea
);

create sequence users_seq;