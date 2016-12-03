create table vfs_path_types
(
	id 				int			not null,
	name			character	varying(32) not null,
	
	constraint pkey_vfs_path_types primary key (id),
	constraint uk_vfs_path_types_name unique (name)
)
with (
  oids=false
);


create table vfs_paths
(
	id 				bigserial	not null,
	name			character 	varying(64) not null,
	create_date		timestamp 	without time zone not null,
	modify_date		timestamp 	without time zone not null,
	parent_id		bigint,
	type_id			int			not null,
	owner_id		bigint		default null,
	group_id		bigint		default null,
	access			smallint	default 3,
	
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

create table vfs_file_contents
(
	id 				bigint	not null,
	content			oid		default null,
	
	constraint pkey_vfs_file_contents primary key (id),
	constraint fk_vfs_file_contents_id foreign key (id)
      references vfs_paths (id) match simple
      on delete cascade
);


insert into vfs_path_types (id, name) values (0, 'directory');
insert into vfs_path_types (id, name) values (1, 'file');

insert into vfs_paths (name, create_date, modify_date, parent_id, type_id) values ('', current_timestamp, current_timestamp, null, (select id from vfs_path_types where name = 'directory'));
