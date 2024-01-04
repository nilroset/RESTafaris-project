INSERT INTO category (id, name) VALUES ('e4014b24-7432-11ee-b962-0242ac120002', 'matematiques');
INSERT INTO category (id, name) VALUES ('e4014ed0-7432-11ee-b962-0242ac120002', 'economia');
INSERT INTO category (id, name) VALUES ('e4014ed0-7432-11ee-b962-0242ac120003', 'informatica');

INSERT INTO language (id, name) VALUES ('e4014ed0-7432-11ee-b962-0242ac120003', 'angles');
INSERT INTO language (id, name) VALUES ('e4014ed0-7432-11ee-b962-0242ac120004', 'castella');

INSERT INTO user (id, username, name, second_name, third_Name, email, gender) VALUES ('6074b4c2-717f-11ee-b962-0242ac120002', 'nroset', 'Nil', 'Roset', 'Lopez', 'nroset@edu.tecnocampus.cat', 'MALE');
INSERT INTO user (id, username, name, second_name, third_Name, email, gender) VALUES ('6074bb02-717f-11ee-b962-0242ac120002', 'jcayla', 'Jordi', 'Cayla', 'Bayona', 'jcayla@edu.tecnocampus.cat', 'male');
INSERT INTO user (id, username, name, second_name, third_Name, email, gender) VALUES ('6074bc56-717f-11ee-b962-0242ac120002', 'gbusquets', 'Gisela', 'Busquets', 'Luengo', 'gbusquets@edu.tecnocampus.cat', 'female');
INSERT INTO user (id, username, name, second_name, third_Name, email, gender) VALUES ('6074bd82-717f-11ee-b962-0242ac120002', 'alahora', 'Alba', 'Lahora', 'Martinez', 'alahora@edu.tecnocampus.cat', 'female');
INSERT INTO user (id, username, name, second_name, third_Name, email, gender) VALUES ('6074bb02-717f-11ee-b962-0242ac120003', 'oarnedo', 'Oscar', 'Arnedo', 'Riccobene', 'oarnedo@edu.tecnocampus.cat', 'male');
INSERT INTO user (id, username, name, second_name, third_Name, email, gender) VALUES ('6074bfbc-717f-11ee-b962-0242ac120004', 'lacosta', 'Luis', 'Acosta', 'Sicilia', 'lacosta@edu.tecnocampus.cat', 'male');
INSERT INTO user (id, username, name, second_name, third_Name, email, gender) VALUES ('6074c0ca-717f-11ee-b962-0242ac120005', 'aarenas', 'Aleix', 'Arenas', 'Guillen', 'aarenas@edu.tecnocampus.cat', 'male');
INSERT INTO user (id, username, name, second_name, third_Name, email, gender) VALUES ('6074c1d8-717f-11ee-b962-0242ac120002', 'lmunoz', 'Laura', 'Munoz', 'Cabello', 'lmunoz@edu.tecnocampus.cat', 'female');
INSERT INTO user (id, username, name, second_name, third_Name, email, gender) VALUES ('6074c2e6-717f-11ee-b962-0242ac120002', 'ccosta', 'Carla', 'Costa', 'Mas', 'ccosta@edu.tecnocampus.cat', 'female');
INSERT INTO user (id, username, name, second_name, third_Name, email, gender) VALUES ('6074c606-717f-11ee-b962-0242ac120002', 'cbusquets', 'Carola', 'Busquets', 'Luengo', 'cbusquets@edu.tecnocampus.cat', 'female');


INSERT INTO course (id, title, description, publication, last_update, image_url, current_price, availability, category_id, creator_id, language_id) VALUES ('72919b8e-6c4d-11ee-b962-0242ac120002', 'Xarxes i serveis', 'internet', '2023-10-2', '2023-10-2', 'hrth', 80, true,'e4014b24-7432-11ee-b962-0242ac120002','6074bc56-717f-11ee-b962-0242ac120002','e4014ed0-7432-11ee-b962-0242ac120003');
INSERT INTO course (id, title, description, publication, last_update, image_url, current_price, availability, category_id, creator_id, language_id) VALUES ('72919e18-6c4d-11ee-b962-0242ac120002','Programacio orientada a objectes', 'programacio amb java', '2023-9-1', '2023-9-24', 'hrth', 100, true, 'e4014b24-7432-11ee-b962-0242ac120002', '6074bc56-717f-11ee-b962-0242ac120002','e4014ed0-7432-11ee-b962-0242ac120003');
INSERT INTO course (id, title, description, publication, last_update, image_url, current_price, availability, category_id, creator_id, language_id) VALUES ('72919f4e-6c4d-11ee-b962-0242ac120002','Aplicacions internet', 'aplicacio web', '2023-9-1', '2023-9-1', 'hrth', 90, true, 'e4014ed0-7432-11ee-b962-0242ac120002', '6074bfbc-717f-11ee-b962-0242ac120004','e4014ed0-7432-11ee-b962-0242ac120003');
INSERT INTO course (id, title, description, publication, last_update, image_url, current_price, availability, category_id, creator_id, language_id) VALUES ('7291a930-6c4d-11ee-b962-0242ac120002','Bases de dades', 'com emmagatzemar i gestionar les dades', current_date, current_date, 'hrth', 80, true, 'e4014ed0-7432-11ee-b962-0242ac120002', '6074b4c2-717f-11ee-b962-0242ac120002', 'e4014ed0-7432-11ee-b962-0242ac120003');
INSERT INTO course (id, title, description, publication, last_update, image_url, current_price, availability, category_id, creator_id, language_id) VALUES ('7291ac3c-6c4d-11ee-b962-0242ac120002','Sistemes informacio', 'les dades en les organitzacions', current_date, current_date, 'hrth', 80, true, 'e4014ed0-7432-11ee-b962-0242ac120002', '6074c0ca-717f-11ee-b962-0242ac120005', 'e4014ed0-7432-11ee-b962-0242ac120003');


INSERT INTO role(name) VALUES ('ROLE_STUDENT');
INSERT INTO role(name) VALUES ('ROLE_TEACHER');
INSERT INTO role(name) VALUES ('ROLE_ADMIN');

INSERT INTO user_security (email, username, password) VALUES ('nroset@edu.tecnocampus.cat', 'nroset','$2a$10$fVKfcc47q6lrNbeXangjYeY000dmjdjkdBxEOilqhapuTO5ZH0co2');
INSERT INTO user_security (email, username, password) VALUES ('jcayla@edu.tecnocampus.cat', 'jcayla','$2a$10$fVKfcc47q6lrNbeXangjYeY000dmjdjkdBxEOilqhapuTO5ZH0co2');
INSERT INTO user_security (email, username, password) VALUES ('gbusquets@edu.tecnocampus.cat', 'gbusquets','$2a$10$fVKfcc47q6lrNbeXangjYeY000dmjdjkdBxEOilqhapuTO5ZH0co2');
INSERT INTO user_security (email, username, password) VALUES ('aarenas@edu.tecnocampus.cat', 'aarenas','$2a$10$fVKfcc47q6lrNbeXangjYeY000dmjdjkdBxEOilqhapuTO5ZH0co2');
INSERT INTO user_security (email, username, password) VALUES ('ccosta@edu.tecnocampus.cat', 'ccosta','$2a$10$fVKfcc47q6lrNbeXangjYeY000dmjdjkdBxEOilqhapuTO5ZH0co2');

INSERT INTO user_roles (USER_ID, ROLE_ID) VALUES (1, 2);
INSERT INTO user_roles (USER_ID, ROLE_ID) VALUES (2, 1);
INSERT INTO user_roles (USER_ID, ROLE_ID) VALUES (3, 3);
INSERT INTO user_roles (USER_ID, ROLE_ID) VALUES (4, 2);
INSERT INTO user_roles (USER_ID, ROLE_ID) VALUES (5, 1);

INSERT INTO lesson (id, title, description, duration, video_url, course_id, orders) VALUES ('7291d5a4-6c4d-11ee-b962-0242ac120002', 'Introduccio', 'Introduccio a les xarxes', 10, 'https://www.youtube.com/watch?v=9GqL1k0ZJWQ', '72919b8e-6c4d-11ee-b962-0242ac120002', 1);
INSERT INTO lesson (id, title, description, duration, video_url, course_id, orders) VALUES ('7291d6b2-6c4d-11ee-b962-0242ac120003', 'Internet', 'Fonaments dinternet', 7, 'https://www.youtube.com/watch?v=9GqL1k0ZJWQ', '72919b8e-6c4d-11ee-b962-0242ac120002', 2);
INSERT INTO lesson (id, title, description, duration, video_url, course_id, orders) VALUES ('7291d7c0-6c4d-11ee-b962-0242ac120004', 'Internet2', 'Funcionament dinternet', 8, 'https://www.youtube.com/watch?v=9GqL1k0ZJWQ', '72919b8e-6c4d-11ee-b962-0242ac120002', 3);
INSERT INTO lesson (id, title, description, duration, video_url, course_id, orders) VALUES ('7291d8ce-6c4d-11ee-b962-0242ac120005', 'Internet3', 'Encaminament dinternet', 5, 'https://www.youtube.com/watch?v=9GqL1k0ZJWQ', '72919b8e-6c4d-11ee-b962-0242ac120002', 4);
INSERT INTO lesson (id, title, description, duration, video_url, course_id, orders) VALUES ('7291d9dc-6c4d-11ee-b962-0242ac120006', 'Seguretat', 'QoS i seguretat', 6, 'https://www.youtube.com/watch?v=9GqL1k0ZJWQ', '72919b8e-6c4d-11ee-b962-0242ac120002', 5);

INSERT INTO lesson (id, title, description, duration, video_url, course_id, orders) VALUES ('7291d5a4-6c4d-11ee-b962-0242ac120007', 'Introduccio', 'Introduccio a la programacio', 2.1, 'https://www.youtube.com/watch?v=9GqL1k0ZJWQ', '72919e18-6c4d-11ee-b962-0242ac120002', 1);
INSERT INTO lesson (id, title, description, duration, video_url, course_id, orders) VALUES ('7291d6b2-6c4d-11ee-b962-0242ac120008', 'Programacio', 'Fonaments de la programacio', 3.2, 'https://www.youtube.com/watch?v=9GqL1k0ZJWQ', '72919e18-6c4d-11ee-b962-0242ac120002', 2);

INSERT INTO lesson (id, title, description, duration, video_url, course_id, orders) VALUES ('7291d5a4-6c4d-11ee-b962-0242ac120009', 'Introduccio', 'Introduccio a les bases de dades', 2, 'https://www.youtube.com/watch?v=9GqL1k0ZJWQ', '7291a930-6c4d-11ee-b962-0242ac120002', 1);
INSERT INTO lesson (id, title, description, duration, video_url, course_id, orders) VALUES ('7291d6b2-6c4d-11ee-b962-0242ac120010', 'Bases de dades', 'Fonaments de les bases de dades', 5, 'https://www.youtube.com/watch?v=9GqL1k0ZJWQ', '7291a930-6c4d-11ee-b962-0242ac120002', 2);
INSERT INTO lesson (id, title, description, duration, video_url, course_id, orders) VALUES ('7291d7c0-6c4d-11ee-b962-0242ac120011', 'SGBD', 'Sistemes gestors de bases de dades', 4, 'https://www.youtube.com/watch?v=9GqL1k0ZJWQ', '7291a930-6c4d-11ee-b962-0242ac120002', 3);

INSERT INTO lesson (id, title, description, duration, video_url, course_id, orders) VALUES ('3491d5a4-6c4d-11ee-b962-0242ac120012', 'Whatever', 'whatever', 2.1, 'https://www.youtube.com/watch?v=9GqL1k0ZJWQ', '7291ac3c-6c4d-11ee-b962-0242ac120002', 1);