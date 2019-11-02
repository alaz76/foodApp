-- Create category table
CREATE TABLE `category` (
  `category_id`   INT            NOT NULL  AUTO_INCREMENT,
  `department_id` INT            NOT NULL,
  `name`          VARCHAR(100)   NOT NULL,
  `description`   VARCHAR(1000),
  PRIMARY KEY (`category_id`),
  KEY `idx_category_department_id` (`department_id`)
) ENGINE=MyISAM;

-- Create product table
CREATE TABLE `product` (
  `product_id`       INT           NOT NULL  AUTO_INCREMENT,
  `name`             VARCHAR(100)  NOT NULL,
  `description`      VARCHAR(1000) NOT NULL,
  `price`            DECIMAL(10,2) NOT NULL,
  `discounted_price` DECIMAL(10,2) NOT NULL  DEFAULT '0.00',
  PRIMARY KEY  (`product_id`),
  FULLTEXT KEY `idx_ft_product_name_description` (`name`, `description`)
) ENGINE=MyISAM;

-- Create product_category table
CREATE TABLE `product_category` (
  `product_id`  INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`product_id`, `category_id`)
) ENGINE=MyISAM;

-- Create user table
CREATE TABLE `user` (
  `user_id`            INT           NOT NULL AUTO_INCREMENT,
  `name`               VARCHAR(50)   NOT NULL,
  `email`              VARCHAR(100)  NOT NULL UNIQUE,
  `password`           VARCHAR(100)   NOT NULL,
  `credit_card`        TEXT,
  `address_1`          VARCHAR(100),
  `address_2`          VARCHAR(100),
  `city`               VARCHAR(100),
  `region`             VARCHAR(100),
  `postal_code`        VARCHAR(100),
  `country`            VARCHAR(100),
  `mob_phone`          varchar(100),
  `authorities`        TEXT,
  `token`              TEXT,
  PRIMARY KEY  (`user_id`),
  UNIQUE KEY `idx_user_email` (`email`)
) ENGINE=MyISAM;