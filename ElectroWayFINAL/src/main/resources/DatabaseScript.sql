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
CREATE DATABASE IF NOT EXISTS `electroway` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `electroway`;

-- Dumping database structure for electroway.template_car

DROP TABLE IF EXISTS `template_car`;
CREATE TABLE IF NOT EXISTS `template_car`
(
    `id`                bigint(20)   NOT NULL,
    `model`             varchar(255) NOT NULL,
    `year`              bigint(20)   NOT NULL,
    `battery_capacity`  double       NOT NULL,
    `charging_capacity` double       NOT NULL,
    `plug_type`         varchar(255) NOT NULL,
    `vehicle_max_speed` bigint(20)   NOT NULL,
    `auxiliary_kwh`     double       NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

insert into template_car
values (1, 'Tesla Model 3 Standard Range', 2019, 54, 7.2, 'Type 2', 209, 0);
insert into template_car
values (2, 'Tesla Model 3 Standard Range Plus', 2019, 54, 7.2, 'Type 2', 225, 0);
insert into template_car
values (3, 'Tesla Model 3 Mid Range', 2018, 62, 7.2, 'Type 2', 225, 0);
insert into template_car
values (4, 'Tesla Model 3 Mid Range', 2019, 62, 7.2, 'Type 2', 225, 0);
insert into template_car
values (5, 'Tesla Model 3 Long Range RWD', 2017, 75, 7.2, 'Type 2', 225, 0);
insert into template_car
values (6, 'Tesla Model 3 Long Range RWD', 2018, 75, 7.2, 'Type 2', 225, 0);
insert into template_car
values (7, 'Tesla Model 3 Long Range RWD', 2019, 75, 7.2, 'Type 2', 225, 0);
insert into template_car
values (8, 'Tesla Model 3 Long Range AWD', 2018, 75, 7.2, 'Type 2', 233, 0);
insert into template_car
values (9, 'Tesla Model 3 Long Range AWD Performance', 2018, 75, 7.2, 'Type 2', 261, 0);
insert into template_car
values (10, 'Tesla Model S 40kWh', 2012, 40, 7.2, 'Type 2', 177, 0);
insert into template_car
values (11, 'Tesla Model S 40kWh', 2013, 40, 7.2, 'Type 2', 177, 0);
insert into template_car
values (12, 'Tesla Model S 60', 2012, 60, 7.2, 'Type 2', 209, 0);
insert into template_car
values (13, 'Tesla Model S 60', 2013, 60, 7.2, 'Type 2', 209, 0);
insert into template_car
values (14, 'Tesla Model S 60', 2014, 60, 7.2, 'Type 2', 209, 0);
insert into template_car
values (15, 'Tesla Model S 60', 2015, 60, 7.2, 'Type 2', 209, 0);
insert into template_car
values (16, 'Tesla Model S 60', 2016, 60, 7.2, 'Type 2', 209, 0);
insert into template_car
values (17, 'Tesla Model S 60', 2017, 60, 7.2, 'Type 2', 209, 0);
insert into template_car
values (18, 'Tesla Model S 60D', 2016, 60, 7.2, 'Type 2', 209, 0);
insert into template_car
values (19, 'Tesla Model S 60D', 2017, 60, 7.2, 'Type 2', 209, 0);
insert into template_car
values (20, 'Tesla Model S 70', 2015, 70, 7.2, 'Type 2', 225, 0);
insert into template_car
values (21, 'Tesla Model S 70', 2016, 70, 7.2, 'Type 2', 225, 0);
insert into template_car
values (22, 'Tesla Model S 70D', 2015, 70, 7.2, 'Type 2', 225, 0);
insert into template_car
values (23, 'Tesla Model S 70D', 2016, 70, 7.2, 'Type 2', 225, 0);
insert into template_car
values (24, 'Tesla Model S 75', 2016, 75, 7.2, 'Type 2', 225, 0);
insert into template_car
values (25, 'Tesla Model S 75', 2017, 75, 7.2, 'Type 2', 225, 0);
insert into template_car
values (26, 'Tesla Model S 75D', 2016, 75, 7.2, 'Type 2', 225, 0);
insert into template_car
values (27, 'Tesla Model S 75D', 2017, 75, 7.2, 'Type 2', 225, 0);
insert into template_car
values (28, 'Tesla Model S 75D', 2018, 75, 7.2, 'Type 2', 225, 0);
insert into template_car
values (29, 'Tesla Model S 75D', 2019, 75, 7.2, 'Type 2', 225, 0);
insert into template_car
values (30, 'Tesla Model S 85', 2012, 85, 7.2, 'Type 2', 225, 0);
insert into template_car
values (31, 'Tesla Model S 85', 2016, 85, 7.2, 'Type 2', 225, 0);
insert into template_car
values (32, 'Tesla Model S 85D', 2014, 85, 7.2, 'Type 2', 249, 0);
insert into template_car
values (33, 'Tesla Model S 85D', 2016, 85, 7.2, 'Type 2', 249, 0);
insert into template_car
values (34, 'Tesla Model S P85', 2012, 85, 7.2, 'Type 2', 209, 0);
insert into template_car
values (35, 'Tesla Model S P85', 2014, 85, 7.2, 'Type 2', 209, 0);
insert into template_car
values (36, 'Tesla Model S P85D', 2014, 85, 7.2, 'Type 2', 249, 0);
insert into template_car
values (37, 'Tesla Model S P85D', 2016, 85, 7.2, 'Type 2', 249, 0);
insert into template_car
values (38, 'Tesla Model S 90', 2015, 90, 7.2, 'Type 2', 225, 0);
insert into template_car
values (39, 'Tesla Model S 90', 2016, 90, 7.2, 'Type 2', 225, 0);
insert into template_car
values (40, 'Tesla Model S 90D', 2015, 90, 7.2, 'Type 2', 249, 0);
insert into template_car
values (41, 'Tesla Model S 90D', 2017, 90, 7.2, 'Type 2', 249, 0);
insert into template_car
values (42, 'Tesla Model S P90D', 2015, 90, 7.2, 'Type 2', 249, 0);
insert into template_car
values (43, 'Tesla Model S P90D', 2016, 90, 7.2, 'Type 2', 249, 0);
insert into template_car
values (44, 'Tesla Model S 100D', 2016, 100, 7.2, 'Type 2', 249, 0);
insert into template_car
values (45, 'Tesla Model S 100D', 2019, 100, 7.2, 'Type 2', 249, 0);
insert into template_car
values (46, 'Tesla Model S P100D', 2016, 100, 7.2, 'Type 2', 262, 0);
insert into template_car
values (47, 'Tesla Model S P100D', 2019, 100, 7.2, 'Type 2', 262, 0);
insert into template_car
values (48, 'Tesla Model S Raven Performance', 2019, 100, 7.2, 'Type 2', 262, 0);
insert into template_car
values (49, 'Tesla Model S Raven Performance', 2021, 100, 7.2, 'Type 2', 262, 0);
insert into template_car
values (50, 'Tesla Model S Raven Long Range Plus', 2019, 100, 7.2, 'Type 2', 249, 0);
insert into template_car
values (51, 'Tesla Model X 60D', 2016, 60, 7.2, 'Type 2', 210, 0);
insert into template_car
values (52, 'Tesla Model X 75D', 2016, 75, 7.2, 'Type 2', 210, 0);
insert into template_car
values (53, 'Tesla Model X 90D', 2016, 90, 7.2, 'Type 2', 250, 0);
insert into template_car
values (54, 'Tesla Model X P90D', 2016, 90, 7.2, 'Type 2', 250, 0);
insert into template_car
values (55, 'Tesla Model X 100D', 2016, 100, 7.2, 'Type 2', 250, 0);
insert into template_car
values (56, 'Tesla Model X P100D', 2016, 100, 7.2, 'Type 2', 250, 0);
insert into template_car
values (57, 'Tesla Model Y Standard Range', 2021, 54, 7.2, 'Type 2', 217, 0);
insert into template_car
values (58, 'Tesla Model Y Long Range AWD', 2020, 75, 7.2, 'Type 2', 217, 0);
insert into template_car
values (59, 'Tesla Model Y Long Range AWD ( Performance Upgrade )', 2020, 75, 7.2, 'Type 2', 217, 0);
insert into template_car
values (60, 'Tesla Model Y Long Range Performance', 2020, 75, 7.2, 'Type 2', 249, 0);
insert into template_car
values (61, 'Audi e-Tron 55 quattro', 2018, 95, 7.2, 'Type 2', 200, 0);
insert into template_car
values (62, 'Audi e-Tron 55 quattro', 2019, 95, 7.2, 'Type 2', 200, 0);
insert into template_car
values (63, 'Audi e-Tron 55 quattro', 2020, 95, 7.2, 'Type 2', 200, 0);
insert into template_car
values (64, 'Audi e-Tron 50 quattro', 2019, 71, 7.2, 'Type 2', 190, 0);
insert into template_car
values (65, 'Audi e-Tron 55 quattro Sportback', 2020, 95, 7.2, 'Type 2', 200, 0);
insert into template_car
values (66, 'Audi e-Tron 50 quattro Sportback', 2020, 71, 7.2, 'Type 2', 190, 0);
insert into template_car
values (67, 'Mercedes-Benz EQC400 4MATIC', 2019, 80, 7.2, 'Type 2', 180, 0);
insert into template_car
values (68, 'Audi e-Tron GT', 2020, 94, 7.2, 'Type 2', 245, 0);
insert into template_car
values (69, 'Audi e-Tron GT RS', 2020, 94, 7.2, 'Type 2', 250, 0);
insert into template_car
values (70, 'Volkswagen ID3 Pro', 2020, 62, 7.2, 'Type 2', 160, 0);
insert into template_car
values (71, 'Volkswagen ID3 Pro S', 2020, 82, 7.2, 'Type 2', 160, 0);
insert into template_car
values (72, 'Volkswagen ID3 Pro Performance', 2020, 62, 7.2, 'Type 2', 160, 0);
insert into template_car
values (73, 'Volkswagen ID3 Pure Performance', 2020, 48, 7.2, 'Type 2', 160, 0);
insert into template_car
values (74, 'Volkswagen ID4 1st', 2020, 82, 7.2, 'Type 2', 160, 0);
insert into template_car
values (75, 'Volkswagen ID4 Pure', 2020, 55, 7.2, 'Type 2', 160, 0);
insert into template_car
values (76, 'Volkswagen ID4 Pure Performance', 2020, 55, 7.2, 'Type 2', 160, 0);
insert into template_car
values (77, 'Volkswagen ID4 Pro Performance', 2020, 82, 7.2, 'Type 2', 160, 0);
insert into template_car
values (78, 'Volkswagen ID4 GTX', 2020, 82, 7.2, 'Type 2', 200, 0);
insert into template_car
values (79, 'BMW i3s 120 Ah', 2020, 42, 7.2, 'Type 2', 160, 0);
insert into template_car
values (80, 'BMW i3 120 Ah', 2020, 42, 7.2, 'Type 2', 150, 0);
insert into template_car
values (81, 'Nissan Leaf', 2020, 40, 7.2, 'Type 2', 144, 0);
insert into template_car
values (82, 'Nissan Leaf e+', 2020, 62, 7.2, 'Type 2', 157, 0);
insert into template_car
values (83, 'Renault ZE50', 2019, 52, 7.2, 'Type 2', 135, 0);
insert into template_car
values (84, 'Renault ZE50', 2020, 52, 7.2, 'Type 2', 135, 0);
insert into template_car
values (85, 'Renault ZE', 2013, 52, 7.2, 'Type 2', 135, 0);
insert into template_car
values (86, 'Renault ZE', 2013, 52, 7.2, 'Type 2', 135, 0);
insert into template_car
values (87, 'Fiat 500e Hatchback 24 kWh', 2020, 24, 7.2, 'Type 2', 145, 0);
insert into template_car
values (88, 'Fiat 500e Hatchback 42 kWh', 2020, 42, 7.2, 'Type 2', 150, 0);
insert into template_car
values (89, 'Skoda Enyaq iV 50', 2020, 55, 7.2, 'Type 2', 160, 0);
insert into template_car
values (90, 'Skoda Enyaq iV 60', 2020, 62, 7.2, 'Type 2', 160, 0);
insert into template_car
values (91, 'Skoda Enyaq iV 80', 2020, 82, 7.2, 'Type 2', 160, 0);
insert into template_car
values (92, 'Porsche Taycan Turbo S', 2020, 94, 7.2, 'Type 2', 260, 0);
insert into template_car
values (93, 'Porsche Taycan Turbo', 2020, 94, 7.2, 'Type 2', 260, 0);
insert into template_car
values (94, 'Porsche Taycan 4S', 2020, 80, 7.2, 'Type 2', 250, 0);
insert into template_car
values (95, 'Porsche Taycan 4S Plus', 2020, 94, 7.2, 'Type 2', 250, 0);
insert into template_car
values (96, 'Porsche Taycan', 2020, 80, 7.2, 'Type 2', 230, 0);
insert into template_car
values (97, 'Porsche Taycan Plus', 2020, 94, 7.2, 'Type 2', 230, 0);
insert into template_car
values (98, 'Peugeot e-208', 2020, 50, 7.2, 'Type 2', 150, 0);
insert into template_car
values (99, 'Peugeot e-208 SUV', 2020, 50, 7.2, 'Type 2', 150, 0);
insert into template_car
values (100, 'Dacia Spring Electric', 2021, 27, 7.2, 'Type 2', 125, 0);

-- Dumping structure for table electroway.car
DROP TABLE IF EXISTS `car`;
CREATE TABLE IF NOT EXISTS `car`
(
    `id`                bigint(20)   NOT NULL,
    `model`             varchar(255) NOT NULL,
    `year`              bigint(20)   NOT NULL,
    `battery_capacity`  double       NOT NULL,
    `charging_capacity` double       NOT NULL,
    `vehicle_max_speed` bigint(20)   NOT NULL,
    `auxiliary_kwh`     double       NOT NULL,
    `owner_id`          bigint references user (id),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

DROP TABLE IF EXISTS `plug_type`;
CREATE TABLE IF NOT EXISTS `plug_type`
(
    `id`        bigint(20)   NOT NULL,
    `plug_type` varchar(255) NOT NULL,
    `car_id`    bigint(20)   NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `car_plug_type_ibfk_1` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

-- Dumping structure for table electroway.consumption
DROP TABLE IF EXISTS `consumption`;
CREATE TABLE IF NOT EXISTS `consumption`
(
    `id`              bigint(20) NOT NULL,
    `speed`           bigint(20) NOT NULL,
    `consumption_kwh` double     NOT NULL,
    `car_id`          bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `car_id` (`car_id`),
    CONSTRAINT `consumption_ibfk_1` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

-- Data exporting was unselected.

-- Dumping structure for table electroway.charging_plug
DROP TABLE IF EXISTS `charging_plug`;
CREATE TABLE IF NOT EXISTS `charging_plug`
(
    `id`                bigint(20)   NOT NULL,
    `status`            tinyint(4)   NOT NULL,
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

-- Dumping structure for table electroway.paypal_detail
CREATE TABLE IF NOT EXISTS `paypal_detail`
(
    `owner_id`  bigint(20)   NOT NULL,
    `client_id` varchar(255) NOT NULL,
    `secret`    varchar(255) NOT NULL,
    `id`        bigint(20)   NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`id`),
    UNIQUE KEY `paypal_detail_owner_id_uindex` (`owner_id`),
    CONSTRAINT `paypal_detail_user_id_fk` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = latin1;

-- Data exporting was unselected.

-- Dumping structure for table electroway.privilege
CREATE TABLE IF NOT EXISTS `privilege`
(
    `id`   bigint(20)   NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
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

-- Dumping structure for table electroway.role
CREATE TABLE IF NOT EXISTS `role`
(
    `id`   bigint(20)   NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = latin1;

-- Data exporting was unselected.

-- Dumping structure for table electroway.role_privilege
CREATE TABLE IF NOT EXISTS `role_privilege`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id`      bigint(20) DEFAULT NULL,
    `privilege_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `role_id` (`role_id`),
    KEY `privilege_id` (`privilege_id`),
    CONSTRAINT `role_privilege_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
    CONSTRAINT `role_privilege_ibfk_2` FOREIGN KEY (`privilege_id`) REFERENCES `privilege` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = latin1;

-- Data exporting was unselected.

-- Dumping structure for table electroway.station
DROP TABLE IF EXISTS `station`;
CREATE TABLE IF NOT EXISTS `station`
(
    `id`                     bigint(20)   NOT NULL,
    `address`                varchar(255) NOT NULL,
    `map_latitude_location`  double       NOT NULL,
    `map_longitude_location` double       NOT NULL,
    `description`            varchar(255) DEFAULT NULL,
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
  AUTO_INCREMENT = 43
  DEFAULT CHARSET = latin1;


-- Data exporting was unselected.

-- Dumping structure for table electroway.favourite
DROP TABLE IF EXISTS `favourite`;
CREATE TABLE IF NOT EXISTS `favourite`
(
    `id`         bigint(20) NOT NULL,
    `user_id`    bigint(20) DEFAULT NULL,
    `station_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    KEY `station_id` (`station_id`),
    CONSTRAINT `favourite_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `favourite_ibfk_2` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

-- Data exporting was unselected.

-- Dumping structure for table electroway.user_role
CREATE TABLE IF NOT EXISTS `user_role`
(
    `id`      bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) DEFAULT NULL,
    `role_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    KEY `role_id` (`role_id`),
    CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
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

-- Dumping structure for table electroway.station_sequence
DROP TABLE IF EXISTS `station_sequence`;
CREATE TABLE IF NOT EXISTS `station_sequence`
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

-- Dumping structure for table electroway.review_sequence
DROP TABLE IF EXISTS `review_sequence`;
CREATE TABLE IF NOT EXISTS `review_sequence`
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

-- Dumping structure for table electroway.charging_point_sequence
DROP TABLE IF EXISTS `charging_point_sequence`;
CREATE TABLE IF NOT EXISTS `charging_point_sequence`
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

-- Dumping structure for table electroway.charging_plug_sequence
DROP TABLE IF EXISTS `charging_plug_sequence`;
CREATE TABLE IF NOT EXISTS `charging_plug_sequence`
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

-- Dumping structure for table electroway.car_sequence
DROP TABLE IF EXISTS `car_sequence`;
CREATE TABLE IF NOT EXISTS `car_sequence`
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

-- Dumping structure for table electroway.report_sequence
DROP TABLE IF EXISTS `report_sequence`;
CREATE TABLE IF NOT EXISTS `report_sequence`
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

-- Dumping structure for table electroway.template_car_sequence
DROP TABLE IF EXISTS `template_car_sequence`;
CREATE TABLE IF NOT EXISTS `template_car_sequence`
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

-- Dumping structure for table electroway.favourite_sequence
DROP TABLE IF EXISTS `favourite_sequence`;
CREATE TABLE IF NOT EXISTS `favourite_sequence`
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

-- Data exporting was unselected.-- Dumping structure for table electroway.consumption_sequence
DROP TABLE IF EXISTS `consumption_sequence`;
CREATE TABLE IF NOT EXISTS `consumption_sequence`
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

-- Data exporting was unselected.-- Dumping structure for table electroway.car_plug_type_sequence
DROP TABLE IF EXISTS `plug_type_sequence`;
CREATE TABLE IF NOT EXISTS `plug_type_sequence`
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

/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;