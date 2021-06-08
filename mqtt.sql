/*
 Navicat Premium Data Transfer

 Source Server         : mysql-server
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : mysql-server:3306
 Source Schema         : mqtt

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 08/06/2021 10:39:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for environment
-- ----------------------------
DROP TABLE IF EXISTS `environment`;
CREATE TABLE `environment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_Id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '客户端ID',
  `slave_id` int(11) NULL DEFAULT NULL COMMENT '从站地址',
  `co2` decimal(10, 3) NULL DEFAULT NULL COMMENT 'CO2',
  `pm25` decimal(10, 3) NULL DEFAULT NULL COMMENT 'PM2.5',
  `humidity` decimal(10, 3) NULL DEFAULT NULL COMMENT '湿度',
  `temperature` decimal(10, 3) NULL DEFAULT NULL COMMENT '温度',
  `data_time` datetime NULL DEFAULT NULL COMMENT '数据时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1402091645994356738 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
