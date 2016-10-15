insert into user_roles (id, name) values (nextval('user_roles_seq'), 'root');
insert into user_roles (id, name) values (nextval('user_roles_seq'), 'registered');

insert into users (id, login, password, is_registered) values (nextval('users_seq'), 'root', '827ccb0eea8a706c4c34a16891f84e7b', 'y');
insert into user_role_map (user_id, role_id) values ((select id from users where login = 'root'), (select id from user_roles where name = 'root'));
insert into user_role_map (user_id, role_id) values ((select id from users where login = 'root'), (select id from user_roles where name = 'registered'));

insert into user_channel_types (id, name) values (nextval('user_channel_types_seq'), 'email');
insert into user_channel_types (id, name) values (nextval('user_channel_types_seq'), 'phone');
insert into user_channel_types (id, name) values (nextval('user_channel_types_seq'), 'icq');
insert into user_channel_types (id, name) values (nextval('user_channel_types_seq'), 'viber');
insert into user_channel_types (id, name) values (nextval('user_channel_types_seq'), 'telegram');
insert into user_channel_types (id, name) values (nextval('user_channel_types_seq'), 'whatapp');



insert into config_types (id, name) values (nextval('config_types_seq'), 'byte');
insert into config_types (id, name) values (nextval('config_types_seq'), 'short');
insert into config_types (id, name) values (nextval('config_types_seq'), 'integer');
insert into config_types (id, name) values (nextval('config_types_seq'), 'long');
insert into config_types (id, name) values (nextval('config_types_seq'), 'float');
insert into config_types (id, name) values (nextval('config_types_seq'), 'double');
insert into config_types (id, name) values (nextval('config_types_seq'), 'string');