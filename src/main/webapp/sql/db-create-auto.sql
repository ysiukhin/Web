-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema timecounterdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `timecounterdb`;

-- -----------------------------------------------------
-- Schema timecounterdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `timecounterdb` DEFAULT CHARACTER SET utf8;
USE `timecounterdb`;

-- -----------------------------------------------------
-- Table `account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `account`;

CREATE TABLE IF NOT EXISTS `account`
(
    `id`          INT         NOT NULL AUTO_INCREMENT,
    `first_name`  VARCHAR(30) NOT NULL,
    `last_name`   VARCHAR(30) NOT NULL,
    `middle_name` VARCHAR(30) NULL     DEFAULT NULL,
    `email`       VARCHAR(50) NOT NULL,
    `md5`         VARCHAR(32) NOT NULL,
    `status`      TINYINT     NOT NULL DEFAULT 1,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kind`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kind`;

CREATE TABLE IF NOT EXISTS `kind`
(
    `id`      INT         NOT NULL AUTO_INCREMENT,
    `kind_en` VARCHAR(60) NOT NULL,
    `kind_ru` VARCHAR(60) NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `activity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `activity`;

CREATE TABLE IF NOT EXISTS `activity`
(
    `id`          INT         NOT NULL AUTO_INCREMENT,
    `activity_en` VARCHAR(50) NOT NULL,
    `activity_ru` VARCHAR(50) NOT NULL,
    `status`      TINYINT     NOT NULL DEFAULT 1,
    `kind_id`     INT         NOT NULL,
    PRIMARY KEY (`id`, `kind_id`),
    INDEX `fk_activity_kind_idx` (`kind_id` ASC) VISIBLE,
    CONSTRAINT `fk_activity_kind`
        FOREIGN KEY (`kind_id`)
            REFERENCES `kind` (`id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `account_activity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `account_activity`;

CREATE TABLE IF NOT EXISTS `account_activity`
(
    `id`          INT     NOT NULL AUTO_INCREMENT,
    `status`      TINYINT NOT NULL,
    `account_id`  INT     NOT NULL,
    `activity_id` INT     NOT NULL,
    PRIMARY KEY (`id`, `account_id`, `activity_id`),
    INDEX `fk_account_activity_activity_idx` (`activity_id` ASC) INVISIBLE,
    INDEX `fk_account_activity_account_idx` (`account_id` ASC) VISIBLE,
    CONSTRAINT `fk_account_activity_account`
        FOREIGN KEY (`account_id`)
            REFERENCES `account` (`id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT `fk_account_activity_activity`
        FOREIGN KEY (`activity_id`)
            REFERENCES `activity` (`id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `record`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `record`;

CREATE TABLE IF NOT EXISTS `record`
(
    `id`                  INT       NOT NULL AUTO_INCREMENT,
    `start`               TIMESTAMP NOT NULL,
    `end`                 TIMESTAMP NOT NULL,
    `account_activity_id` INT       NOT NULL,
    PRIMARY KEY (`id`, `account_activity_id`),
    INDEX `fk_record_account_activity_idx` (`account_activity_id` ASC) INVISIBLE,
    CONSTRAINT `fk_record_account_activity1`
        FOREIGN KEY (`account_activity_id`)
            REFERENCES `account_activity` (`id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `request`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `request`;

CREATE TABLE IF NOT EXISTS `request`
(
    `id`          INT     NOT NULL AUTO_INCREMENT,
    `request`     TINYINT NOT NULL DEFAULT 0,
    `status`      TINYINT NULL,
    `account_id`  INT     NOT NULL,
    `activity_id` INT     NOT NULL,
    PRIMARY KEY (`id`, `account_id`, `activity_id`),
    INDEX `fk_request_account_idx` (`account_id` ASC) VISIBLE,
    INDEX `fk_request_activity_idx` (`activity_id` ASC) INVISIBLE,
    CONSTRAINT `fk_request_account1`
        FOREIGN KEY (`account_id`)
            REFERENCES `account` (`id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT `fk_request_activity1`
        FOREIGN KEY (`activity_id`)
            REFERENCES `activity` (`id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
)
    ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
