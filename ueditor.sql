/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50168
Source Host           : localhost:3306
Source Database       : ueditor

Target Server Type    : MYSQL
Target Server Version : 50168
File Encoding         : 65001

Date: 2017-02-14 17:09:25
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

-- ----------------------------
-- Table structure for `wangeditor`
-- ----------------------------
DROP TABLE IF EXISTS `wangeditor`;
CREATE TABLE `wangeditor` (
  `wang_id` int(32) NOT NULL AUTO_INCREMENT,
  `wang_html` longblob,
  `wang_name` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`wang_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wangeditor
-- ----------------------------
INSERT INTO `wangeditor` VALUES ('1', 0x3C68746D6C3E3331333264736A6B666B6C6173646A666C6B75757364616A666C6B71776572E788B1E4B88AE5BD93E5878FE882A5E4BA86E5BC80E5A78BE5A4A7E5AEB6E58886E5BC80E4BA86E983BDE698AF3C2F68746D6C3E, null);
