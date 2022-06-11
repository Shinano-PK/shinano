insert into token (token, valid_until, type) values ('token1', '2023-10-10', 'password');
insert into token (token, valid_until, type) values ('token2', '2023-10-11', 'resetpassword');
insert into token (token, valid_until, type) values ('token3', '2023-10-12', 'register');

insert into "user" (name, surname, birth_date, enabled, creation_date, email, username, password, role) values('a', 'b', '2020-10-10', 1, '2020-10-10', 'test@t.t', 'test', '$2a$12$SN8r4LJcg2SuqKCrNCJ7OOY2QXsrUJU64ScXA.Po9jB7zpO1eiM2i', 'ROLE_USER');
insert into "user" (name, surname, birth_date, enabled, creation_date, email, username, password, role) values('aaa', 'bbb', '2021-11-11', 0, '2020-10-10', 'test2@t.t', 'test2', '$2a$12$SN8r4LJcg2SuqKCrNCJ7OOY2QXsrUJU64ScXA.Po9jB7zpO1eiM2i', 'ROLE_USER');
