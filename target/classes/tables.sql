#~~~~~~~~~~~~~~~~~~~~~~~~ Creating tables ~~~~~~~~~~~~~~~~~~~~~~~~

create table users (
	id bigint auto_increment not null,
    name varchar(100) not null,
    active bit default 1,
    #role enum('ROLE_ADMIN', 'ROLE_USER'),
    password varchar(255) not null,
    email varchar(100) not null,
    primary key (id)
) engine=InnoDB character set = utf8;

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

create table role (
	id bigint auto_increment not null,
    role varchar(50),
    primary key(id)
) engine=InnoDB character set = utf8;

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

create table user_roles (	
	user_id bigint not null,
    roles varchar(50) not null 
) engine =  InnoDB character set = utf8;

#create table user_roles (	
#	user_id bigint not null,
#    role_id varchar(45) not null 
#) engine =  InnoDB character set = utf8;
#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

create table category (
	id bigint auto_increment not null,
    title text not null,
    primary key (id)
) engine=InnoDB character set = utf8;

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

create table interview (
	id bigint auto_increment not null,
    date timestamp default now(),
    user_id bigint not null, 
    title text not null, 
    isAnonymous bit default 0,
    isComment bit default 0,
    active bit default 1,
    primary key (id)
) engine=InnoDB character set = utf8;

create table interviews (
	category_id bigint not null, 
    interview_id bigint not null
) engine=InnoDB character set = utf8;

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

create table comment (
	id bigint auto_increment not null, 
    text text not null,
    user_id bigint, 
    anonymous bit default 0,
    date timestamp default now(),
    primary key (id)
) engine=InnoDB character set = utf8;

create table comments (
	interview_id bigint not null, 
    comment_id bigint not null
) engine=InnoDB character set = utf8;

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

create table question (
	id bigint auto_increment not null,
    text text not null,     
    primary key (id)
) engine=InnoDB character set = utf8;

create table questions (
	interview_id bigint not null, 
    question_id bigint not null
) engine=InnoDB character set = utf8;

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
create table answer (
	id bigint auto_increment not null,
    text mediumtext,
    correct bit default 0,
    primary key(id)
) engine=InnoDB character set = utf8;

create table answers (
	answer_id bigint not null, 
    question_id bigint not null
) engine=InnoDB character set = utf8;

create table user_answers (
	answer_id bigint not null, 
    user_id bigint not null,
    question_id bigint not null,
    id bigint not null auto_increment,
	primary key(id),
	interview_id bigint not null
) engine=InnoDB character set = utf8;

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
#~~~~~~~~~~~~~~~~~~~~~~~~ End creating tables ~~~~~~~~~~~~~~~~~~~~~~

insert into role(role) values('ROLE_ADMIN');
insert into role(role) values('ROLE_USER');

insert into category(title) values ('Опитування університету');
insert into category(title) values ('Опитування факультету');
insert into category(title) values ('Опитування викладачів');
insert into category(title) values ('Опитування студентів');