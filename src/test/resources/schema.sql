DROP SCHEMA IF EXISTS o2021 CASCADE;
CREATE SCHEMA o2021;

CREATE TABLE o2021.privilege (
    id bigint NOT NULL,
    code character varying(50) NOT NULL,
    enabled boolean NOT NULL,
    i18n character varying(50) NOT NULL,
    "order" integer,
    idprivilegegroup bigint
);

CREATE TABLE o2021.privilege_group (
    id bigint NOT NULL,
    i18n character varying(50) NOT NULL,
    "order" integer
);

CREATE SEQUENCE o2021.privilege_group_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE o2021.role (
    id bigint NOT NULL,
    enabled boolean NOT NULL,
    name character varying(255) NOT NULL
);

CREATE TABLE o2021.role_privilege (
    idrole bigint NOT NULL,
    idprivilege bigint NOT NULL
);

CREATE TABLE o2021.usuario (
    id bigint NOT NULL,
    deleted boolean NOT NULL,
    email character varying(255) NOT NULL,
    enabled boolean NOT NULL,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    idrole bigint NOT NULL
);

insert into o2021.role values (1, true, 'user');
insert into o2021.role values (2, true, 'admin');
insert into o2021.role values (3, false, 'user2false');
insert into o2021.role values (4, false, 'admin2false');

Insert into o2021.usuario values (1, false, 'femail@gmail.com', true, '1117','nora',1);
Insert into o2021.usuario values (2, false, 'bemail@gmail.com', false, '1117','admin',4);
Insert into o2021.usuario values (3, true, 'demail@gmail.com', true, '1117','user',3);
Insert into o2021.usuario values (4, false, 'cemail@gmail.com', false, '1117','usernora',2);
Insert into o2021.usuario values (5, false, 'aemail@gmail.com', true, '1117','adminuser',2);

ALTER TABLE ONLY o2021.privilege_group
    ADD CONSTRAINT privilege_group_pkey PRIMARY KEY (id);

ALTER TABLE ONLY o2021.privilege
    ADD CONSTRAINT privilege_pkey PRIMARY KEY (id);

ALTER TABLE ONLY o2021.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);

ALTER TABLE ONLY o2021.role_privilege
    ADD CONSTRAINT role_privilege_pkey PRIMARY KEY (idprivilege, idrole);

ALTER TABLE ONLY o2021.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);

ALTER TABLE ONLY o2021.role_privilege
    ADD CONSTRAINT fk61jt7fln25yxvojve2vb5b6ln FOREIGN KEY (idprivilege) REFERENCES o2021.privilege(id);

ALTER TABLE ONLY o2021.usuario
    ADD CONSTRAINT fkcb0sxedhyibys6bcaxx9qjvcd FOREIGN KEY (idrole) REFERENCES o2021.role(id);

ALTER TABLE ONLY o2021.privilege
    ADD CONSTRAINT fkk0y0jkrwhgus5tb9ji5i3iqop FOREIGN KEY (idprivilegegroup) REFERENCES o2021.privilege_group(id);

ALTER TABLE ONLY o2021.role_privilege
    ADD CONSTRAINT fkofqhorxf4p15u0unw59ovat0h FOREIGN KEY (idrole) REFERENCES o2021.role(id);