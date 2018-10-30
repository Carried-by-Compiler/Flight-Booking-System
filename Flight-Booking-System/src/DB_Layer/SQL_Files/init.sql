/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  John Rey Juele
 * Created: 29-Oct-2018
 */

/* CREATE TABLES */
CREATE TABLE airlines (
    `id`            INT NOT NULL AUTO_INCREMENT, 
    `name`          VARCHAR(255) NOT NULL,
    `password`      VARCHAR(255),

    PRIMARY KEY (`id`)
);

CREATE TABLE customers (
    `id`            INT NOT NULL AUTO_INCREMENT,
    `firstname`     VARCHAR(50) NOT NULL,
    `surname`       VARCHAR(50) NOT NULL,
    `password`      VARCHAR(255),

    PRIMARY KEY (`id`)
);

CREATE TABLE flights (
    `id`            INT NOT NULL AUTO_INCREMENT,
    `departure`     VARCHAR(255) NOT NULL,
    `arrival`       VARCHAR(255) NOT NULL,
    `depart_time`   DATETIME NOT NULL DEFAULT NOW(),
    `arrival_time`  DATETIME NOT NULL DEFAULT NOW(),

    PRIMARY KEY(`id`)
);

/*
Adding sample data. Please remove when done testing
*/

INSERT INTO airlines
VALUES(DEFAULT, "RYANAIR", PASSWORD("ryanair"));

INSERT INTO airlines
VALUES(DEFAULT, "AERLINGUS", PASSWORD("aerlingus"));

INSERT INTO customers
VALUES(DEFAULT, "JOHNREY", "JUELE", PASSWORD("John1234"));

INSERT INTO customers
VALUES(DEFAULT, "EOGHAN", "CASEY", PASSWORD("Eoghn1234"));

INSERT INTO flights
VALUES(DEFAULT, "DUBLIN", "HEATHROW", "2018-12-10 17:00:00", "2018-12-10 18:25:00");