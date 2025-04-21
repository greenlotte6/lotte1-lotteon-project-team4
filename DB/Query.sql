-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema LotteON
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema LotteON
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `LotteON` DEFAULT CHARACTER SET utf8 ;
USE `LotteON` ;

-- -----------------------------------------------------
-- Table `LotteON`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`Users` (
  `t_u_num` INT NOT NULL AUTO_INCREMENT,
  `uid` VARCHAR(12) NOT NULL,
  `uname` VARCHAR(10) NOT NULL,
  `password` VARCHAR(12) NOT NULL,
  `gender` VARCHAR(1) NOT NULL,
  `grade` VARCHAR(10) NOT NULL,
  `point` INT NOT NULL DEFAULT 0,
  `email` VARCHAR(50) NULL,
  `hp` CHAR(13) NOT NULL,
  `zip` VARCHAR(5) NOT NULL,
  `addr1` TEXT NOT NULL,
  `addr2` TEXT NOT NULL,
  `role` VARCHAR(10) NOT NULL,
  `u_created_at` DATETIME NOT NULL,
  `status` VARCHAR(2) NOT NULL,
  `u_last_login` DATETIME NOT NULL,
  `mgmt` VARCHAR(5) NOT NULL,
  `other` TEXT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `hp_UNIQUE` (`hp` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`category` (
  `cate_id` INT NOT NULL AUTO_INCREMENT,
  `cate_name` VARCHAR(50) NULL,
  `parent_id` INT NULL,
  PRIMARY KEY (`cate_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`cart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`cart` (
  `cart_id` INT NOT NULL AUTO_INCREMENT,
  `Users_uid` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`cart_id`),
  INDEX `fk_cart_Users1_idx` (`Users_uid` ASC) VISIBLE,
  CONSTRAINT `fk_cart_Users1`
    FOREIGN KEY (`Users_uid`)
    REFERENCES `LotteON`.`Users` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`cart_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`cart_item` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `cart_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`item_id`),
  INDEX `fk_cart_item_cart1_idx` (`cart_id` ASC) VISIBLE,
  CONSTRAINT `fk_cart_item_cart1`
    FOREIGN KEY (`cart_id`)
    REFERENCES `LotteON`.`cart` (`cart_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`Products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`Products` (
  `pid` INT NOT NULL AUTO_INCREMENT,
  `img_file_1` VARCHAR(255) NOT NULL,
  `img_file_2` VARCHAR(255) NULL,
  `img_file_3` VARCHAR(255) NULL,
  `detaile_file_1` VARCHAR(255) NOT NULL,
  `pcode` INT NOT NULL,
  `pname` VARCHAR(50) NOT NULL,
  `description` TEXT NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `discount` VARCHAR(4) NOT NULL DEFAULT '0%',
  `stock` INT NOT NULL,
  `company` VARCHAR(45) NOT NULL,
  `hits` VARCHAR(10) NOT NULL DEFAULT 0,
  `mgmt` VARCHAR(5) NOT NULL,
  `category_id` INT NOT NULL,
  `brand` VARCHAR(30) NOT NULL,
  `p_created_at` DATETIME NOT NULL,
  `p_updates_at` DATETIME NOT NULL,
  `maker` VARCHAR(20) NOT NULL,
  `delivery_free` INT NOT NULL DEFAULT 0,
  `category_cate_id` INT NOT NULL,
  `poiont_rate` FLOAT NOT NULL,
  `cart_item_item_id` INT NOT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE INDEX `category_id_UNIQUE` (`category_id` ASC) VISIBLE,
  INDEX `fk_Products_category1_idx` (`category_cate_id` ASC) VISIBLE,
  INDEX `fk_Products_cart_item1_idx` (`cart_item_item_id` ASC) VISIBLE,
  CONSTRAINT `fk_Products_category1`
    FOREIGN KEY (`category_cate_id`)
    REFERENCES `LotteON`.`category` (`cate_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Products_cart_item1`
    FOREIGN KEY (`cart_item_item_id`)
    REFERENCES `LotteON`.`cart_item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`product_option`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`product_option` (
  `option_id` INT NOT NULL AUTO_INCREMENT,
  `Products_pid` INT NOT NULL,
  `option_name` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`option_id`),
  INDEX `fk_product_option_Products2_idx` (`Products_pid` ASC) VISIBLE,
  UNIQUE INDEX `Products_pid_UNIQUE` (`Products_pid` ASC) VISIBLE,
  CONSTRAINT `fk_product_option_Products2`
    FOREIGN KEY (`Products_pid`)
    REFERENCES `LotteON`.`Products` (`pid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`banner`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`banner` (
  `banner_id` INT NOT NULL AUTO_INCREMENT,
  `banner_name` VARCHAR(45) NOT NULL,
  `size` VARCHAR(15) NOT NULL,
  `back_color` VARCHAR(10) NOT NULL,
  `banner_img_url` VARCHAR(255) NULL,
  `banner_link` VARCHAR(255) NULL,
  `position` VARCHAR(20) NOT NULL,
  `active` VARCHAR(10) NOT NULL,
  `array` INT NOT NULL,
  `start_day` DATE NOT NULL,
  `close_day` DATE NULL,
  `start_at` TIME NOT NULL,
  `close_at` TIME NULL,
  `mgmt` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`banner_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`terms`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`terms` (
  `terms_id` INT NOT NULL,
  `type` VARCHAR(10) NOT NULL,
  `content` TEXT NOT NULL,
  PRIMARY KEY (`terms_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`admin` (
  `aid` VARCHAR(12) NOT NULL,
  `password` VARCHAR(12) NULL,
  `company` VARCHAR(45) NOT NULL,
  `ceo` VARCHAR(10) NOT NULL,
  `biz_num` VARCHAR(12) NOT NULL,
  `osn` VARCHAR(30) NOT NULL,
  `number` VARCHAR(20) NOT NULL,
  `fax` VARCHAR(15) NOT NULL,
  `addr` TEXT NOT NULL,
  `role` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`aid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`order` (
  `oid` INT NOT NULL,
  `Users_uid` VARCHAR(12) NOT NULL,
  `order_total` INT NOT NULL,
  `payment` VARCHAR(5) NOT NULL,
  `order_status` VARCHAR(4) NOT NULL,
  `order_date` DATETIME NOT NULL,
  `shipping_status` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`oid`),
  INDEX `fk_order_Users1_idx` (`Users_uid` ASC) VISIBLE,
  CONSTRAINT `fk_order_Users1`
    FOREIGN KEY (`Users_uid`)
    REFERENCES `LotteON`.`Users` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`version`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`version` (
  `version_id` INT NOT NULL AUTO_INCREMENT,
  `site_version` VARCHAR(20) NOT NULL,
  `writer` VARCHAR(30) NOT NULL,
  `regist_at` DATETIME NOT NULL,
  `change_history` TEXT NOT NULL,
  PRIMARY KEY (`version_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`site`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`site` (
  `site_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(10) NOT NULL,
  `sub_title` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`site_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`logo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`logo` (
  `logo_id` INT NOT NULL AUTO_INCREMENT,
  `header_file` VARCHAR(255) NOT NULL,
  `footer_file` VARCHAR(255) NULL,
  `favicon` VARCHAR(255) NULL,
  PRIMARY KEY (`logo_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`company_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`company_info` (
  `company_id` INT NOT NULL AUTO_INCREMENT,
  `admin_aid` VARCHAR(12) NOT NULL,
  `logo_url` VARCHAR(255) NULL,
  `identity_img_url` VARCHAR(255) NULL,
  `intro_text` TEXT NOT NULL,
  PRIMARY KEY (`company_id`),
  INDEX `fk_company_info_admin1_idx` (`admin_aid` ASC) VISIBLE,
  UNIQUE INDEX `admin_aid_UNIQUE` (`admin_aid` ASC) VISIBLE,
  CONSTRAINT `fk_company_info_admin1`
    FOREIGN KEY (`admin_aid`)
    REFERENCES `LotteON`.`admin` (`aid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`support`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`support` (
  `sup_id` VARCHAR(10) NOT NULL,
  `biz_hours` VARCHAR(20) NOT NULL,
  `eft_manager` VARCHAR(15) NOT NULL,
  `admin_aid` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`sup_id`),
  INDEX `fk_support_admin1_idx` (`admin_aid` ASC) VISIBLE,
  UNIQUE INDEX `admin_aid_UNIQUE` (`admin_aid` ASC) VISIBLE,
  CONSTRAINT `fk_support_admin1`
    FOREIGN KEY (`admin_aid`)
    REFERENCES `LotteON`.`admin` (`aid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`copyright`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`copyright` (
  `copyright_id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`copyright_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`shop`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`shop` (
  `shop_id` INT NOT NULL AUTO_INCREMENT,
  `operation` VARCHAR(1) NOT NULL,
  `mgmt` VARCHAR(5) NOT NULL,
  `admin_aid` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`shop_id`),
  INDEX `fk_shop_admin1_idx` (`admin_aid` ASC) VISIBLE,
  UNIQUE INDEX `admin_aid_UNIQUE` (`admin_aid` ASC) VISIBLE,
  CONSTRAINT `fk_shop_admin1`
    FOREIGN KEY (`admin_aid`)
    REFERENCES `LotteON`.`admin` (`aid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`sales`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`sales` (
  `sales_id` INT NOT NULL AUTO_INCREMENT,
  `admin_aid` VARCHAR(12) NOT NULL,
  `order_count` INT NOT NULL DEFAULT 0,
  `pay_completed` INT NOT NULL DEFAULT 0,
  `shippied` INT NOT NULL DEFAULT 0,
  `confiremed` INT NOT NULL DEFAULT 0,
  `order_total` INT NOT NULL DEFAULT 0,
  `sales` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`sales_id`),
  INDEX `fk_sales_admin1_idx` (`admin_aid` ASC) VISIBLE,
  UNIQUE INDEX `admin_aid_UNIQUE` (`admin_aid` ASC) VISIBLE,
  CONSTRAINT `fk_sales_admin1`
    FOREIGN KEY (`admin_aid`)
    REFERENCES `LotteON`.`admin` (`aid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`point`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`point` (
  `point_id` INT NOT NULL AUTO_INCREMENT,
  `Users_uid` VARCHAR(12) NOT NULL,
  `given` INT NOT NULL DEFAULT 0,
  `balance` INT NOT NULL DEFAULT 0,
  `details` VARCHAR(45) NOT NULL,
  `given_date` DATETIME NOT NULL,
  PRIMARY KEY (`point_id`),
  INDEX `fk_point_Users1_idx` (`Users_uid` ASC) VISIBLE,
  CONSTRAINT `fk_point_Users1`
    FOREIGN KEY (`Users_uid`)
    REFERENCES `LotteON`.`Users` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`product_option_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`product_option_item` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `product_option_option_id` INT NOT NULL,
  `item_name` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`item_id`),
  INDEX `fk_product_option_item_product_option1_idx` (`product_option_option_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_option_item_product_option1`
    FOREIGN KEY (`product_option_option_id`)
    REFERENCES `LotteON`.`product_option` (`option_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`product_compliance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`product_compliance` (
  `compliance_id` INT NOT NULL AUTO_INCREMENT,
  `Products_pid` INT NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  `tax` VARCHAR(5) NOT NULL,
  `receipt` VARCHAR(10) NOT NULL,
  `biz_type` VARCHAR(10) NOT NULL,
  `origin` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`compliance_id`),
  INDEX `fk_product_compliance_Products1_idx` (`Products_pid` ASC) VISIBLE,
  UNIQUE INDEX `Products_pid_UNIQUE` (`Products_pid` ASC) VISIBLE,
  CONSTRAINT `fk_product_compliance_Products1`
    FOREIGN KEY (`Products_pid`)
    REFERENCES `LotteON`.`Products` (`pid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`order_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`order_item` (
  `order_item_id` INT NOT NULL AUTO_INCREMENT,
  `order_oid` INT NOT NULL,
  `Products_pid` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`order_item_id`),
  INDEX `fk_order_has_Products_Products1_idx` (`Products_pid` ASC) VISIBLE,
  INDEX `fk_order_has_Products_order1_idx` (`order_oid` ASC) VISIBLE,
  UNIQUE INDEX `order_oid_UNIQUE` (`order_oid` ASC) VISIBLE,
  UNIQUE INDEX `Products_pid_UNIQUE` (`Products_pid` ASC) VISIBLE,
  CONSTRAINT `fk_order_has_Products_order1`
    FOREIGN KEY (`order_oid`)
    REFERENCES `LotteON`.`order` (`oid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_has_Products_Products1`
    FOREIGN KEY (`Products_pid`)
    REFERENCES `LotteON`.`Products` (`pid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`delivery`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`delivery` (
  `delivery_id` INT NOT NULL AUTO_INCREMENT,
  `order_oid` INT NOT NULL,
  `delivery_company` VARCHAR(10) NOT NULL,
  `delivery_num` VARCHAR(20) NOT NULL,
  `other` TEXT NULL,
  `shipping_status` VARCHAR(4) NOT NULL,
  `delivery_at` DATETIME NOT NULL,
  PRIMARY KEY (`delivery_id`),
  INDEX `fk_delivery_order1_idx` (`order_oid` ASC) VISIBLE,
  CONSTRAINT `fk_delivery_order1`
    FOREIGN KEY (`order_oid`)
    REFERENCES `LotteON`.`order` (`oid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`coupon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`coupon` (
  `coupon_id` INT NOT NULL,
  `admin_aid` VARCHAR(12) NOT NULL,
  `coupon_type` VARCHAR(10) NOT NULL,
  `coupon_name` VARCHAR(20) NOT NULL,
  `benefit` VARCHAR(15) NOT NULL,
  `vaild_period` VARCHAR(30) NOT NULL,
  `issued_quantity` VARCHAR(3) NOT NULL,
  `used_quantity` VARCHAR(3) NOT NULL,
  `status` VARCHAR(5) NOT NULL,
  `issued_date` DATETIME NOT NULL,
  `mgmt` VARCHAR(2) NOT NULL,
  `other` TEXT NOT NULL,
  `Users_uid` VARCHAR(12) NOT NULL,
  `Products_pid` INT NOT NULL,
  PRIMARY KEY (`coupon_id`),
  INDEX `fk_coupon_admin1_idx` (`admin_aid` ASC) VISIBLE,
  UNIQUE INDEX `admin_aid_UNIQUE` (`admin_aid` ASC) VISIBLE,
  INDEX `fk_coupon_Users1_idx` (`Users_uid` ASC) VISIBLE,
  INDEX `fk_coupon_Products1_idx` (`Products_pid` ASC) VISIBLE,
  CONSTRAINT `fk_coupon_admin1`
    FOREIGN KEY (`admin_aid`)
    REFERENCES `LotteON`.`admin` (`aid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_coupon_Users1`
    FOREIGN KEY (`Users_uid`)
    REFERENCES `LotteON`.`Users` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_coupon_Products1`
    FOREIGN KEY (`Products_pid`)
    REFERENCES `LotteON`.`Products` (`pid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`coupon_issuance_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`coupon_issuance_status` (
  `issuance_id` INT NOT NULL AUTO_INCREMENT,
  `coupon_coupon_id` INT NOT NULL,
  `coupon_status` VARCHAR(3) NOT NULL,
  `mgmt` VARCHAR(2) NOT NULL,
  PRIMARY KEY (`issuance_id`),
  INDEX `fk_coupon_issuance_status_coupon1_idx` (`coupon_coupon_id` ASC) VISIBLE,
  CONSTRAINT `fk_coupon_issuance_status_coupon1`
    FOREIGN KEY (`coupon_coupon_id`)
    REFERENCES `LotteON`.`coupon` (`coupon_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`notice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`notice` (
  `notice_id` INT NOT NULL AUTO_INCREMENT,
  `notice_type` VARCHAR(10) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `content` TEXT NOT NULL,
  `hits` INT NOT NULL,
  `upload_at` DATE NOT NULL,
  `mgmt` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`notice_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`faq`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`faq` (
  `faq_id` INT NOT NULL AUTO_INCREMENT,
  `type_1` VARCHAR(5) NOT NULL,
  `type_2` VARCHAR(5) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `content` TEXT NOT NULL,
  `hits` INT NOT NULL,
  `upload_date` DATE NOT NULL,
  `mgmt` VARCHAR(4) NOT NULL,
  `cate_icon` VARCHAR(1) NOT NULL,
  PRIMARY KEY (`faq_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`qna`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`qna` (
  `qna_id` INT NOT NULL AUTO_INCREMENT,
  `Users_uid` VARCHAR(12) NOT NULL,
  `qna_type_1` VARCHAR(5) NOT NULL,
  `qna_type_2` VARCHAR(5) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `content` TEXT NOT NULL,
  `date` DATE NOT NULL,
  `status` VARCHAR(5) NOT NULL,
  `answer` TEXT NULL,
  INDEX `fk_table1_Users1_idx` (`Users_uid` ASC) VISIBLE,
  PRIMARY KEY (`qna_id`),
  CONSTRAINT `fk_table1_Users1`
    FOREIGN KEY (`Users_uid`)
    REFERENCES `LotteON`.`Users` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`job`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`job` (
  `job_id` INT NOT NULL AUTO_INCREMENT,
  `department` VARCHAR(10) NOT NULL,
  `Experience` VARCHAR(10) NOT NULL,
  `type` VARCHAR(5) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `writer` VARCHAR(5) NOT NULL,
  `status` VARCHAR(5) NOT NULL,
  `Period` DATETIME NOT NULL,
  `date` DATETIME NOT NULL,
  `other` TEXT NULL,
  PRIMARY KEY (`job_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`review` (
  `review_id` INT NOT NULL AUTO_INCREMENT,
  `Products_pid` INT NOT NULL,
  `Users_uid` VARCHAR(12) NOT NULL,
  `rating` INT NOT NULL,
  `comment` TEXT NOT NULL,
  `write_at` DATE NOT NULL,
  PRIMARY KEY (`review_id`),
  INDEX `fk_review_Products1_idx` (`Products_pid` ASC) VISIBLE,
  INDEX `fk_review_Users1_idx` (`Users_uid` ASC) VISIBLE,
  CONSTRAINT `fk_review_Products1`
    FOREIGN KEY (`Products_pid`)
    REFERENCES `LotteON`.`Products` (`pid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_review_Users1`
    FOREIGN KEY (`Users_uid`)
    REFERENCES `LotteON`.`Users` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`company_timeline`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`company_timeline` (
  `timeline_id` INT NOT NULL AUTO_INCREMENT,
  `year` YEAR NOT NULL,
  `month` TINYINT NULL,
  `description` TEXT NOT NULL,
  `sort` INT NOT NULL,
  PRIMARY KEY (`timeline_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`company_blog_posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`company_blog_posts` (
  `post_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `summary` TEXT NOT NULL,
  `thumbnail_url` VARCHAR(255) NOT NULL,
  `blog_url` VARCHAR(255) NOT NULL,
  `write_at` DATE NOT NULL,
  PRIMARY KEY (`post_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`company_banners`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`company_banners` (
  `banners_id` INT NOT NULL AUTO_INCREMENT,
  `banner_img_url` VARCHAR(255) NOT NULL,
  `alt_text` VARCHAR(255) NULL,
  `link` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`banners_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LotteON`.`my_page`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LotteON`.`my_page` (
  `order_item_order_item_id` INT NOT NULL,
  `admin_aid` VARCHAR(12) NOT NULL,
  `delivery_delivery_id` INT NOT NULL,
  INDEX `fk_my_page_order_item1_idx` (`order_item_order_item_id` ASC) VISIBLE,
  INDEX `fk_my_page_admin1_idx` (`admin_aid` ASC) VISIBLE,
  INDEX `fk_my_page_delivery1_idx` (`delivery_delivery_id` ASC) VISIBLE,
  CONSTRAINT `fk_my_page_order_item1`
    FOREIGN KEY (`order_item_order_item_id`)
    REFERENCES `LotteON`.`order_item` (`order_item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_my_page_admin1`
    FOREIGN KEY (`admin_aid`)
    REFERENCES `LotteON`.`admin` (`aid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_my_page_delivery1`
    FOREIGN KEY (`delivery_delivery_id`)
    REFERENCES `LotteON`.`delivery` (`delivery_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
