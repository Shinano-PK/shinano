insert into token (token, valid_until, type) values ('token1', '2023-10-10', 'password');
insert into token (token, valid_until, type) values ('token2', '2023-10-11', 'resetpassword');
insert into token (token, valid_until, type) values ('token3', '2023-10-12', 'register');

insert into "user" (enabled, creation_date, email, username, password, role) values(1, '2020-10-10', 'test@t.t', 'test', '$2a$10$0iGIDd2fi0DpRg0g1ZSD4ukXlQh7ETyCOf.3xSYC.8GWs3Ye41/0i', 'ROLE_USER');
insert into "user" (enabled, creation_date, email, username, password, role) values(0, '2020-10-10', 'test2@t.t', 'test2', '$2a$10$1R.fhro.unt71eaGWzrsdeGJZvMWFlMFCv4DwCxLYbh6QaNn/NEwi', 'ROLE_ADMIN');
