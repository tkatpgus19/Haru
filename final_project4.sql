DROP DATABASE IF EXISTS ssafy_final_project;

CREATE DATABASE ssafy_final_project;
USE ssafy_final_project;

CREATE TABLE user
(user_id VARCHAR(20) PRIMARY KEY,
 user_password VARCHAR(20) NOT NULL,
 user_nickname VARCHAR(10) NOT NULL,
 user_email VARCHAR(20) NOT NULL,
 user_heart INTEGER DEFAULT 0,
 join_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 user_img VARCHAR(100));

CREATE TABLE diary
(diary_no INTEGER AUTO_INCREMENT PRIMARY KEY,
 user_id VARCHAR(20) NOT NULL,
 diary_emotion VARCHAR(10) NOT NULL,
 diary_content VARCHAR(300) NOT NULL,
 diary_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 diary_img VARCHAR(100),
 CONSTRAINT fk_diary_user FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE);

CREATE TABLE homework
(homework_no INTEGER AUTO_INCREMENT PRIMARY KEY,
 user_id VARCHAR(20) NOT NULL,
 homework_question VARCHAR(30) NOT NULL,
 homework_content VARCHAR(300) NOT NULL,
 homework_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_homework_user FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE);

CREATE TABLE item
(item_id INTEGER PRIMARY KEY,
 item_type VARCHAR(1) NOT NULL,
 item_source VARCHAR(30) NOT NULL,
 item_price INTEGER NOT NULL);

INSERT INTO item VALUES(0, 'B', 'background01', 10);
INSERT INTO item VALUES(1, 'B', 'background02', 20);
INSERT INTO item VALUES(2, 'B', 'background03', 40);
INSERT INTO item VALUES(3, 'B', 'background04', 60);
INSERT INTO item VALUES(4, 'B', 'background05', 1100);
INSERT INTO item VALUES(5, 'F', 'character01', 10);
INSERT INTO item VALUES(6, 'F', 'character02', 20);
INSERT INTO item VALUES(7, 'F', 'character03', 30);
INSERT INTO item VALUES(8, 'F', 'character04', 40);
INSERT INTO item VALUES(9, 'F', 'character05', 50);
INSERT INTO item VALUES(10, 'F', 'character06', 600);

CREATE TABLE inventory
(inventory_no INTEGER AUTO_INCREMENT PRIMARY KEY,
 user_id VARCHAR(20) NOT NULL,
 item_id INTEGER NOT NULL,
 CONSTRAINT fk_inventory_user FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE);


update user set user_heart = 100000 where user_id = 'q';
update user set join_date = '2023-07-01 13:17:34'
where user_id = 'q';
