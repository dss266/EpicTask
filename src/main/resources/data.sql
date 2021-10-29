CREATE TABLE task (
	id bigint primary key auto_increment,
	title varchar(200),
	description varchar(200),
	points int,
	status int DEFAULT 0,
	user_id int
);

CREATE TABLE user (
	id bigint PRIMARY KEY auto_increment,
	name varchar(200),
	email varchar(200),
	password varchar(200),
	githubuser varchar(200),
	points int
);

CREATE TABLE role (
	id int primary key auto_increment,
	name varchar(200)
);

INSERT INTO role (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');

CREATE TABLE user_roles (
	user_id int,
	roles_id int
);

INSERT INTO user_roles VALUES (1, 1), (2,2), (3, 2), (4,1), (5,1);


INSERT INTO user (name, email, password, githubuser, points) VALUES
('Joao Carlos', 'joao@gmail.com', '$2a$12$Yce89ySyGptRxj10aJqLiu.islz/wWCs7JDzFITb9x.HmWIoFik7a', 'joaocarloslima', 300),
('Carla Lopes', 'carla@gmail.com', '$2a$12$Yce89ySyGptRxj10aJqLiu.islz/wWCs7JDzFITb9x.HmWIoFik7a', 'carla', 250),
('Fabio Cabrini', 'fabio@fiap.com.br', '$2a$12$Yce89ySyGptRxj10aJqLiu.islz/wWCs7JDzFITb9x.HmWIoFik7a', 'marcos', 130),
('Pablo Andre', 'pabloandre129@gmail.com', '$2a$12$Yce89ySyGptRxj10aJqLiu.islz/wWCs7JDzFITb9x.HmWIoFik7a', 'pabloandre129', 500),
('Administrador', 'admin@fiap.com.br', '$2a$12$uTwT5NR7l4qfZaCMFkVYMOEOU21ExADkKvghwYWznrnJxQ.R2En5O', 'admin', 280);
INSERT INTO task (title, description, points, status, user_id) VALUES(
	'Criar banco de dados',
	'Criar bd oracle na nuvem',
	300,
	100,
	1
);

INSERT INTO task (title, description, points, status) VALUES(
	'Protótipo',
	'Criar protótipo de alta fidelidade',
	150,
	60
);

INSERT INTO task (title, description, points, status, user_id) VALUES(
	'Modelagem de dados',
	'Criar modelo lógico dos dados',
	200,
	95,
	2
);

