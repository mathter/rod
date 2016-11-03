create table users
(
  id 			bigint not null,
  is_registered boolean not null,
  login			character varying(64) not null,
  password		character varying(32) not null,
  
  constraint pkey_users primary key (id),
  constraint uk_users_login unique (login)
)
with
(
	oids = false
);


create table user_roles
(
  id			bigint not null,
  name			character varying(128) not null,
  
  constraint pkey_user_roles primary key (id),
  constraint uk_user_roles_name unique (name)
)
with (
  oids=false
);


create table user_role_map
(
  user_id		bigint not null,
  role_id		bigint not null,
  
  constraint pkey_user_role_map primary key (user_id, role_id),
  constraint fk_user_role_map_role_id foreign key (role_id)
      references user_roles (id) match simple
      on update no action on delete no action,
  constraint fk_user_role_map_user_id foreign key (user_id)
      references users (id) match simple
      on update no action on delete no action
)
with (
  oids=false
);


create table user_confirm_registration
(
  uuid			uuid not null,
  date			timestamp without time zone not null,
  iscomplete	boolean not null,
  user_id		bigint not null,
  
  constraint pkey_user_confirm_registration primary key (uuid),
  constraint fk_user_confirm_registration_user_id foreign key (user_id)
      references users (id) match simple
      on update no action on delete no action
)
with (
  oids=false
);


create table user_channel_types
(
  id			bigint not null,
  name			character varying(64) not null,
  
  constraint pkey_user_channel_types primary key (id)
)
with (
  oids=false
);


create table user_channels
(
  id					bigint not null,
  value					character varying(64) not null,
  user_channel_type_id	bigint not null,
  user_id				bigint not null,
  
  constraint pkey_user_channels primary key (id),
  constraint fk_user_channels_user_channel_type_id foreign key (user_channel_type_id)
      references user_channel_types (id) match simple
      on update no action on delete no action,
  constraint fk_user_channels_user_id foreign key (user_id)
      references users (id) match simple
      on update no action on delete no action,
  constraint uk_user_id_user_channel_type_id unique (user_id, user_channel_type_id)
)
with (
  oids=false
);


create table config_types
(
  id bigint not null,
  name character varying(255) not null,
  
  constraint pkey_config_types primary key (id),
  constraint uk_config_types_name unique (name)
)
with (
  oids=false
);


create table config_records
(
  key character varying(255) not null,
  value character varying(255),
  config_type_id bigint not null,
  constraint pkey_config_records primary key (key),
  constraint fk_config_records_config_type_id foreign key (config_type_id)
      references config_types (id) match simple
      on update no action on delete no action
)
with (
  oids=false
);


create sequence users_seq
  increment 1
  minvalue 1
  maxvalue 9223372036854775807
  start 8
  cache 1;
  

create sequence user_roles_seq
  increment 1
  minvalue 1
  maxvalue 9223372036854775807
  start 3
  cache 1;
  
  
create sequence user_channels_seq
  increment 1
  minvalue 1
  maxvalue 9223372036854775807
  start 7
  cache 1;
  
  
create sequence user_channel_types_seq
  increment 1
  minvalue 1
  maxvalue 9223372036854775807
  start 6
  cache 1;

  
create sequence config_types_seq
  increment 1
  minvalue 1
  maxvalue 9223372036854775807
  start 7
  cache 1;