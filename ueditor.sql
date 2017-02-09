/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50168
Source Host           : localhost:3306
Source Database       : ueditor

Target Server Type    : MYSQL
Target Server Version : 50168
File Encoding         : 65001

Date: 2017-02-09 17:10:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `editor`
-- ----------------------------
DROP TABLE IF EXISTS `editor`;
CREATE TABLE `editor` (
  `editor_id` int(11) NOT NULL AUTO_INCREMENT,
  `editorName` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`editor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of editor
-- ----------------------------
