-- MySQL Workbench Synchronization
-- Generated: 2019-06-04 11:52
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: u1z2s

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

ALTER TABLE `library`.`rental` 
DROP FOREIGN KEY `fk_Rental_Customer`;

ALTER TABLE `library`.`inventory` 
DROP FOREIGN KEY `fk_Inventory_Book1`,
DROP FOREIGN KEY `fk_Inventory_Rental1`;

ALTER TABLE `library`.`rental` 
DROP COLUMN `customer_id`,
ADD COLUMN `customer_id` SMALLINT(6) NOT NULL AFTER `rental_id`,
ADD INDEX `fk_Rental_Customer_idx` (`customer_id` ASC) VISIBLE,
DROP INDEX `fk_Rental_Customer_idx` ;
;

ALTER TABLE `library`.`inventory` 
DROP COLUMN `rental_id`,
DROP COLUMN `book_id`,
ADD COLUMN `book_id` SMALLINT(6) NOT NULL AFTER `inventory_id`,
ADD COLUMN `rental_id` SMALLINT(6) NOT NULL AFTER `book_id`,
ADD INDEX `fk_Inventory_Book1_idx` (`book_id` ASC) VISIBLE,
ADD INDEX `fk_Inventory_Rental1_idx` (`rental_id` ASC) VISIBLE,
DROP INDEX `fk_Inventory_Rental1_idx` ,
DROP INDEX `fk_Inventory_Book1_idx` ;
;

ALTER TABLE `library`.`rental` 
ADD CONSTRAINT `fk_Rental_Customer`
  FOREIGN KEY (`customer_id`)
  REFERENCES `library`.`customer` (`customer_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `library`.`inventory` 
ADD CONSTRAINT `fk_Inventory_Book1`
  FOREIGN KEY (`book_id`)
  REFERENCES `library`.`book` (`book_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_Inventory_Rental1`
  FOREIGN KEY (`rental_id`)
  REFERENCES `library`.`rental` (`rental_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
