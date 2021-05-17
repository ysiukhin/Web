DROP
DATABASE IF EXISTS timecounterdb;
CREATE
DATABASE timecounterdb;
USE
timecounterdb;

DROP TABLE IF EXISTS person;
CREATE TABLE person
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    first_name   VARCHAR(20) NOT NULL,
    last_name    VARCHAR(20) NOT NULL,
    middle_name  VARCHAR(20),
    email        VARCHAR(50) NOT NULL,
    registration DATE        NOT NULL
);

DROP TABLE IF EXISTS project;
CREATE TABLE project
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(50) NOT NULL,
    project_desc TEXT,
    creator_id   INT         NOT NULL
);

DROP TABLE IF EXISTS task;
create table task
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    desc_short VARCHAR(100),
    desc_full  TEXT
);