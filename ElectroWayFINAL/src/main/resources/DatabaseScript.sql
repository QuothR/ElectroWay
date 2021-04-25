-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.5.9-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for electroway
DROP DATABASE IF EXISTS `electroway`;
CREATE DATABASE IF NOT EXISTS `electroway` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `electroway`;

-- Dumping structure for table electroway.car
DROP TABLE IF EXISTS `car`;
CREATE TABLE IF NOT EXISTS `car`
(
    `id`                    bigint(20)   NOT NULL,
    `model`                 varchar(255) NOT NULL,
    `year`                  bigint(20)   NOT NULL,
    `autonomy`              bigint(20)   NOT NULL,
    `medium_consumption_kw` bigint(20)   NOT NULL,
    `owner_id`              bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `owner_id` (`owner_id`),
    CONSTRAINT `car_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

-- Data exporting was unselected.

-- Dumping structure for table electroway.charging_plug
DROP TABLE IF EXISTS `charging_plug`;
CREATE TABLE IF NOT EXISTS `charging_plug`
(
    `id`                bigint(20)   NOT NULL,
    `status`            tinyint(4)   NOT NULL,
    `level`             bigint(20)   NOT NULL,
    `connector_type`    varchar(255) NOT NULL,
    `price_kw`          double       NOT NULL,
    `charging_speed_kw` double       NOT NULL,
    `charging_point_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `charging_point_id` (`charging_point_id`),
    CONSTRAINT `charging_plug_ibfk_1` FOREIGN KEY (`charging_point_id`) REFERENCES `charging_point` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

-- Data exporting was unselected.

-- Dumping structure for table electroway.charging_point
DROP TABLE IF EXISTS `charging_point`;
CREATE TABLE IF NOT EXISTS `charging_point`
(
    `id`         bigint(20) NOT NULL,
    `station_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `station_id` (`station_id`),
    CONSTRAINT `charging_point_ibfk_1` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

-- Data exporting was unselected.

-- Dumping structure for table electroway.password_reset_token
DROP TABLE IF EXISTS `password_reset_token`;
CREATE TABLE IF NOT EXISTS `password_reset_token`
(
    `id`      bigint(11) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20)   DEFAULT NULL,
    `token`   varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

-- Data exporting was unselected.

-- Dumping structure for table electroway.report
DROP TABLE IF EXISTS `report`;
CREATE TABLE IF NOT EXISTS `report`
(
    `id`          bigint(20)   NOT NULL,
    `text_report` varchar(255) NOT NULL,
    `user_id`     bigint(20) DEFAULT NULL,
    `station_id`  bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    KEY `station_id` (`station_id`),
    CONSTRAINT `report_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `report_ibfk_2` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

-- Data exporting was unselected.

-- Dumping structure for table electroway.review
DROP TABLE IF EXISTS `review`;
CREATE TABLE IF NOT EXISTS `review`
(
    `id`          bigint(20)   NOT NULL,
    `text_review` varchar(255) NOT NULL,
    `rating`      tinyint(4)   NOT NULL,
    `user_id`     bigint(20) DEFAULT NULL,
    `station_id`  bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    KEY `station_id` (`station_id`),
    CONSTRAINT `review_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `review_ibfk_2` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

-- Data exporting was unselected.

-- Dumping structure for table electroway.station
DROP TABLE IF EXISTS `station`;
CREATE TABLE IF NOT EXISTS `station`
(
    `id`                     bigint(20)  NOT NULL,
    `address`                varchar(64) NOT NULL,
    `map_latitude_location`  double      NOT NULL,
    `map_longitude_location` double      NOT NULL,
    `owner_id`               bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `map_latitude_location` (`map_latitude_location`, `map_longitude_location`, `owner_id`),
    KEY `owner_id` (`owner_id`),
    CONSTRAINT `station_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

-- Data exporting was unselected.

-- Dumping structure for table electroway.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user`
(
    `id`                   bigint(20)   NOT NULL,
    `user_name`            varchar(255) NOT NULL,
    `password_hash`        varchar(255) NOT NULL,
    `first_name`           varchar(255) DEFAULT NULL,
    `last_name`            varchar(255) DEFAULT NULL,
    `phone_number`         varchar(255) DEFAULT NULL,
    `email_address`        varchar(255) NOT NULL,
    `address1`             varchar(255) DEFAULT NULL,
    `address2`             varchar(255) DEFAULT NULL,
    `city`                 varchar(255) DEFAULT NULL,
    `region`               varchar(255) DEFAULT NULL,
    `country`              varchar(255) DEFAULT NULL,
    `zipcode`              varchar(255) DEFAULT NULL,
    `is_enabled`           tinyint(1)   NOT NULL,
    `password_reset_token` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_name` (`user_name`),
    UNIQUE KEY `email_address` (`email_address`),
    UNIQUE KEY `phone_number` (`phone_number`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

-- Data exporting was unselected.

-- Dumping structure for table electroway.user_sequence
DROP TABLE IF EXISTS `user_sequence`;
CREATE TABLE IF NOT EXISTS `user_sequence`
(
    `next_not_cached_value` bigint(21)          NOT NULL,
    `minimum_value`         bigint(21)          NOT NULL,
    `maximum_value`         bigint(21)          NOT NULL,
    `start_value`           bigint(21)          NOT NULL COMMENT 'start value when sequences is created or value if RESTART is used',
    `increment`             bigint(21)          NOT NULL COMMENT 'increment value',
    `cache_size`            bigint(21) unsigned NOT NULL,
    `cycle_option`          tinyint(1) unsigned NOT NULL COMMENT '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
    `cycle_count`           bigint(21)          NOT NULL COMMENT 'How many cycles have been done'
) ENGINE = InnoDB SEQUENCE=1;

-- Data exporting was unselected.

-- Dumping structure for table electroway.verification_token
DROP TABLE IF EXISTS `verification_token`;
CREATE TABLE IF NOT EXISTS `verification_token`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(20)          DEFAULT NULL,
    `token`       varchar(255)        DEFAULT NULL,
    `expiry_date` timestamp  NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    CONSTRAINT `verification_token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 31
  DEFAULT CHARSET = latin1;

-- Data exporting was unselected.

/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
