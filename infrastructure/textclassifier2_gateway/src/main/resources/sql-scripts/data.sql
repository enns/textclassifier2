INSERT INTO app_role (id, role_name, description) VALUES (1, 'STANDARD_USER', 'Standard User - Has no admin rights');
INSERT INTO app_role (id, role_name, description) VALUES (2, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks');

-- USER
-- non-encrypted password: jwtpass
INSERT INTO app_user (id, first_name, last_name, password, username) VALUES (1, 'John', 'Doe', '{bcrypt}$2a$10$Ua3JncGS.u/R5Bh5zVynGexJ.mp5vIlVvnV5D2VSUyRZl6J19xl0a', 'john.doe');
INSERT INTO app_user (id, first_name, last_name, password, username) VALUES (2, 'Admin', 'Admin', '{bcrypt}$2a$10$dJM6PDkEsNz8DlPiE.f0yOjMov5Kfvk44uPfiiuNDRTRy26AN307K', 'admin.admin');


INSERT INTO user_role(user_id, role_id) VALUES(1,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,2);

-- Populate random city table

INSERT INTO country(id, name) VALUES (1, 'USA');

INSERT INTO random_city(id, name, COUNTRY_ID) VALUES (1, 'Bamako', 1);
INSERT INTO random_city(id, name, COUNTRY_ID) VALUES (2, 'Nonkon', 1);
INSERT INTO random_city(id, name, COUNTRY_ID) VALUES (3, 'Houston', 1);
INSERT INTO random_city(id, name, COUNTRY_ID) VALUES (4, 'Toronto', 1);
INSERT INTO random_city(id, name, COUNTRY_ID) VALUES (5, 'New York City', 1);
INSERT INTO random_city(id, name, COUNTRY_ID) VALUES (6, 'Mopti', 1);
INSERT INTO random_city(id, name, COUNTRY_ID) VALUES (7, 'Koulikoro', 1);
INSERT INTO random_city(id, name, COUNTRY_ID) VALUES (8, 'Moscow', 1);
