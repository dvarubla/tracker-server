create table role (
  id int not null primary key,
  name text not null,
  alias text not null,
  constraint role_unique_name unique (alias)
);

create sequence role_id_seq start with 1 increment by 1;

create table privilege (
  id int not null primary key,
  name text not null,
  alias text not null,
  constraint privilege_unique_name unique (alias)
);

create sequence privilege_id_seq start with 1 increment by 1;

create table role_privilege (
  role_id int not null,
  privilege_id int not null,
  primary key (role_id, privilege_id),
  constraint fk_role_id foreign key (role_id) references role(id) on delete cascade,
  constraint fk_privilege_id foreign key (privilege_id) references privilege(id) on delete cascade
);

create table app_user (
  id int not null primary key,
  login text not null,
  password text not null,
  name text not null,
  role_id int not null,
  constraint app_user_unique_login unique (login),
  constraint fk_role_id foreign key (role_id) references role(id) on delete cascade
);

create sequence user_id_seq start with 1 increment by 1;

