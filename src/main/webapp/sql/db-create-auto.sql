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
    `status`      TINYINT     NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 130
    DEFAULT CHARACTER SET = utf8mb3;


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
    ENGINE = InnoDB
    AUTO_INCREMENT = 5
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `activity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `activity`;

CREATE TABLE IF NOT EXISTS `activity`
(
    `id`          INT         NOT NULL AUTO_INCREMENT,
    `activity_en` VARCHAR(50) NOT NULL,
    `activity_ru` VARCHAR(50) NOT NULL,
    `kind_id`     INT         NOT NULL,
    PRIMARY KEY (`id`, `kind_id`),
    INDEX `fk_activity_kind_idx` (`kind_id` ASC) VISIBLE,
    CONSTRAINT `fk_activity_kind`
        FOREIGN KEY (`kind_id`)
            REFERENCES `kind` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 15
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `account_activity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `account_activity`;

CREATE TABLE IF NOT EXISTS `account_activity`
(
    `id`          INT NOT NULL AUTO_INCREMENT,
    `account_id`  INT NOT NULL,
    `activity_id` INT NOT NULL,
    PRIMARY KEY (`id`, `account_id`, `activity_id`),
    INDEX `fk_account_activity_activity_idx` (`activity_id` ASC) INVISIBLE,
    INDEX `fk_account_activity_account_idx` (`account_id` ASC) VISIBLE,
    CONSTRAINT `fk_account_activity_account`
        FOREIGN KEY (`account_id`)
            REFERENCES `account` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_account_activity_activity`
        FOREIGN KEY (`activity_id`)
            REFERENCES `activity` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 253
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `record`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `record`;

CREATE TABLE IF NOT EXISTS `record`
(
    `id`                  INT       NOT NULL AUTO_INCREMENT,
    `start`               TIMESTAMP NULL DEFAULT NULL,
    `end`                 TIMESTAMP NULL DEFAULT NULL,
    `account_activity_id` INT       NOT NULL,
    PRIMARY KEY (`id`, `account_activity_id`),
    INDEX `fk_record_account_activity_idx` (`account_activity_id` ASC) INVISIBLE,
    CONSTRAINT `fk_record_account_activity1`
        FOREIGN KEY (`account_activity_id`)
            REFERENCES `account_activity` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 572
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `request`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `request`;

CREATE TABLE IF NOT EXISTS `request`
(
    `id`          INT     NOT NULL AUTO_INCREMENT,
    `request`     TINYINT NOT NULL DEFAULT '0',
    `account_id`  INT     NOT NULL,
    `activity_id` INT     NOT NULL,
    PRIMARY KEY (`id`, `account_id`, `activity_id`),
    INDEX `fk_request_account_idx` (`account_id` ASC) VISIBLE,
    INDEX `fk_request_activity_idx` (`activity_id` ASC) INVISIBLE,
    CONSTRAINT `fk_request_account1`
        FOREIGN KEY (`account_id`)
            REFERENCES `account` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_request_activity1`
        FOREIGN KEY (`activity_id`)
            REFERENCES `activity` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 44
    DEFAULT CHARACTER SET = utf8mb3;

USE `timecounterdb`;

-- -----------------------------------------------------
-- procedure GetUserActivities
-- -----------------------------------------------------

USE `timecounterdb`;
DROP procedure IF EXISTS `GetUserActivities`;

DELIMITER $$
USE `timecounterdb`$$
CREATE
    DEFINER = `web`@`localhost` PROCEDURE `GetUserActivities`(IN accountId INT, IN lim INT, IN offs INT)
BEGIN
    select aa.id             account_activity_id,
           kind_en,
           kind_ru,
           activity_en,
           activity_ru,
           rec.start         start,
           ifnull(rec.id, 0) record_id
    from account_activity aa
             inner join activity at on aa.activity_id = at.id
             inner join kind ki on at.kind_id = ki.id
             left join record rec on (aa.id = rec.account_activity_id and end is null)
    where aa.account_id = accountId
    order by rec.start desc
    LIMIT lim OFFSET offs;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure GetUserActivitiesAndRequest
-- -----------------------------------------------------

USE `timecounterdb`;
DROP procedure IF EXISTS `GetUserActivitiesAndRequest`;

DELIMITER $$
USE `timecounterdb`$$
CREATE
    DEFINER = `web`@`localhost` PROCEDURE `GetUserActivitiesAndRequest`(IN accountId INT, IN lim INT, IN offs INT)
BEGIN
    SELECT at.id,
           kind_en,
           kind_ru,
           activity_en,
           activity_ru,
           ifnull(un.account_activity_id, 0) account_activity_id,
           ifnull(un.request_id, 0)          request_id
    FROM kind ki
             INNER JOIN activity at ON ki.id = at.kind_id
             LEFT JOIN (
        SELECT ac.id account_id, aa.activity_id activity_id, aa.id account_activity_id, rq.id request_id
        FROM account ac
                 INNER JOIN account_activity aa ON ac.id = aa.account_id
                 LEFT JOIN request rq ON
            (ac.id = rq.account_id AND aa.activity_id = rq.activity_id)
        WHERE ac.id = accountId
        UNION ALL
        SELECT ac.id account_id, rq.activity_id activity_id, aa.id account_activity_id, rq.id request_id
        FROM account ac
                 INNER JOIN request rq ON ac.id = rq.account_id
                 LEFT JOIN account_activity aa ON (ac.id = aa.account_id AND rq.activity_id = aa.activity_id)
        WHERE ac.id = accountId
          AND aa.id IS NULL
    ) un ON at.id = un.activity_id
    LIMIT lim OFFSET offs;
END$$

DELIMITER ;

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
