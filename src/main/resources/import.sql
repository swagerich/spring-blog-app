INSERT INTO users(nombre,apellido,email,password) VALUES ('ivan','cuevas','ivan@gmail.com','$2a$10$xGw4tjFfKfllkCqy.bYUVu.RH0G9L7nim3RDW64dRMCcx3bNXYMeC');
INSERT INTO users(nombre,apellido,email,password) VALUES ('erich','hc','erich@gmail.com','$2a$10$xGw4tjFfKfllkCqy.bYUVu.RH0G9L7nim3RDW64dRMCcx3bNXYMeC');

INSERT INTO roles(id,authority) VALUES(1,'ROLE_ADMIN');
INSERT INTO roles(id,authority) VALUES(2,'ROLE_USER');

INSERT INTO users_roles(user_id,role_id) VALUES (1,2);
INSERT INTO users_roles(user_id,role_id) VALUES (2,1);
