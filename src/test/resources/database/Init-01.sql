SET search_path TO local;
CREATE EXTENSION if not exists "uuid-ossp";
ALTER EXTENSION "uuid-ossp" SET SCHEMA public;

CREATE TABLE IF NOT EXISTS users (
	created_by varchar(255) NOT NULL,
	created_date timestamp NOT NULL,
	modified_by varchar(255) NULL,
	modified_date timestamp NULL,
	id uuid NOT NULL DEFAULT public.uuid_generate_v4(),
	email varchar(50) NOT NULL,
	password varchar(50) NOT NULL,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS work_item (
	created_by varchar(255) NOT NULL,
	created_date timestamp NOT NULL,
	modified_by varchar(255) NULL,
	modified_date timestamp NULL,
	id uuid NOT NULL DEFAULT public.uuid_generate_v4(),
	user_id uuid NOT NULL,
	title varchar(500) NOT NULL,
	description varchar(2000) NOT NULL,
	is_complete boolean default false,
	assigned_at timestamp NULL,
	percentage numeric(18, 2) NULL DEFAULT 0,
	CONSTRAINT work_item_pkey PRIMARY KEY (id)
);

ALTER TABLE work_item
ADD CONSTRAINT work_item_users_id_fkey
FOREIGN KEY (user_id)
REFERENCES users(id) on delete cascade;


