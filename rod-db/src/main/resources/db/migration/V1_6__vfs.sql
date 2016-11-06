create table vfs_path_types
(
	id 				int not null,
	name			character varying(32) not null,
	
	constraint pkey_vfs_path_types primary key (id),
	constraint uk_vfs_path_types_name unique (name)
)
with (
  oids=false
);


create table vfs_paths
(
	id 				bigint not null,
	name			character varying(64) not null,
	create_date		timestamp without time zone not null,
	modify_date		timestamp without time zone not null,
	parent_id		bigint,
	type_id			int not null,
	
	constraint pkey_vfs_paths primary key (id),
	constraint uk_vfs_paths_name_parent_id unique (name, parent_id),
	constraint fk_vfs_paths_parent_id foreign key (parent_id)
      references vfs_paths (id) match simple
      on delete cascade,
    constraint fk_vfs_paths_type_id foreign key (type_id)
      references vfs_path_types (id) match simple
)
with (
  oids=false
);


create sequence vfs_paths_seq
  increment 1
  minvalue 1
  maxvalue 9223372036854775807
  start 1
  cache 1;


create table vfs_file_contents
(
	id 				bigint not null,
	content			bytea,
	
	constraint pkey_vfs_file_contents primary key (id),
	constraint fk_vfs_file_contents_id foreign key (id)
      references vfs_paths (id) match simple
      on delete cascade
);


insert into vfs_path_types (id, name) values (1, 'directory');
insert into vfs_path_types (id, name) values (2, 'file');