INSERT INTO user_details (user_details_id, name, surname, birth_date) values (1, 'Mario', 'Rossi', '1989-10-10');
INSERT INTO user_details (user_details_id, name, surname, birth_date) values (2, 'Elisa', 'Bianchi', '1995-10-10');

INSERT INTO user (user_id, username, password, user_details_id) values (1, 'mariorossi@yahoo.it', 'pisolo', 1);
INSERT INTO user (user_id, username, password, user_details_id) values (2, 'elisabianchi@gmail.com', 'cavallo', 2);