-- MySQL Workbench Synchronization
-- Generated: 2019-06-04 12:09
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: u1z2s

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `Library` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE IF NOT EXISTS `Library`.`book` (
  `book_id` SMALLINT(6) NOT NULL AUTO_INCREMENT,
  `author` VARCHAR(45) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`book_id`),
  UNIQUE INDEX `book_id_UNIQUE` (`book_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `Library`.`customer` (
  `customer_id` SMALLINT(6) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `active` TINYINT(4) NULL DEFAULT NULL,
  `signup_date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE INDEX `customer_id_UNIQUE` (`customer_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `Library`.`rental` (
  `rental_id` SMALLINT(6) NOT NULL AUTO_INCREMENT,
  `customer_id` SMALLINT(6) NOT NULL,
  `inventory_id` SMALLINT(6) NOT NULL,
  `rental_date` DATETIME NOT NULL,
  `return_date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`rental_id`),
  UNIQUE INDEX `rental_id_UNIQUE` (`rental_id` ASC) VISIBLE,
  INDEX `fk_Rental_Customer_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `fk_Rental_Customer`
    FOREIGN KEY (`customer_id`)
    REFERENCES `Library`.`customer` (`customer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `Library`.`inventory` (
  `inventory_id` SMALLINT(6) NOT NULL AUTO_INCREMENT,
  `book_id` SMALLINT(6) NOT NULL,
  `rental_id` SMALLINT(6) NOT NULL,
  PRIMARY KEY (`inventory_id`),
  UNIQUE INDEX `inventory_id_UNIQUE` (`inventory_id` ASC) VISIBLE,
  INDEX `fk_Inventory_Book1_idx` (`book_id` ASC) VISIBLE,
  INDEX `fk_Inventory_Rental1_idx` (`rental_id` ASC) VISIBLE,
  CONSTRAINT `fk_Inventory_Book1`
    FOREIGN KEY (`book_id`)
    REFERENCES `Library`.`book` (`book_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Inventory_Rental1`
    FOREIGN KEY (`rental_id`)
    REFERENCES `Library`.`rental` (`rental_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
