/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80032
Source Host           : localhost:3306
Source Database       : s-api

Target Server Type    : MYSQL
Target Server Version : 80032
File Encoding         : 65001

Date: 2024-03-31 23:38:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '1');
INSERT INTO `user` VALUES ('root', '123');
INSERT INTO `user` VALUES ('user', '123456');
INSERT INTO `user` VALUES ('user1', '123456');
INSERT INTO `user` VALUES ('user2', '123456');
INSERT INTO `user` VALUES ('user3', '123456');
INSERT INTO `user` VALUES ('user4', '123456');
INSERT INTO `user` VALUES ('user5', '123456');
INSERT INTO `user` VALUES ('user6', '123456');
INSERT INTO `user` VALUES ('user7', '123456');
