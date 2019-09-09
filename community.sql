/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.5.62 : Database - community
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`community` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `community`;

/*Table structure for table `announcement` */

DROP TABLE IF EXISTS `announcement`;

CREATE TABLE `announcement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '公告主键',
  `title` varchar(50) DEFAULT NULL COMMENT '公告名称',
  `content` varchar(500) DEFAULT NULL COMMENT '公告内容',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT '1' COMMENT '公告状态 0-删除 1-正常',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论主键',
  `parent_id` bigint(20) NOT NULL COMMENT '父级id',
  `type` int(11) DEFAULT NULL COMMENT '评论类型 1-问题评论 2-评论的回复',
  `commentator` bigint(20) DEFAULT NULL COMMENT '评论人',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '评论时间',
  `like_count` int(11) DEFAULT NULL COMMENT '点赞数',
  `content` varchar(1000) DEFAULT NULL COMMENT '评论内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

/*Table structure for table `notification` */

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '通知主键',
  `notifier` bigint(20) DEFAULT NULL COMMENT '通知人id',
  `notifier_name` varchar(30) DEFAULT NULL COMMENT '通知人名称',
  `receiver` bigint(20) DEFAULT NULL COMMENT '接收人id',
  `outer_id` bigint(20) DEFAULT NULL COMMENT '评论上层主键',
  `outer_title` varchar(50) DEFAULT NULL COMMENT '评论上层名称',
  `type` int(11) DEFAULT NULL COMMENT '评论或问题下的评论',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT '0' COMMENT '是否已读 0-未读 1-已读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Table structure for table `question` */

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '提问主键',
  `title` varchar(50) NOT NULL COMMENT '问题名称',
  `description` varchar(2000) DEFAULT NULL COMMENT '内容描述',
  `creator` bigint(20) DEFAULT NULL COMMENT '作者',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `comment_count` int(11) DEFAULT NULL COMMENT '评论数量',
  `view_count` int(11) DEFAULT NULL COMMENT '浏览次数',
  `like_count` int(11) DEFAULT NULL COMMENT '点赞数',
  `tag` varchar(50) DEFAULT NULL COMMENT '标签',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色主键',
  `role` varchar(20) DEFAULT NULL COMMENT '角色',
  `description` varchar(20) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `tags` */

DROP TABLE IF EXISTS `tags`;

CREATE TABLE `tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标签主键',
  `name` varchar(20) DEFAULT NULL COMMENT '标签名',
  `type` int(11) DEFAULT NULL COMMENT '标签分类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `username` varchar(30) DEFAULT NULL COMMENT '用户名',
  `password` varchar(200) DEFAULT NULL COMMENT '密码',
  `cmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `avatar_url` varchar(100) DEFAULT NULL COMMENT '头像地址',
  `state` int(11) DEFAULT '0' COMMENT '账号状态',
  `role` bigint(2) DEFAULT '1' COMMENT '角色，默认1用户user',
  `nickname` varchar(30) DEFAULT NULL COMMENT '昵称',
  `detail` bigint(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Table structure for table `user_detail` */

DROP TABLE IF EXISTS `user_detail`;

CREATE TABLE `user_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户信息主键',
  `e_mail` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `industry` varchar(30) DEFAULT NULL COMMENT '行业',
  `position` varchar(30) DEFAULT NULL COMMENT '职位',
  `birthday` date DEFAULT NULL COMMENT '生日',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户角色中间表主键',
  `userid` bigint(20) DEFAULT NULL COMMENT '用户主键',
  `roleid` bigint(20) DEFAULT NULL COMMENT '角色主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
