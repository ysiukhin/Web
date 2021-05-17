-- MySQL Workbench Forward Engineering

SET
@OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET
@OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET
@OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema timecounterdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `timecounterdb`;

-- -----------------------------------------------------
-- Schema timecounterdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `timecounterdb` DEFAULT CHARACTER SET utf8;
USE
`timecounterdb` ;

-- -----------------------------------------------------
-- Table `role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `role`;

CREATE TABLE IF NOT EXISTS `role`
(
    `id`
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `role_ru`
    VARCHAR
(
    60
) NOT NULL,
    `role_en` VARCHAR
(
    60
) NOT NULL,
    PRIMARY KEY
(
    `id`
))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `account`;

CREATE TABLE IF NOT EXISTS `account`
(
    `id`
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `first_name`
    VARCHAR
(
    30
) NOT NULL,
    `last_name` VARCHAR
(
    30
) NOT NULL,
    `middle_name` VARCHAR
(
    30
) NULL DEFAULT NULL,
    `email` VARCHAR
(
    50
) NOT NULL,
    `login` VARCHAR
(
    45
) NOT NULL,
    `md5` VARCHAR
(
    32
) NOT NULL,
    `status` TINYINT NOT NULL DEFAULT 1,
    `role_id` INT NOT NULL,
    PRIMARY KEY
(
    `id`,
    `role_id`
),
    INDEX `fk_account_role_idx`
(
    `role_id` ASC
) VISIBLE,
    CONSTRAINT `fk_account_role`
    FOREIGN KEY
(
    `role_id`
)
    REFERENCES `role`
(
    `id`
)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project`;

CREATE TABLE IF NOT EXISTS `project`
(
    `id`
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `project_name`
    VARCHAR
(
    50
) NOT NULL,
    `project_desc` TEXT NOT NULL,
    `status` TINYINT NOT NULL DEFAULT 1,
    PRIMARY KEY
(
    `id`
))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_executor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_executor`;

CREATE TABLE IF NOT EXISTS `project_executor`
(
    `id`
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `status`
    TINYINT
    NOT
    NULL,
    `account_id`
    INT
    NOT
    NULL,
    `project_id`
    INT
    NOT
    NULL,
    PRIMARY
    KEY
(
    `id`,
    `account_id`,
    `project_id`
),
    INDEX `fk_project_executor_account_idx`
(
    `account_id` ASC
) VISIBLE,
    INDEX `fk_project_executor_project_idx`
(
    `project_id` ASC
) INVISIBLE,
    CONSTRAINT `fk_project_executor_account`
    FOREIGN KEY
(
    `account_id`
)
    REFERENCES `account`
(
    `id`
)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
    CONSTRAINT `fk_project_executor_project`
    FOREIGN KEY
(
    `project_id`
)
    REFERENCES `project`
(
    `id`
)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `task`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `task`;

CREATE TABLE IF NOT EXISTS `task`
(
    `id`
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `desc_short`
    VARCHAR
(
    100
) NOT NULL,
    `desc_full` TEXT NOT NULL,
    `status` TINYINT NOT NULL DEFAULT 1,
    `project_id` INT NOT NULL,
    `project_executor_id` INT NOT NULL,
    PRIMARY KEY
(
    `id`,
    `project_id`,
    `project_executor_id`
),
    INDEX `fk_task_project_executor_idx`
(
    `project_executor_id` ASC
) VISIBLE,
    INDEX `fk_task_project_idx`
(
    `project_id` ASC
) VISIBLE,
    CONSTRAINT `fk_task_project_executor1`
    FOREIGN KEY
(
    `project_executor_id`
)
    REFERENCES `project_executor`
(
    `id`
)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
    CONSTRAINT `fk_task_project1`
    FOREIGN KEY
(
    `project_id`
)
    REFERENCES `project`
(
    `id`
)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `record`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `record`;

CREATE TABLE IF NOT EXISTS `record`
(
    `id`
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `project_executor_id`
    INT
    NOT
    NULL,
    `task_id`
    INT
    NOT
    NULL,
    `start`
    TIMESTAMP
    NOT
    NULL,
    `end`
    TIMESTAMP
    NOT
    NULL,
    PRIMARY
    KEY
(
    `id`,
    `project_executor_id`,
    `task_id`
),
    INDEX `fk_record_project_executor_idx`
(
    `project_executor_id` ASC
) VISIBLE,
    INDEX `fk_record_task_idx`
(
    `task_id` ASC
) VISIBLE,
    CONSTRAINT `fk_record_project_executor`
    FOREIGN KEY
(
    `project_executor_id`
)
    REFERENCES `project_executor`
(
    `id`
)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
    CONSTRAINT `fk_record_task`
    FOREIGN KEY
(
    `task_id`
)
    REFERENCES `task`
(
    `id`
)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `permission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `permission`;

CREATE TABLE IF NOT EXISTS `permission`
(
    `id`
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `permission_ru`
    VARCHAR
(
    90
) NOT NULL,
    `permission_en` VARCHAR
(
    90
) NOT NULL,
    PRIMARY KEY
(
    `id`
))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `role_permission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE IF NOT EXISTS `role_permission`
(
    `role_id`
    INT
    NOT
    NULL,
    `permission_id`
    INT
    NOT
    NULL,
    PRIMARY
    KEY
(
    `role_id`,
    `permission_id`
),
    INDEX `fk_role_permission_permission_idx`
(
    `permission_id` ASC
) VISIBLE,
    INDEX `fk_role_permission_role_idx`
(
    `role_id` ASC
) VISIBLE,
    CONSTRAINT `fk_role_has_permission_role1`
    FOREIGN KEY
(
    `role_id`
)
    REFERENCES `role`
(
    `id`
)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
    CONSTRAINT `fk_role_has_permission_permission1`
    FOREIGN KEY
(
    `permission_id`
)
    REFERENCES `permission`
(
    `id`
)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


SET
SQL_MODE=@OLD_SQL_MODE;
SET
FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET
UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
