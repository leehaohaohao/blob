/*
 Navicat Premium Data Transfer

 Source Server         : library
 Source Server Type    : MySQL
 Source Server Version : 80035 (8.0.35)
 Source Host           : localhost:3306
 Source Schema         : blob

 Target Server Type    : MySQL
 Target Server Version : 80035 (8.0.35)
 File Encoding         : 65001

 Date: 12/09/2024 13:15:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for api_statistics
-- ----------------------------
DROP TABLE IF EXISTS `api_statistics`;
CREATE TABLE `api_statistics`  (
  `id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `count` bigint NULL DEFAULT NULL,
  `max_time` bigint NULL DEFAULT NULL,
  `average_time` bigint NULL DEFAULT NULL,
  `name` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `min_time` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of api_statistics
-- ----------------------------
INSERT INTO `api_statistics` VALUES ('A1829352753999769600', 1026, 5121, 126, 'UserInfoController.getUserAvatarInfo', 22);
INSERT INTO `api_statistics` VALUES ('A1829352758076633088', 817, 1738, 126, 'ForumController.getPostByTag', 11);
INSERT INTO `api_statistics` VALUES ('A1829390571732070400', 8, 871, 463, 'LoginController.mLogin', 121);
INSERT INTO `api_statistics` VALUES ('A1829390573367848960', 35, 326, 37, 'BackController.getHotPost', 2);
INSERT INTO `api_statistics` VALUES ('A1829390573367848961', 35, 316, 37, 'BackController.getHotTag', 2);
INSERT INTO `api_statistics` VALUES ('A1829390574231875584', 35, 539, 70, 'BackController.num', 6);
INSERT INTO `api_statistics` VALUES ('A1829390589721440256', 22, 369, 65, 'BackController.api', 3);
INSERT INTO `api_statistics` VALUES ('A1829390675583037440', 77, 245, 14, 'BackController.getPerson', 2);
INSERT INTO `api_statistics` VALUES ('A1829390938389737472', 32, 557, 30, 'BackController.getGroupList', 2);
INSERT INTO `api_statistics` VALUES ('A1829390952524541952', 41, 328, 15, 'NoteController.managerSelect', 2);
INSERT INTO `api_statistics` VALUES ('A1829390960279810048', 53, 712, 19, 'ForumController.getApprovalList', 2);
INSERT INTO `api_statistics` VALUES ('A1829398017074573312', 316, 316, 7, 'NoteController.select', 0);
INSERT INTO `api_statistics` VALUES ('A1829398017074573313', 749, 5111, 54, 'ForumController.getMyLikePost', 1);
INSERT INTO `api_statistics` VALUES ('A1829398017397534720', 511, 5135, 49, 'ForumController.getMyPost', 1);
INSERT INTO `api_statistics` VALUES ('A1829398029200306176', 302, 186, 7, 'ForumController.getTag', 0);
INSERT INTO `api_statistics` VALUES ('A1829398144493334528', 105, 192, 12, 'UserInfoController.otherInfo', 1);
INSERT INTO `api_statistics` VALUES ('A1829398196108439552', 36, 1475, 120, 'SocialController.getConcern', 1);
INSERT INTO `api_statistics` VALUES ('A1829398382465560576', 11, 61, 10, 'FeedBackController.getType', 3);
INSERT INTO `api_statistics` VALUES ('A1831962423172046848', 23, 1476, 485, 'LoginController.loginCon', 93);
INSERT INTO `api_statistics` VALUES ('A1831963440982827008', 27, 19, 8, 'FeedBackController.get', 3);
INSERT INTO `api_statistics` VALUES ('A1831963710697545728', 59, 30, 7, 'SocialController.getComment', 2);
INSERT INTO `api_statistics` VALUES ('A1831963710814986240', 59, 195, 125, 'ForumController.getPostById', 24);
INSERT INTO `api_statistics` VALUES ('A1831964256242278400', 15, 32, 19, 'ForumController.likeOrCollect', 13);
INSERT INTO `api_statistics` VALUES ('A1831964464430751744', 20, 117, 20, 'SocialController.comment', 10);
INSERT INTO `api_statistics` VALUES ('A1831964734384545792', 13, 18387, 1439, 'UserInfoController.updateTag', 3);
INSERT INTO `api_statistics` VALUES ('A1831964776763793408', 29, 18, 4, 'GroupController.selectMyGroup', 2);
INSERT INTO `api_statistics` VALUES ('A1831964776776376320', 29, 6, 3, 'GroupController.selectGroupList', 2);
INSERT INTO `api_statistics` VALUES ('A1831964776889622528', 73, 5, 3, 'GroupController.add2Group', 1);
INSERT INTO `api_statistics` VALUES ('A1831964777074171904', 73, 12, 6, 'GroupController.selectGroupComment', 2);
INSERT INTO `api_statistics` VALUES ('A1831964817821835264', 24, 6, 3, 'GroupController.exit', 1);
INSERT INTO `api_statistics` VALUES ('A1833092551973277696', 31, 1503, 405, 'ForumController.post', 28);
INSERT INTO `api_statistics` VALUES ('A1833114570886492160', 30, 30, 19, 'ForumController.approvalPost', 11);
INSERT INTO `api_statistics` VALUES ('A1833360382358093824', 3, 1342, 463, 'FeedBackController.publish', 19);
INSERT INTO `api_statistics` VALUES ('A1833362979324329984', 5, 13427, 12562, 'LoginController.code', 11446);
INSERT INTO `api_statistics` VALUES ('A1833363620599857152', 2, 93, 63, 'LoginController.send', 33);
INSERT INTO `api_statistics` VALUES ('A1833365962623725568', 17, 161, 24, 'GroupController.chat', 11);
INSERT INTO `api_statistics` VALUES ('A1833366620823269376', 3, 19, 14, 'GroupController.removeFromGroup', 10);
INSERT INTO `api_statistics` VALUES ('A1833366752633466880', 2, 3, 3, 'GroupController.selectGroup', 2);
INSERT INTO `api_statistics` VALUES ('A1833366896959467520', 2, 1578, 797, 'GroupController.create', 16);
INSERT INTO `api_statistics` VALUES ('A1833367577221050368', 7, 314, 130, 'UserInfoController.updateInfo', 26);
INSERT INTO `api_statistics` VALUES ('A1833378569075138560', 5, 15658, 3244, 'NoteController.publish', 16);
INSERT INTO `api_statistics` VALUES ('A1833381494652510208', 10, 166, 93, 'BackController.updatePerson', 26);
INSERT INTO `api_statistics` VALUES ('A1833382045368819712', 2, 13, 11, 'BackController.updateGroup', 9);
INSERT INTO `api_statistics` VALUES ('A1833385609029853184', 4, 363, 210, 'SocialController.concern', 57);
INSERT INTO `api_statistics` VALUES ('A1833385740898770944', 5, 20, 14, 'ForumController.deletePost', 11);
INSERT INTO `api_statistics` VALUES ('A1833388337395245056', 2, 734, 614, 'LoginController.register', 494);
INSERT INTO `api_statistics` VALUES ('A1833776873155973120', 1, 5, 5, 'ForumController.getApprovalPostById', 5);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `comment_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论id',
  `user_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `post_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '帖子id',
  `parent_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '上一级评论id',
  `comment_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评论内容',
  `comment_date` datetime NULL DEFAULT NULL COMMENT '评论时间',
  `comment_status` tinyint(1) NULL DEFAULT NULL COMMENT '0：正常 1：删除',
  `top_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '顶级父评论',
  PRIMARY KEY (`comment_id`) USING BTREE,
  INDEX `post`(`post_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('C1833384910980227072', 'U1805198690294980608', 'P1833126644513505280', '', 'es确实很好用啊', '2024-09-10 14:00:00', 0, '');
INSERT INTO `comment` VALUES ('C1833385020787105792', 'U1805198690294980608', 'P1833126644513505280', 'C1833384910980227072', 'es查询速度很快', '2024-09-10 14:00:26', 0, 'C1833384910980227072');
INSERT INTO `comment` VALUES ('C1833385074369339392', 'U1805198690294980608', 'P1833126644513505280', '', 'es可以用来辅助数据库查询', '2024-09-10 14:00:39', 0, '');
INSERT INTO `comment` VALUES ('C1833394432738172928', 'U1805198690294980608', 'P1833393798760734720', '', 'test', '2024-09-10 14:37:50', 0, '');
INSERT INTO `comment` VALUES ('C1833394449263730688', 'U1805198690294980608', 'P1833393798760734720', 'C1833394432738172928', 'test', '2024-09-10 14:37:54', 0, 'C1833394432738172928');
INSERT INTO `comment` VALUES ('C1833394468591083520', 'U1805198690294980608', 'P1833393798760734720', '', 'test2', '2024-09-10 14:37:59', 0, '');
INSERT INTO `comment` VALUES ('C1833395226526982144', 'U1805198690294980608', 'P1833111579852812288', '', 'java是世界上最好的语言', '2024-09-10 14:40:59', 0, '');
INSERT INTO `comment` VALUES ('C1833395254083559424', 'U1805198690294980608', 'P1833111579852812288', 'C1833395226526982144', '我爱学java', '2024-09-10 14:41:06', 0, 'C1833395226526982144');
INSERT INTO `comment` VALUES ('C1833409893668323328', 'U1805230146664833025', 'P1833393798760734720', '', '景色确实很不错啊', '2024-09-10 15:39:16', 0, '');
INSERT INTO `comment` VALUES ('C1833416498422054912', 'U1833414699124359168', 'P1833126238056087552', '', 'test', '2024-09-10 16:05:31', 0, '');
INSERT INTO `comment` VALUES ('C1833416513601241088', 'U1833414699124359168', 'P1833126238056087552', '', 'test', '2024-09-10 16:05:35', 0, '');
INSERT INTO `comment` VALUES ('C1833416537357778944', 'U1833414699124359168', 'P1833126238056087552', 'C1833416513601241088', 'tew', '2024-09-10 16:05:40', 0, 'C1833416513601241088');
INSERT INTO `comment` VALUES ('C1833416559851831296', 'U1833414699124359168', 'P1833126238056087552', 'C1833416537357778944', 'test', '2024-09-10 16:05:46', 0, 'C1833416513601241088');
INSERT INTO `comment` VALUES ('C1833456132543840256', 'U1805198690294980608', 'P1833393728279650304', '', 'hah', '2024-09-10 18:43:01', 0, '');
INSERT INTO `comment` VALUES ('C1833456146452152320', 'U1805198690294980608', 'P1833393728279650304', 'C1833456132543840256', 'fafaf1', '2024-09-10 18:43:04', 0, 'C1833456132543840256');
INSERT INTO `comment` VALUES ('C1833777620346064896', 'U1805198690294980608', 'P1833092549947428864', '', '这个文章很不错！', '2024-09-11 16:00:29', 0, '');
INSERT INTO `comment` VALUES ('C1833777653497843712', 'U1805198690294980608', 'P1833092549947428864', 'C1833777620346064896', '我也是这样想的', '2024-09-11 16:00:37', 0, 'C1833777620346064896');

-- ----------------------------
-- Table structure for concern
-- ----------------------------
DROP TABLE IF EXISTS `concern`;
CREATE TABLE `concern`  (
  `user_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `concern_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '关注id',
  `concern_time` datetime NULL DEFAULT NULL COMMENT '关注时间',
  PRIMARY KEY (`user_id`, `concern_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of concern
-- ----------------------------
INSERT INTO `concern` VALUES ('U1805198690294980608', 'U1805230146664833026', '2024-09-10 14:02:46');
INSERT INTO `concern` VALUES ('U1805230146664833025', 'U1805230146664833026', '2024-09-10 15:38:43');
INSERT INTO `concern` VALUES ('U1833414699124359168', 'U1805198690294980608', '2024-09-11 15:21:56');
INSERT INTO `concern` VALUES ('U1833414699124359168', 'U1805230146664833026', '2024-09-11 15:21:53');

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `feedback_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '问题id',
  `user_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `status` tinyint NULL DEFAULT NULL COMMENT '是否解决 0：未解决 1：已解决',
  `time` datetime NULL DEFAULT NULL COMMENT '反馈日期',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '反馈内容',
  `file` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '反馈问题相关材料链接',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '问题类型',
  PRIMARY KEY (`feedback_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES ('FB1833360377144573952', 'U1805198690294980608', 0, '2024-09-10 12:22:31', '首页布局需要优化', 'http://localhost:9090/blob/feedback/3def47a9-298d-48cd-ae0c-41abf7a255c5.jpeg', 2);
INSERT INTO `feedback` VALUES ('FB1833416676373790720', 'U1833414699124359168', 0, '2024-09-10 16:06:14', 'tete', 'http://localhost:9090/blob/img/background.png', 1);
INSERT INTO `feedback` VALUES ('FB1833765107709628416', 'U1833414699124359168', 0, '2024-09-11 15:10:46', '首页布局可以在美观一点', 'http://localhost:9090/blob/img/background.png', 2);

-- ----------------------------
-- Table structure for feedback_type
-- ----------------------------
DROP TABLE IF EXISTS `feedback_type`;
CREATE TABLE `feedback_type`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '类型id',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型名字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of feedback_type
-- ----------------------------
INSERT INTO `feedback_type` VALUES (1, '页面bug');
INSERT INTO `feedback_type` VALUES (2, '优化');
INSERT INTO `feedback_type` VALUES (3, '功能');
INSERT INTO `feedback_type` VALUES (4, '数据丢失');
INSERT INTO `feedback_type` VALUES (5, '安全问题');
INSERT INTO `feedback_type` VALUES (6, '其他');

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group`  (
  `id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `time` datetime NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of group
-- ----------------------------
INSERT INTO `group` VALUES ('G1827294685224845312', 'U1805198690294980608', 'http://localhost:9090/blob/img/group.png', '不挂科', '2024-08-24 18:39:37', 0);
INSERT INTO `group` VALUES ('G1833366896447762432', 'U1805230146664833025', 'http://localhost:9090/blob/group/avatar/8b4eccd0-b0d7-4fcf-9185-831b59d6fbe2.jpeg', 'java学习交流群', '2024-09-10 12:48:25', 0);
INSERT INTO `group` VALUES ('G1833772542591619072', 'U1833414699124359168', 'http://localhost:9090/blob/img/group.png', '期末复习交流群', '2024-09-11 15:40:19', 0);

-- ----------------------------
-- Table structure for group_comment
-- ----------------------------
DROP TABLE IF EXISTS `group_comment`;
CREATE TABLE `group_comment`  (
  `id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `group_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of group_comment
-- ----------------------------
INSERT INTO `group_comment` VALUES ('GC1833409982277189632', 'U1805198690294980608', 'G1833366896447762432', '哈喽', '2024-09-10 15:39:38');
INSERT INTO `group_comment` VALUES ('GC1833410007975690240', 'U1805230146664833025', 'G1833366896447762432', '你好', '2024-09-10 15:39:44');
INSERT INTO `group_comment` VALUES ('GC1833413444444454912', 'U1833388335084183552', 'G1833366896447762432', '你好', '2024-09-10 15:53:23');
INSERT INTO `group_comment` VALUES ('GC1833417026484928512', 'U1833388335084183552', 'G1833366896447762432', 'nihao', '2024-09-10 16:07:37');
INSERT INTO `group_comment` VALUES ('GC1833771504232943616', 'U1833414699124359168', 'G1833366896447762432', '你好', '2024-09-11 15:36:11');
INSERT INTO `group_comment` VALUES ('GC1833772605992718336', 'U1833414699124359168', 'G1833772542591619072', '你好啊', '2024-09-11 15:40:34');
INSERT INTO `group_comment` VALUES ('GC1833772679833440256', 'U1805198690294980608', 'G1833772542591619072', '哈喽', '2024-09-11 15:40:51');

-- ----------------------------
-- Table structure for group_user
-- ----------------------------
DROP TABLE IF EXISTS `group_user`;
CREATE TABLE `group_user`  (
  `group_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`group_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of group_user
-- ----------------------------
INSERT INTO `group_user` VALUES ('G1833366896447762432', 'U1833388335084183552');
INSERT INTO `group_user` VALUES ('G1833772542591619072', 'U1805198690294980608');

-- ----------------------------
-- Table structure for history
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history`  (
  `history_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '历史浏览id',
  `user_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `browse_time` datetime NULL DEFAULT NULL COMMENT '浏览时间',
  `post_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '帖子id',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态 0：正常 1：删除',
  PRIMARY KEY (`history_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of history
-- ----------------------------

-- ----------------------------
-- Table structure for hot_tag
-- ----------------------------
DROP TABLE IF EXISTS `hot_tag`;
CREATE TABLE `hot_tag`  (
  `tag` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '热点标签',
  `nums` int NULL DEFAULT NULL COMMENT '热点标签数',
  PRIMARY KEY (`tag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of hot_tag
-- ----------------------------
INSERT INTO `hot_tag` VALUES ('es', 2);
INSERT INTO `hot_tag` VALUES ('http', 1);
INSERT INTO `hot_tag` VALUES ('idea', 1);
INSERT INTO `hot_tag` VALUES ('java', 21);
INSERT INTO `hot_tag` VALUES ('jsp', 1);
INSERT INTO `hot_tag` VALUES ('maven', 1);
INSERT INTO `hot_tag` VALUES ('mybatis', 2);
INSERT INTO `hot_tag` VALUES ('mybatisp', 1);
INSERT INTO `hot_tag` VALUES ('mysql', 2);
INSERT INTO `hot_tag` VALUES ('nacos', 1);
INSERT INTO `hot_tag` VALUES ('netty', 2);
INSERT INTO `hot_tag` VALUES ('nosql', 1);
INSERT INTO `hot_tag` VALUES ('redis', 1);
INSERT INTO `hot_tag` VALUES ('restful', 1);
INSERT INTO `hot_tag` VALUES ('spring', 1);
INSERT INTO `hot_tag` VALUES ('springboot', 3);
INSERT INTO `hot_tag` VALUES ('swagger', 1);
INSERT INTO `hot_tag` VALUES ('test', 3);
INSERT INTO `hot_tag` VALUES ('拦截器', 1);
INSERT INTO `hot_tag` VALUES ('搜索数据库', 1);
INSERT INTO `hot_tag` VALUES ('数据库', 2);
INSERT INTO `hot_tag` VALUES ('景色', 1);
INSERT INTO `hot_tag` VALUES ('编程教程', 1);
INSERT INTO `hot_tag` VALUES ('网络编程', 2);
INSERT INTO `hot_tag` VALUES ('计算机', 1);
INSERT INTO `hot_tag` VALUES ('计算机操作系统', 2);
INSERT INTO `hot_tag` VALUES ('计算机组成原理', 2);
INSERT INTO `hot_tag` VALUES ('阿里巴巴', 1);

-- ----------------------------
-- Table structure for love_collect
-- ----------------------------
DROP TABLE IF EXISTS `love_collect`;
CREATE TABLE `love_collect`  (
  `user_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `post_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子id',
  `collect_time` datetime NULL DEFAULT NULL COMMENT '收藏日期',
  `love_time` datetime NULL DEFAULT NULL COMMENT '点赞日期',
  `collect` tinyint(1) NULL DEFAULT 0 COMMENT '0：未收藏 1：已收藏',
  `love` tinyint(1) NULL DEFAULT 0 COMMENT '0：未点赞 1：已点赞',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of love_collect
-- ----------------------------
INSERT INTO `love_collect` VALUES ('U1805198690294980608', 'P1833092549947428864', '2024-09-11 16:00:39', NULL, 1, 0);
INSERT INTO `love_collect` VALUES ('U1805198690294980608', 'P1833112909740781568', NULL, '2024-09-10 13:31:44', 0, 1);
INSERT INTO `love_collect` VALUES ('U1805198690294980608', 'P1833125494364045312', '2024-09-10 13:32:06', NULL, 1, 0);
INSERT INTO `love_collect` VALUES ('U1805198690294980608', 'P1833126238056087552', NULL, '2024-09-10 13:32:15', 0, 1);
INSERT INTO `love_collect` VALUES ('U1805230146664833025', 'P1833116495925227520', '2024-09-10 15:35:49', '2024-09-10 15:35:48', 1, 1);
INSERT INTO `love_collect` VALUES ('U1805230146664833025', 'P1833122564156284928', '2024-09-10 15:35:41', '2024-09-10 15:35:40', 1, 1);
INSERT INTO `love_collect` VALUES ('U1805230146664833025', 'P1833126238056087552', '2024-09-10 15:59:51', '2024-09-10 15:59:48', 1, 1);
INSERT INTO `love_collect` VALUES ('U1833414699124359168', 'P1833092549947428864', '2024-09-11 15:04:27', '2024-09-11 15:04:24', 1, 1);
INSERT INTO `love_collect` VALUES ('U1833414699124359168', 'P1833110479397466112', NULL, '2024-09-11 15:04:49', 0, 1);

-- ----------------------------
-- Table structure for note
-- ----------------------------
DROP TABLE IF EXISTS `note`;
CREATE TABLE `note`  (
  `note_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告id',
  `user_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发布人id',
  `note_date` datetime NULL DEFAULT NULL COMMENT '公告日期',
  `note_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '公告内容',
  `note_status` tinyint(1) NULL DEFAULT 0 COMMENT '公告状态 0：正常 1：删除',
  `note_type` tinyint(1) NULL DEFAULT NULL COMMENT '公告类型',
  PRIMARY KEY (`note_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of note
-- ----------------------------
INSERT INTO `note` VALUES ('N1833378568961892352', 'U1805198690294980608', '2024-09-10 13:34:48', '<h1>更新公告</h1><p>修复若干问题</p><p>优化首页布局</p>', 0, 1);
INSERT INTO `note` VALUES ('N1833407355959230464', 'U1805198690294980608', '2024-09-10 15:29:11', '<ol><li>不挂课博客平台正式上线</li><li>所有文章均免费阅读</li><li>福利多多</li><li>有问题请在问题反馈处及时反馈</li></ol>', 0, 0);
INSERT INTO `note` VALUES ('N1833775816438829056', 'U1805198690294980608', '2024-09-11 15:53:19', '<p>期末复习开始啦，所有文章免费阅读！祝各位不挂科，考个好成绩！</p>', 0, 1);

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `permission_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `permission` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permissions
-- ----------------------------
INSERT INTO `permissions` VALUES ('P00000001', '发布文章');
INSERT INTO `permissions` VALUES ('P00000002', '发表评论');
INSERT INTO `permissions` VALUES ('P00000003', '编辑文章');
INSERT INTO `permissions` VALUES ('P00000004', '删除文章');
INSERT INTO `permissions` VALUES ('P00000005', '查看文章');
INSERT INTO `permissions` VALUES ('P00000006', '发布公告');
INSERT INTO `permissions` VALUES ('P00000007', '审核文章');

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `post_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子id',
  `user_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发布人id',
  `post_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '文案内容',
  `tag` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签，一个内容最多有5个标签',
  `post_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `post_like` bigint NULL DEFAULT 0 COMMENT '点赞数',
  `collect` bigint NULL DEFAULT 0 COMMENT '收藏数',
  `post_status` tinyint UNSIGNED NULL DEFAULT 2 COMMENT '0：正常 1：删除 2:审核中 3:审核不通过',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `cover` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面图片',
  `approval_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `approval_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核人',
  `rand_num` int NULL DEFAULT 0 COMMENT '随机数',
  PRIMARY KEY (`post_id`) USING BTREE,
  UNIQUE INDEX `id_index`(`post_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES ('P1833092549947428864', 'U1805198690294980608', '<p>绝对装入（只适用于单道程序环境）：在编译时，如果知道程序将放到内存中的哪个位置，编译程序将产生绝对地址的目标代码。</p><p>静态重定位（必须分配其要求的全部内存空间，程序运行期间不可移动）：编译、链接后的装入模块的地址都是从0开始的，指令中使用的地址、数据存放的地址都是相对于起始地址而言的逻辑地址。可根据内存的当前情况，将装入模块装入到内存的适当位置。装入时对地址进行重定位（<font color=\"#c24f4a\">地址变换时在装入时一次完成的</font>）</p><p>\r\n\r\n\r\n\r\n</p><p>动态重定位（可将程序分配到不连续的存储区中，允许程序在运行期间在内存中移动）：编译、链接后的装入模块的地址都是从0开始的。装入程序把装入模块装入内存后，并不会立即把逻辑地址转换为物理地址，而是把地址转换推迟到程序真正要执行时才进行。因此装入内存后所有的地址依然是逻辑地址。这种方式需要一个<font color=\"#c24f4a\">重定位寄存器</font>。</p><br>', '计算机操作系统', '2024-09-09 18:38:16', 1, 2, 0, '重定位把逻辑地址变成内存的物理地址', 'http://localhost:9090/blob/post/cover/865e3ca1-2bc3-4a8f-b711-bce4a6580455.jpg', '2024-09-09 20:05:50', NULL, 1);
INSERT INTO `post` VALUES ('P1833110479397466112', 'U1805198690294980608', '<p><b>运算器：</b>进行加减乘除运算和逻辑运算和附加运算</p><p><b>存储器：</b>用于存储数据和指令</p><p>\r\n\r\n\r\n\r\n</p><p><b>控制器：</b>自动执行指令</p><br>', '计算机组成原理', '2024-09-09 19:49:30', 1, 0, 0, '计算机组成', 'http://localhost:9090/blob/post/cover/64dc8463-f44e-4e57-a289-b4e8f8a93133.png', '2024-09-09 20:05:50', NULL, 0);
INSERT INTO `post` VALUES ('P1833111579852812288', 'U1805198690294980608', '<p>Java是一种广泛使用的<a href=\"https://so.csdn.net/so/search?q=%E9%9D%A2%E5%90%91%E5%AF%B9%E8%B1%A1&amp;spm=1001.2101.3001.7020\" target=\"_blank\">面向对象</a>的编程语言，以其平台无关性、安全性、可移植性和高性能而著称。Java支持一次编写，到处运行（WORA）的理念，使得Java程序可以在任何安装了Java虚拟机（JVM）的平台上运行。<br/></p>', 'java', '2024-09-09 19:53:53', 0, 0, 0, 'Java简介', 'http://localhost:9090/blob/post/cover/cda80743-3085-47a9-8536-647fc57ec982.jpeg', '2024-09-09 20:05:49', NULL, 1);
INSERT INTO `post` VALUES ('P1833112688331862016', 'U1805198690294980608', '<h4>1.简化配置&nbsp;</h4><h4>2. 起步依赖（Starter&nbsp;Dependencies）</h4><h4>3.&nbsp;<a href=\"https://so.csdn.net/so/search?q=%E8%87%AA%E5%8A%A8%E5%8C%96%E9%85%8D%E7%BD%AE&amp;spm=1001.2101.3001.7020\" target=\"_blank\">自动化配置</a></h4><h4>4. 外部化配置</h4><h4>5. 基于注解&nbsp;</h4><h4>6. 整合生态系统&nbsp;</h4><br>', 'java|springboot', '2024-09-09 19:58:17', 0, 0, 0, '理解SpringBoot', 'http://localhost:9090/blob/post/cover/c705f0df-d4b6-459e-a401-6fda294192d6.png', '2024-09-09 20:05:49', NULL, 0);
INSERT INTO `post` VALUES ('P1833112909740781568', 'U1805198690294980608', '<p>在Spring Boot中，控制器（Controller）是处理HTTP请求和返回响应的组件。控制器是Spring MVC框架的一部分，用于实现模型-视图-控制器（MVC）设计模式中的控制器层。<br/><br/>SpringBoot提供了两种注解来表示此类负责接受和处理 HTTP 请求：@Controller和@RestController，如果请求的是页面和数据，使用@Controller；如果只是请求数据，则可以使用@RestController。<br/><br/>默认情况下，@RestController注解会将返回的对象数据转换为JSON格式。<br/></p>', 'java|springboot', '2024-09-09 19:59:10', 1, 0, 0, 'SpringBoot控制器', 'http://localhost:9090/blob/post/cover/1d308914-f419-47ac-a926-26fd8e4c0f1f.png', '2024-09-09 20:05:48', NULL, 0);
INSERT INTO `post` VALUES ('P1833113195301580800', 'U1805198690294980608', '<p>注解 @RequsetMapping主要负责 URL 的路由映射，可以添加在Controller 类或者具体的方法上。如果添加 Controller 类上，则这个 Controller 中的所有路由映射都会加上此映射规则，如果添加在某个方法上，则只会对当前方法生效。<br/><br/>常用属性参数：<br/><br/><font color=\"#c24f4a\">value 或 path</font>：<br/>用途：定义请求的URL路径。<br/>说明：value是@RequestMapping的属性，可以指定一个或多个URL路径。path是@RequestMapping的别名，与value功能相同，但只能指定一个路径。<br/><font color=\"#c24f4a\">method</font>：<br/>用途：限制请求的HTTP方法（如GET、POST、PUT、DELETE等）。<br/>说明：可以指定一个或多个HTTP方法，只有匹配这些方法的请求才会被映射到相应的处理方法。<br/><font color=\"#c24f4a\">params</font>：<br/>用途：根据请求参数的存在与否来决定是否映射请求。<br/>说明：可以指定一个或多个参数条件，只有当这些参数在请求中出现时，请求才会被映射。<br/><font color=\"#c24f4a\">headers</font>：<br/>用途：根据请求头的存在与否来决定是否映射请求。<br/>说明：可以指定一个或多个请求头条件，只有当这些请求头在请求中出现时，请求才会被映射。<br/><font color=\"#c24f4a\">consumes</font>：<br/>用途：指定可接受的请求体的媒体类型（如application/json、text/plain等）。<br/>说明：只有当请求的Content-Type与指定的媒体类型匹配时，请求才会被映射。<br/><font color=\"#c24f4a\">produces</font>：<br/>用途：指定控制器方法可以产生的媒体类型。<br/>说明：这通常用于设置响应的Content-Type，告诉客户端期望接收的媒体类型。<br/><font color=\"#c24f4a\">name</font>：<br/>用途：为映射定义一个名称，方便在其他注解中引用。<br/>说明：在大型应用中，使用名称可以简化映射的引用，提高代码的可维护性。<br/>参数传递：<br/><br/>@RequestParam：用于将HTTP请求的查询字符串参数或请求体参数绑定到控制器方法的参数上。如果参数名称一致，可以省略。<br/>@PathVariable：用于提取URL中的动态路径变量，并将这些变量传递给控制器方法的参数。<br/>@RequestBody：用于接收请求体中的参数，通常用于处理JSON、XML等非表单编码的数据。<br/></p>', 'java|springboot', '2024-09-09 20:00:18', 0, 0, 0, 'SpringBoot路由', 'http://localhost:9090/blob/img/background.png', '2024-09-09 20:05:47', NULL, 1);
INSERT INTO `post` VALUES ('P1833113394572963840', 'U1805198690294980608', '<p>SpringBoot定义了HardlerInterceptor接口来实现自定义拦截器的功能。HandlerInterceptor接口定义了preHandle、postHandle、afterCompletion三种方法，通过重写这三种方法实现请求前、请求后等操作。<br/><br/><font color=\"#c24f4a\">preHandle</font>：在控制器（Controller）方法执行之前被调用。<br/><font color=\"#c24f4a\">postHandle</font>：它在请求的控制器方法执行之后、渲染视图之前被调用。<br/><font color=\"#c24f4a\">afterCompletion</font>：请求处理流程的最后阶段被调用。<br/>拦截器的使用分为两个步骤：1. 定义，2. 注册。<br/></p>', 'java|spring|拦截器', '2024-09-09 20:01:06', 0, 0, 0, 'Spring拦截器', 'http://localhost:9090/blob/post/cover/be5cb421-2d0c-4889-9b76-ef81c00febb6.png', '2024-09-09 20:05:47', NULL, 0);
INSERT INTO `post` VALUES ('P1833113976905936896', 'U1805198690294980608', '<p>RESTFUL 是目前流行的互联网软件服务架构设计风格。要求客户端使用GET、POST、PUT、DELETE四种表示操作方式的动词对服务端资源进行操作：<br/><br/>GET用于获取资源<br/>POST用于新建资源（也可以用于更新资源）<br/>PUT用于更新资源<br/>DELETE用于删除资源。<br/>RESTFUL 的特点：资源的表现形式是JSON或者HTML，客户端与服务端之间的交互在请求之间是无状态的，从客户端到服务端的每个请求都包含必须的信息。<br/><br></p>', 'restful|http', '2024-09-09 20:03:24', 0, 0, 0, 'RESTFUL请求', 'http://localhost:9090/blob/post/cover/1d578451-1299-4f42-bb62-530ef99557f9.jpg', '2024-09-09 20:05:46', NULL, 0);
INSERT INTO `post` VALUES ('P1833115535463165952', 'U1805230146664833025', '<p>Swagger 是一个开源的 API 设计和文档工具，由 Tony Tam 于 2010 年创建，它可以帮助开发人员更快、更简单地设计、构建、文档化和测试 RESTful API。Swagger 可以自动生成交互式 API 文档、客户端 SDK、服务器 stub 代码等，从而使开发人员更加容易地开发、测试和部署 API 。<br/><br/>Swagger是一个规范和完整的框架，用于生成、描述、调用和可视化RESTFul风格的web服务，是非常流行的API表达工具。Swagger能够自动生成完善的RESTFul API文档，同时并根据后台代码的修改同步更新，同时提供完整的测试页面来调试API<br/></p>', 'swagger', '2024-09-09 20:09:36', 0, 0, 0, 'Swagger', 'http://localhost:9090/blob/post/cover/7302535d-93c9-451b-acee-879ce4bdb9f2.jpg', '2024-09-09 20:17:38', NULL, 1);
INSERT INTO `post` VALUES ('P1833115888875220992', 'U1805230146664833025', '<p>MyBatis是一款优秀的数据持久ORM框架，被广泛地应用于系统，MyBatis 能够非常灵活地实现动态 SQL，可以使用 XML 或 注解 来配置和映射原生信息，能够轻松地将 JAVA 的 POJO（Plain Ordinary Java Object，普通的Java对象）与数据库中的表和字段进行映射关联。<br/><br/>MyBatis-Plus（简称 MP）是一个 MyBatis 的增强工具，它在 MyBatis 的基础上只做增强不做改变，旨在简化开发和提高效率<br/></p>', 'mybatisp|java', '2024-09-09 20:11:00', 0, 0, 0, 'MyBatis-Plus', 'http://localhost:9090/blob/post/cover/f8bec037-bd65-4817-a295-77a905c64f9c.png', '2024-09-09 20:17:37', NULL, 1);
INSERT INTO `post` VALUES ('P1833116495925227520', 'U1805230146664833025', '<p>1.查看代码历史版本<br/>鼠标在需要查看的java类 右键 找到Local History &gt;&gt; Show History 点开即可看到历史版本，常用于自己忘记代码改了哪些内容 或需要恢复至某个版本 (注意 只能看近期修改 太久了也是看不到的)<br/>2. 调整idea的虚拟内存：<br/>尽管本质都是去改变 .vmoptions配置文件，但推荐使用Change Memory Settings去调整，选择Edit Custom VM Options 或者在本地磁盘目录更改，通过某些方法破解的idea 很可能造成idea打不开的情况<br/>3. idea设置成eclipse的快捷键<br/>这对eclipse转idea的开发人员来说 非常友好，这样不需要记两套快捷键<br/>4. 设置提示词忽略大小写<br/>把这个勾去掉，（有的idea版本是选择选项 选择none即可），例如String 输入string 、String 都可以提示<br/>5. 关闭代码检查<br/>与eclipse类似，idea也可以自己关闭代码检查 减少资源使用，但不推荐全部关闭，（是大佬当我没说），把我们项目中不会使用到的关闭就好了<br/>6. 设置文档注释模板<br/>文档注释快捷键及模板<br/>7.显示方法分隔符<br/>方便查看方法与方法之间的间隔，在代码不规范的项目中 很好用！<br/>8.设置多行tab<br/>idea默认是选择显示单行的，我们把这个去掉，就可以显示多行tab了，在打开tab过多时的场景非常方便！<br/>9. 快速匹配方法的大括号位置<br/>ctrl+[ ctrl+] 可以快速跳转到方法大括号的起止位置，配合方法分隔符使用，不怕找不到方法在哪儿分割了<br/>10.代码结尾补全<br/>例如一行代码补全分号，或者是if(xxx) 补全大括号，按ctrl+shift+enter 无需切换鼠标光标，大幅度提升了编码效率<br/></p>', 'idea|java', '2024-09-09 20:13:25', 1, 1, 0, 'idea一些用法', 'http://localhost:9090/blob/post/cover/0c053576-4727-4993-9577-3b85f90c79f1.jpg', '2024-09-09 20:17:37', NULL, 1);
INSERT INTO `post` VALUES ('P1833122564156284928', 'U1805230146664833025', '<p>什么是maven？<br/>Maven是apache旗下的一个开源项目，是一款用于管理和构建java项目的工具。<br/><br/>官网:maven<br/>功能三个：<br/>依赖管理<br/>方便快捷的管理项目依赖的资源（jar包），避免版本冲突的问题<br/>统一的项目结构<br/>提供标准、统一的项目结构<br/><br/>项目构建<br/>标准跨平台（linux、window、macOS）的自动化项目构建方式<br/>maven三个方面：<br/>项目对象模型（pom.xml）<br/>依赖管理模型(本地仓库)<br/>仓库：用于储存资源，管理各种jar包<br/>本地仓库：自己计算机上的一个目录<br/>中央仓库：由maven团队维护的全球唯一的。仓库地址：<a href=\"https://repo1.maven.org/maven2/\" target=\"_blank\">https://repo1.maven.org/maven2/</a><br/>远程仓库（私服）：一般由公司团队搭建的私有仓库<br/>中央仓库：本地仓库没有的依赖会自动连接国外的中央仓库<br/>远程仓库：是公司的仓库，阿里云的仓库可以使用，会比中央仓库的下载速度要快很多<br/>构建生命周期/阶段（Build lifecycle &amp; phases）<br/>maven的安装：<br/>官网下载的maven文件    官方下载<br/>解压文件zip<br/><br/><br/>配置本地仓库：修改 conf/settings.xml中的&lt;localRepository&gt;为一个指定目录,<br/>&lt;localRepository&gt;E:\\develop apache-maven-3.6.1\\mvn repo&lt;/localRepository&gt;<br/><br/>配置阿里云私服：修改conf/setting.xml中的&lt;mirroes&gt;标签，为其添加如下子标签：<br/><br/>&lt;mirror&gt;<br/>    &lt;id&gt;alimaven&lt;/id&gt;<br/>    &lt;name&gt;aliyun maven&lt;/name&gt;<br/>    &lt;urb&gt;<a href=\"http://maven.aliyun.com/nexus/content/groups/public/</urb>\" target=\"_blank\">http://maven.aliyun.com/nexus/content/groups/public/%3C/urb%3E</a>                <br/>    &lt;mirrorOf&gt;central&lt;/mirrorOf&gt;<br/>&lt;/mirror&gt;<br/>配置环境变量：MAVEN_HOME为maven的解压目录 ，并将其bin目录加入path环境变量中<br/>：MAVEN_HOME为maven的解压目录<br/>将其bin目录加入path环境变量中<br/>验证maven<br/>win+r---&gt;cmd---&gt;mvn -v（需要安装jdk11以上才可以安装maven，不然会报错）<br/><br/>配置Maven环境（当前工程）<br/>选择 IDEA中 File --&gt;Settings --&gt; Build,Execution,Deployment --&gt; Build Tools --&gt; Maven<br/>设置 IDEA 使用本地安装的 Maven，并修改配置文件及本地仓库路径<br/><br/></p>', 'maven|java', '2024-09-09 20:37:32', 1, 1, 0, 'maven', 'http://localhost:9090/blob/post/cover/9d9753f5-c84f-4f99-86fb-065970dd99bc.jpg', '2024-09-09 20:37:51', NULL, 0);
INSERT INTO `post` VALUES ('P1833125494364045312', 'U1805230146664833026', '<p>一、Mybatis<br/>概述<br/>   1、什么是MyBatis?<br/><br/>        MyBatis（官网：<a href=\"https://mybatis.org/mybatis-3/zh/index.html\" target=\"_blank\">https://mybatis.org/mybatis-3/zh/index.html</a> ）是一款优秀的 持久层 框架，用于简化JDBC的开发。是 Apache的一个开源项目iBatis，2010年这个项目由apache迁移到了google code，并且改名为MyBatis 。2013年11月迁移到Github。<br/><br/>MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。<br/><br/>MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。<br/><br/>        持久层：指的是就是数据访问层(dao)，是用来操作数据库的。<br/><br/><br/><br/>        框架：是一个半成品软件，是一套可重用的、通用的、软件基础代码模型。在框架的基础上进行软件开发更加高效、规范、通用、可拓展。<br/><br/>二、快速入门<br/>准备工作<br/>    1、创建springboot工程<br/>创建springboot工程；<br/><br/><br/><br/>导入 mybatis的起步依赖、mysql的驱动包（初次使用，下载依赖时间会有些长，不要着急；如果下载好依然无法打开，请重启Idea）<br/><br/><br/><br/>项目创建完成后，会自动在pom.xml文件中，导入 Mybatis依赖和 MySQL驱动依赖。<br/><br/>        &lt;dependency&gt;<br/>            &lt;groupId&gt;org.mybatis.spring.boot&lt;/groupId&gt;<br/>            &lt;artifactId&gt;mybatis-spring-boot-starter&lt;/artifactId&gt;<br/>            &lt;version&gt;2.2.2&lt;/version&gt;<br/>        &lt;/dependency&gt;<br/> <br/>        &lt;dependency&gt;<br/>            &lt;groupId&gt;com.mysql&lt;/groupId&gt;<br/>            &lt;artifactId&gt;mysql-connector-j&lt;/artifactId&gt;<br/>            &lt;scope&gt;runtime&lt;/scope&gt;<br/>        &lt;/dependency&gt;<br/>2、数据准备<br/>创建用户表user，并创建对应的实体类User。<br/><br/>用户表：<br/><br/>-- 用户表<br/>create table user(<br/>    id int unsigned primary key auto_increment comment \'ID\',<br/>    name varchar(100) comment \'姓名\',<br/>    age tinyint unsigned comment \'年龄\',<br/>    gender tinyint unsigned comment \'性别, 1:男, 2:女\',<br/>    phone varchar(11) comment \'手机号\'<br/>) comment \'用户表\';<br/><br/>添加测试数据：<br/><br/> insert into user(id, name, age, gender, phone) VALUES (null,\'白眉鹰王\',55,\'1\',\'18800000000\');<br/>insert into user(id, name, age, gender, phone) VALUES (null,\'金毛狮王\',45,\'1\',\'18800000001\');<br/>insert into user(id, name, age, gender, phone) VALUES (null,\'青翼蝠王\',38,\'1\',\'18800000002\');<br/>insert into user(id, name, age, gender, phone) VALUES (null,\'紫衫龙王\',42,\'2\',\'18800000003\');<br/>insert into user(id, name, age, gender, phone) VALUES (null,\'光明左使\',37,\'1\',\'18800000004\');<br/>insert into user(id, name, age, gender, phone) VALUES (null,\'光明右使\',48,\'1\',\'18800000005\');<br/><br/>查询数据：<br/><br/><br/><br/>创建实体类：<br/><br/>public class User {<br/>    private Integer id;   //id（主键）<br/>    private String name;  //姓名<br/>    private Short age;    //年龄<br/>    private Short gender; //性别<br/>    private String phone; //手机号<br/>    <br/>    //省略GET, SET方法<br/>}<br/><br/> 配置Mybatis<br/>在之前使用图形化客户端工具，连接MySQL数据库时，需要配置：<br/><br/>连接数据库的四大参数：<br/><br/>MySQL驱动类<br/><br/>登录名<br/><br/>密码<br/><br/>数据库连接字符串<br/><br/><br/><br/> <br/><br/>在springboot项目中，可以编写application.properties文件，配置数据库连接信息。我们要连接数据库，就需要配置数据库连接的基本信息，包括：driver-class-name、url 、username，password。 在入门程序中，大家可以直接这么配置，后面会介绍什么是驱动。<br/><br/>打开resources下的application.properties<br/><br/><br/><br/>然后进行配置；<br/><br/>spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver<br/>spring.datasource.url=jdbc:mysql://localhost:3306/mybatis<br/>spring.datasource.username=root<br/>spring.datasource.password=1234<br/> 编写SQL语句<br/>在创建出来的springboot工程中，在引导类所在包下，在创建一个包 mapper。在mapper包下创建一个接口 UserMapper ，这是一个持久层接口（Mybatis的持久层接口规范一般都叫 XxxMapper）。<br/><br/>UserMapper：<br/><br/>import com.itheima.pojo.User;<br/>import org.apache.ibatis.annotations.Mapper;<br/>import org.apache.ibatis.annotations.Select;<br/>import java.util.List;<br/> <br/>@Mapper<br/>public interface UserMapper {<br/>    <br/>    //查询所有用户数据<br/>    @Select(\"select id, name, age, gender, phone from user\")<br/>    public List&lt;User&gt; list();<br/>    <br/>}<br/>@Mapper注解：表示是mybatis中的Mapper接口<br/><br/>程序运行时：框架会自动生成接口的实现类对象(代理对象)，并给交Spring的IOC容器管理<br/><br/>@Select注解：代表的就是select查询，用于书写select查询语句<br/><br/> 单元测试<br/>在创建出来的SpringBoot工程中，在src下的test目录下，已经自动帮我们创建好了测试类 ，并且在测试类上已经添加了注解 @SpringBootTest，代表该测试类已经与SpringBoot整合。<br/><br/>该测试类在运行时，会自动通过引导类加载Spring的环境（IOC容器）。我们要测试那个bean对象，就可以直接通过@Autowired注解直接将其注入进行，然后就可以测试了。<br/><br/>测试类代码如下：<br/><br/>@SpringBootTest<br/>public class MybatisQuickstartApplicationTests {<br/>	<br/>    @Autowired<br/>    private UserMapper userMapper;<br/>	<br/>    @Test<br/>    public void testList(){<br/>        List&lt;User&gt; userList = userMapper.list();<br/>        for (User user : userList) {<br/>            System.out.println(user);<br/>        }<br/>    }<br/> <br/>}<br/> 运行结果：<br/><br/>User{id=1, name=\'白眉鹰王\', age=55, gender=1, phone=\'18800000000\'}<br/>User{id=2, name=\'金毛狮王\', age=45, gender=1, phone=\'18800000001\'}<br/>User{id=3, name=\'青翼蝠王\', age=38, gender=1, phone=\'18800000002\'}<br/>User{id=4, name=\'紫衫龙王\', age=42, gender=2, phone=\'18800000003\'}<br/>User{id=5, name=\'光明左使\', age=37, gender=1, phone=\'18800000004\'}<br/>User{id=6, name=\'光明右使\', age=48, gender=1, phone=\'18800000005\'}<br/><br/>打开SQL提示和警告<br/>默认我们在UserMapper接口上加的@Select注解中编写SQL语句是没有提示的。 如果想让idea给我们提示对应的SQL语句，我们需要在IDEA中配置与MySQL数据库的链接。 默认我们在UserMapper接口上的@Select注解中编写SQL语句是没有提示的。如果想让idea给出提示，可以做如下配置：<br/><br/><br/><br/> 配置完成之后，发现SQL语句中的关键字有提示了.<br/><br/></p>', 'mybatis|java', '2024-09-09 20:49:10', 0, 1, 0, 'Mybatis', 'http://localhost:9090/blob/post/cover/b3be6cbd-0b07-41f9-825b-30f4340bc6f6.jpg', '2024-09-09 20:57:44', NULL, 0);
INSERT INTO `post` VALUES ('P1833125759431475200', 'U1805230146664833026', '<p>1.MyBatis介绍 <br/>1.1Mybatis是什么<br/>        MyBatis 是一款优秀的持久层框架，它支持自定义 SQL、存储过程以及高级映射。MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。<br/><br/>1.2Mybatis解决的问题<br/>        实际上MyBatis就是对JDBC优化的产物，正是因为JDBC不太好用所以才对其进行优化。<br/>以上是一个JDBC的一个过程程序，从程序中可以看出在JDBC中有几个问题：<br/><br/>JDBC需要手动创建和释放链接<br/><br/>JDBC中sql语句在代码中硬编码<br/><br/>JDBC对结果的解析在遍历的时候太过麻烦<br/><br/>        MyBatis是一个优秀的持久层框架，它对jdbc的操作数据库的过程进行封装，使开发者只需要关注 SQL 本身，而不需要花费精力去处理例如注册驱动、创建connection、创建statement、手动设置参数、获取结果集等jdbc繁杂的过程代码。<br/><br/>        MyBatis 本是apache的一个开源项目iBatis, 2010年这个项目由apache software foundation 迁移到了google code，并且改名为MyBatis。iBatis一词来源于“internet”和“abatis”的组合，是一个基于Java的持久层框架。<br/><br/>2.MyBatis入门案例<br/>2.1 准备资料<br/>        首先要在navicat中创建数据库和数据表，下面已经给了链接，把文件下载后，可以拉到数据库的表中，直接生成表，链接永久有效。<br/><br/>        链接：<a href=\"https://pan.baidu.com/s/19GLi15tyXPgLrqUjvclrLA?pwd=1123\" target=\"_blank\">https://pan.baidu.com/s/19GLi15tyXPgLrqUjvclrLA?pwd=1123</a> <br/>        提取码：1123<br/></p>', 'mybatis|java|编程教程', '2024-09-09 20:50:14', 0, 0, 0, 'mybatis学习教程', 'http://localhost:9090/blob/post/cover/9c728b95-84fc-4942-93cb-1dbf9d447c4b.jpg', '2024-09-09 20:57:43', NULL, 0);
INSERT INTO `post` VALUES ('P1833126084854939648', 'U1805230146664833026', '<p>1.3.黏包半包现象分析<br/>黏包<br/>现象<br/>发送 abc def 接收 abdcef<br/>原因<br/>应用层：接收方ByteBuf设置太大（Netty默认1024）<br/>滑动窗口：假设发送方 256 bytes 表示一个完整报文，但是由于接收方处理不及时，且窗口大小足够大，这256 bytes 字节就会缓冲在接收方的滑动窗口中，当滑动窗口缓冲了多个报文就会黏包<br/>Nagle算法：会造成黏包<br/>半包<br/>现象：发送 abcefg 接收方 abc efg<br/>原因<br/>应用层：接收方ByteBuf 设置容量大小，小于实际发送的数据量<br/>滑动窗口：假设接收方的窗口只剩下了，128byte，发送方的报文大小是 256 byte，这时就会放不下，只能先发送 128 byte数据，然后等待ack确认后，才能发送剩下的部门，这时就造成了半包。<br/>MSS限制：当发送的数据超过了MSS的限制后，会将数据切割，然后分批发送，就会造成半包<br/>为什么在数据传输截断存在数据分割呢？一个TCP报文的有效数据（净荷数据）是有大小容量限制的，这个报文有效数据的大小就被称为**MSS（Mixinum Segment Size） 最大报文字段长度**。具体MSS的值会在三次握手阶段进行协商，但是最大长度不会超过**1460**个字节<br/>出现黏包半包的主要原因就是 TCP的消息没有边界<br/><br/></p>', 'netty|java|网络编程', '2024-09-09 20:51:31', 0, 0, 0, 'Netty学习方法', 'http://localhost:9090/blob/post/cover/7e585e0d-cd55-449b-8a53-64f06998f7b1.png', '2024-09-09 20:57:42', NULL, 0);
INSERT INTO `post` VALUES ('P1833126238056087552', 'U1805230146664833026', '<p>一：Netty初探<br/>NIO 的类库和 API 繁杂， 使用麻烦： 需要熟练掌握Selector、 ServerSocketChannel、 SocketChannel、 ByteBuffer等。<br/><br/>开发工作量和难度都非常大： 例如客户端面临断线重连、 网络闪断、心跳处理、半包读写、 网络拥塞和异常流的处理等等。<br/><br/>Netty 对 JDK 自带的 NIO 的 API 进行了良好的封装，解决了上述问题。且Netty拥有高性能、 吞吐量更高，延迟更低，减少资源消耗，最小化不必要的内存复制等优点。<br/><br/>Netty 现在都在用的是4.x，5.x版本已经废弃，Netty 4.x 需要JDK 6以上版本支持<br/><br/>二：Netty的使用场景：<br/>1）互联网行业：在分布式系统中，各个节点之间需要远程服务调用，高性能的 RPC 框架必不可少，Netty 作为异步高性能的通信框架，往往作为基础通信组件被这些 RPC 框架使用。典型的应用有：阿里分布式服务框架 Dubbo 的 RPC 框架使用 Dubbo 协议进行节点间通信，Dubbo 协议默认使用 Netty 作为基础通信组件，用于实现。各进程节点之间的内部通信。Rocketmq底层也是用的Netty作为基础通信组件。<br/><br/>2）游戏行业：无论是手游服务端还是大型的网络游戏，Java 语言得到了越来越广泛的应用。Netty 作为高性能的基础通信组件，它本身提供了 TCP/UDP 和 HTTP 协议栈。<br/><br/>3）大数据领域：经典的 Hadoop 的高性能通信和序列化组件 Avro 的 RPC 框架，默认采用 Netty 进行跨界点通信，它的 Netty Service 基于 Netty 框架二次封装实现。<br/><br/></p>', 'netty|java|网络编程', '2024-09-09 20:52:08', 2, 1, 0, 'netty', 'http://localhost:9090/blob/post/cover/fbfad782-e42c-45a7-8337-3231bb27b9ef.png', '2024-09-09 20:57:42', NULL, 1);
INSERT INTO `post` VALUES ('P1833126644513505280', 'U1805230146664833026', '<p>1.1 为什么要学Elasticsearch<br/>为什么要学Elasticsearch(ES)<br/><br/>我想这是一个存留于很多初学者内心比较困惑的问题。<br/><br/>但是在回答之前，我们还需要先大致了解下它是啥。<br/><br/>毕竟如果一个技术你都不了解它是啥，何谈为什么要学它？<br/><br/>那么, 什么是ES呢？<br/><br/>Elasticsearch 简称ES, 经常与Logstash 和Kibana 一起使用，江湖人称ELK.<br/><br/>这E 自然指的就是Elasticsearch,简称ES, 具有分布式存储,搜索和分析的功能。<br/>L 指的就是Logstash,分布式日志收集框架<br/>K 指的就是Kibana,可视化分析框架。<br/>接下来我们聊聊为什么我们要学传说中的ES。<br/><br/>这个问题的本质其实是ES 可以做啥？回答清楚这个问题，问题的答案自然就有了。<br/><br/>我翻开了官方文档，在网络中流浪，终于寻找到了答案。<br/><br/>其中分享一些经典的使用案例如下：<br/><br/>使用案例一：<br/>ELK 结合使用，用于微服务架构下不同机器上微服务的日志聚合，日志分析。<br/>使用案例二：<br/>当我们打开淘宝，京东，等电商网站的时候，尝试输入一些关键词，然后系统就会给我们提供一些搜索建议。<br/>这种场景其实也是ES 使用的一个经典案例。<br/>使用案例三：<br/>Github使用Elasticsearch检索1300亿行的代码<br/>使用案例四:<br/>维基百科使用Elasticsearch提供全文搜索并高亮关键字，以及输入实时搜索(search-as-you-type)和搜索纠错(did-you-mean)等搜索建议功能。<br/>使用案例五:<br/>StackOverflow结合全文搜索与地理位置查询，以及more-like-this功能来找到相关的问题和答案<br/>1.2 如何下载安装使用ES<br/>ES 官网: <a href=\"https://www.elastic.co/cn/\" target=\"_blank\">https://www.elastic.co/cn/</a><br/></p>', 'es|java', '2024-09-09 20:53:45', 0, 0, 0, 'Elasticsearch', 'http://localhost:9090/blob/post/cover/2cbb0006-7452-4f6f-a27b-a45c5e31b36f.jpg', '2024-09-09 20:57:41', NULL, 0);
INSERT INTO `post` VALUES ('P1833127106142797824', 'U1805230146664833026', '<p>一.分布式集群<br/>1.1单节点集群<br/>我们在包含一个空节点的集群内创建名为 users 的索引，为了演示目的，我们将分配 3个主分片和一份副本（每个主分片拥有一个副本分片）<br/></p><br/><p><br/></p><pre><code class=\"JSON\">{\r\n　　　　\"settings\" : {\r\n　　　　　　　　\"number_of_shards\" : 3,\r\n　　　　　　　　\"number_of_replicas\" : 1\r\n　　　　}\r\n}</code></pre><p>此时我们的集群现在是拥有一个索引的单节点集群,所有 3 个主分片都被分配在 node-1 。<br/><br/>这里我们可以通过ES的插进elasticsearch-head查看集群情况<br/><br/><br/>集群健康值:yellow( 7 of 14 ) : 表示当前集群的全部主分片都正常运行，但是副本分片没有全部处在正常状态,3个副本分片都是 Unassigned----它们都没有被分配到任何节点。 在同一个节点上既保存原始数据又保存副本是没有意义的，因为一旦失去了那个节点，我们也将丢失该节点上的所有副本数据。此时我们的集群是正常运行的，但是在硬件故障时有丢失数据的风险.<br/><br/>1.2故障转移<br/>当集群中只有一个节点在运行时，意味着会有一个单点故障问题——没有冗余。 幸运的是，我们只需再启动一个节点即可防止数据丢失。当你在同一台机器上启动了第二个节点时，只要它和第一个节点有同样的 cluster.name 配置，它就会自动发现集群并加入到其中。但是在不同机器上启动节点的时候，为了加入到同一集群，你需要配置一个可连接到的单播主机列表。之所以配置为单播发现，为了防止节点无意中加入集群。<br/><br/>如果启动了第二个节点，我们的集群将会拥有两个节点的集群 : 所有主分片和副本分片都已被分配<br/><br/><br/><br/>通过 elasticsearch-head 插件查看集群情况<br/><br/><br/><br/><br/><br/>1.3水平扩容<br/>怎样为我们的正在增长中的应用程序按需扩容呢？当启动了第三个节点，我们的集群将会拥有三个节点的集群 : 为了分散负载而对分片进行重新分配<br/><br/><br/><br/>通过 elasticsearch-head 插件查看集群情况<br/><br/><br/><br/><br/><br/>但是如果我们想要扩容超过 6 个节点怎么办呢？<br/><br/>主分片的数目在索引创建时就已经确定了下来。实际上，这个数目定义了这个索引能够存储的最大数据量。（实际大小取决于你的数据、硬件和使用场景。） 但是，读操作——搜索和返回数据——可以同时被主分片或副本分片所处理，所以当你拥有越多的副本分片时，也将拥有越高的吞吐量。<br/><br/>在运行中的集群上是可以动态调整副本分片数目的，我们可以按需伸缩集群。让我们把副本数从默认的 1 增加到 2<br/><br/>{<br/> \"number_of_replicas\" : 2<br/>}<br/>1<br/>2<br/>3<br/><br/><br/>users 索引现在拥有 9 个分片：3 个主分片和 6 个副本分片。 这意味着我们可以将集群扩容到 9 个节点，每个节点上一个分片。相比原来 3 个节点时，集群搜索性能可以提升 3 倍。<br/><br/><br/><br/>通过 elasticsearch-head 插件查看集群情况<br/><br/><br/><br/>当然，如果只是在相同节点数目的集群上增加更多的副本分片并不能提高性能，因为每个分片从节点上获得的资源会变少。 你需要增加更多的硬件资源来提升吞吐量。但是更多的副本分片数提高了数据冗余量：按照上面的节点配置，我们可以在失去 2 个节点的情况下不丢失任何数据。<br/><br/></p>', 'es|java|搜索数据库', '2024-09-09 20:55:35', 0, 0, 0, 'es学习方法', 'http://localhost:9090/blob/post/cover/b5ef9a82-485c-4e1c-a311-0ed07e99769c.jpg', '2024-09-09 20:57:40', NULL, 0);
INSERT INTO `post` VALUES ('P1833127507709657088', 'U1805230146664833026', '<p>1.1 服务注册到Nacos<br/>1.1.1 配置<br/>**引入com.alibaba.cloud，**以后的版本就不用操心了<br/><br/>&lt;!-- com.alibaba.cloud--&gt;<br/>            &lt;!-- com.alibaba.cloud--&gt;<br/>            &lt;dependency&gt;<br/>                &lt;groupId&gt;com.alibaba.cloud&lt;/groupId&gt;<br/>                &lt;artifactId&gt;spring-cloud-alibaba-dependencies&lt;/artifactId&gt;<br/>                &lt;version&gt;2.2.6.RELEASE&lt;/version&gt;<br/>                &lt;type&gt;pom&lt;/type&gt;<br/>                &lt;scope&gt;import&lt;/scope&gt;<br/>            &lt;/dependency&gt;<br/>nacos的管理依赖<br/><br/>&lt;!--nacos的管理依赖 --&gt;<br/>&lt;dependency&gt;<br/>    &lt;groupId&gt;com.alibaba.cloud&lt;/groupId&gt;<br/>    &lt;artifactId&gt;spring-cloud-alibaba-dependencies&lt;/artifactId&gt;<br/>    &lt;version&gt;2.2.5.RELEASE&lt;/version&gt;<br/>    &lt;type&gt;pom&lt;/type&gt;<br/>    &lt;scope&gt;import&lt;/scope&gt;<br/>&lt;/dependency&gt;<br/>注释掉order-service和user-service中原有的eureka依赖<br/><br/>添加nacos客户端依赖<br/><br/>在user-service及order-service中添加<br/><br/>服务注册发现的客户端依赖，一个启动器starter<br/><br/>&lt;!-- nacos客户端依赖 --&gt;<br/>&lt;dependency&gt;<br/>    &lt;groupId&gt;com.alibaba.cloud&lt;/groupId&gt;<br/>    &lt;artifactId&gt;spring-cloud-starter-alibaba-nacos-discovery&lt;/artifactId&gt;<br/>&lt;/dependency&gt;<br/>修改user-service&amp;order-service中的application.yml文件，注释eureka地址，添加nacos地址<br/><br/>spring:<br/>  application:<br/>    name: orderservice<br/>  cloud:<br/>    nacos:<br/>    #nacos服务端地址，默认就是8848<br/>    server-addr: localhost:8848<br/><br/></p>', 'nacos|java|阿里巴巴', '2024-09-09 20:57:10', 0, 0, 0, 'nacos', 'http://localhost:9090/blob/post/cover/0f4bcb68-ecb6-4eea-89eb-a3c4df424ae8.jpg', '2024-09-09 20:57:39', NULL, 0);
INSERT INTO `post` VALUES ('P1833392443333648384', 'U1833388335084183552', '<p>数据库相关概念<br/><br/><br/>MySQL百度百科<br/>MySQL是一个关系型数据库管理系统，由瑞典MySQL AB 公司开发，属于 Oracle 旗下产品。MySQL 是最流行的关系型数据库管理系统之一，在 WEB 应用方面，MySQL是最好的 RDBMS (Relational Database Management System，关系数据库管理系统) 应用软件之一<br/><br/>注意：MySQL究其本质就是一个管理系统，管理关系型数据库的应用软件<br/><br/>Windows下安装MySQL<br/><br/>MySQL数据类型<br/>数值类型<br/><br/><br/><br/><br/>字符串类型<br/><br/><br/><br/>日期类型<br/><br/><br/><br/>SQL<br/>全称 Structured Query Language，结构化查询语言。操作关系型数据库的编程语言，定义了 一套操作关系型数据库统一标准<br/><br/>SQL通用语法<br/>在学习具体的SQL语句之前，先来了解一下SQL语言的同于语法。<br/>1). SQL语句可以单行或多行书写，以分号结尾。<br/>2). SQL语句可以使用空格/缩进来增强语句的可读性。<br/>3). MySQL数据库的SQL语句不区分大小写，关键字建议使用大写<br/>4). 注释：单行注释：-- 注释内容 或 # 注释内容 多行注释：/* 注 释内容 */<br/><br/>SQL分类<br/><br/><br/>DDL<br/># 查询所有数据库<br/>SHOW DATABASES;<br/># 查看当前数据库<br/>SELECT DATABASE();<br/># 创建数据库 方括号内是可选参数<br/># 标准语法 create database [ if not exists ] 数据库名 [ default charset 字符集 ] [ collate 排序规则 ] ;<br/>create database itcast;<br/># 删除数据库 drop database [ if exists ] 数据库名 ; if exists 代表数据库存在在删除<br/>DROP DATABASE IF EXISTS teste;<br/># 切换数据库 use 数据库名<br/>use test<br/>对表定义操作<br/># 查询当前数据库所有表<br/>show tables;<br/># 查询表结构 desc 表名<br/>desc test;<br/># 查看创建表语句 SHOW CREATE TABLE 表名<br/>SHOW CREATE TABLE tb_blog;<br/># 创建表<br/>/*<br/>CREATE TABLE 表名(<br/>字段1 字段1类型 [ COMMENT 字段1注释 ],<br/>字段2 字段2类型 [COMMENT 字段2注释 ],<br/>字段3 字段3类型 [COMMENT 字段3注释 ],<br/>......<br/>字段n 字段n类型 [COMMENT 字段n注释 ]<br/>) [ COMMENT 表注释 ] ;<br/>*/<br/># 创建tb_blog表<br/>CREATE TABLE `tb_blog` (<br/>`id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT \'主键\',<br/>`shop_id` bigint NOT NULL COMMENT \'商户id\',<br/>`user_id` bigint unsigned NOT NULL COMMENT \'用户id\',<br/>`title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT \'标题\',<br/>`images` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT \'探店的照片，最多9张，多张以\",\"隔开\',<br/>`content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT \'探店的文字描述\',<br/>`liked` int unsigned DEFAULT \'0\' COMMENT \'点赞数量\',<br/>`comments` int unsigned DEFAULT NULL COMMENT \'评论数量\',<br/>`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT \'创建时间\',<br/>`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT \'更新时间\',<br/>PRIMARY KEY (`id`) USING BTREE<br/>) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT<br/># 修改字段 ALTER TABLE 表名 ADD 字段名 类型 (长度) [ COMMENT 注释 ] [ 约束 ];<br/>ALTER TABLE emp ADD nickname varchar(20) COMMENT \'昵称\';<br/># 修改数据库类型<br/>ALTER TABLE emp MODIMY 字段名 新数据类型 （长度）<br/># 修改数据类型和字段名称<br/>ALTER TABLE 表名 CHANGE 旧字段名 新字段名 新类型 (长度) [ COMMENT 注释 ] [ 约束 ];<br/># 删除字段<br/>ALTER TABLE 表名 drop 字段名称<br/>#　修改表名<br/>ALTER TABLE 表名 ＲＥＮＡＭＥ　ＴＯ　新表名<br/>#　删除表<br/>ＤＲＯＰ　TABLE　表名　或　TRUNCATE TABLE 表名;<br/>＃　TRUNCATE 删除后创建新表<br/>ＤＭＬ<br/><br/>数据操作语言，对数据库表中数据进行增删改操作。　　　　　　　　　<br/>１）添加数据【ＩＮＳＥＲＴ】<br/>２）修改数据【ＵＰＤＡＴＥ】<br/>３）删除数据【ＤＥＬＥＴＥ】<br/><br/>添加数据<br/><br/><br/><br/>修改数据<br/><br/><br/><br/>删除数据<br/><br/><br/><br/>ＤＱＬ<br/><br/>数据查询语言，用来查询数据库表中记录。关键字ＳＥＬＥＣＴ<br/><br/>图片<br/>PART01<br/><br/>语法<br/><br/>语法<br/>/*<br/>SELECT<br/>字段列表<br/>FROM<br/>表名列表<br/>WHERE<br/>条件列表<br/>GROUP BY<br/>分组字段列表<br/>HAVING<br/>分组后条件列表<br/>ORDER BY<br/>排序字段列表<br/>LIMIT<br/>分页参数<br/>*/<br/><br/>基础查询<br/><br/># 查询多个字段语法 SELECT 字段1, 字段2, 字段3 ... FROM 表名 ;<br/># 查询所有字段语法 SELECT * FROM 表名 ;<br/># 例<br/>SELECT name,age FROM emp;<br/>SELECT * FROM emp;<br/># * 代表查询所有字段，影响效率，开发中不推荐使用<br/># 设置别名语法 SELECT 字段1 [ AS 别名1 ] , 字段2 [ AS 别名2 ] ... FROM 表名;<br/># 简写形式 SELECT 字段1 [ 别名1 ] , 字段2 [ 别名2 ] ... FROM 表名;<br/># 例<br/>SELECT name AS ename,workno no,age FROM emp;<br/><br/># 去除重复记录语法 SELECT DISTINCT 字段列表 FROM 表名;<br/># 例<br/>select distinct workaddress \'工作地址\',dep_id from emp;<br/># 注：distinct是对当前记录去重，当distinct后面跟多个字段时，去重条件为这几个字段的值<br/>条件拆查询<br/>语法<br/>SELECT 字段列表 FROM 表名 WHERE 条件列表 ;<br/><br/>案例<br/><br/># 查询年龄等于 88 的员工<br/>select * from emp where age = 88;<br/># 查询年龄小于 20 的员工信息<br/>select * from emp where age &lt; 20;<br/># 查询年龄小于等于 20 的员工信息<br/>select * from emp where age &lt;= 20;<br/># 查询没有身份证号的员工信息<br/>select * from emp where idcard is null;<br/># 查询有身份证号的员工信息<br/>select * from emp where idcard is not null;<br/># 查询年龄不等于 88 的员工信息<br/>select * from emp where age != 88;<br/># 查询年龄在15岁(包含) 到 20岁(包含)之间的员工信息<br/>select * from emp where age &gt;= 15 &amp;&amp; age &lt;= 20;<br/># 查询性别为 女 且年龄小于 25岁的员工信息<br/>select * from emp where gender = \'女\' and age &lt; 25;<br/># 查询年龄等于18 或 20 或 40 的员工信息<br/>select * from emp where age = 18 or age = 20 or age =40;<br/>select * from emp where age in(18,20,40);<br/># 查询姓名为两个字的员工信息 _ %<br/>select * from emp where name like \'__\';<br/># 查询身份证号最后一位是X的员工信息<br/>select * from emp where idcard like \'%X\';<br/>select * from emp where idcard like \'_________________X\';<br/>聚合函数<br/>语法<br/>SELECT 聚合函数(字段列表) FROM 表名 ;<br/>常见聚合函数</p>', 'mysql|java|数据库', '2024-09-10 14:29:56', 0, 0, 0, 'MySQL基础入门教程', 'http://localhost:9090/blob/post/cover/4ae1511b-a067-45d2-b5b2-2584dd11155b.jpg', '2024-09-10 14:32:38', NULL, 1);
INSERT INTO `post` VALUES ('P1833392487675830272', 'U1833388335084183552', '<p>数据库相关概念<br/><br/><br/>MySQL百度百科<br/> MySQL是一个关系型数据库管理系统，由瑞典MySQL AB 公司开发，属于 Oracle 旗下产品。MySQL 是最流行的关系型数据库管理系统之一，在 WEB 应用方面，MySQL是最好的 RDBMS (Relational Database Management System，关系数据库管理系统) 应用软件之一<br/><br/>注意：MySQL究其本质就是一个管理系统，管理关系型数据库的应用软件<br/><br/>Windows下安装MySQL<br/><br/>MySQL数据类型<br/>数值类型<br/><br/><br/><br/><br/>字符串类型<br/><br/><br/><br/>日期类型<br/><br/><br/><br/>SQL<br/>全称 Structured Query Language，结构化查询语言。操作关系型数据库的编程语言，定义了 一套操作关系型数据库统一标准<br/><br/>SQL通用语法<br/>在学习具体的SQL语句之前，先来了解一下SQL语言的同于语法。<br/>1). SQL语句可以单行或多行书写，以分号结尾。<br/>2). SQL语句可以使用空格/缩进来增强语句的可读性。<br/>3). MySQL数据库的SQL语句不区分大小写，关键字建议使用大写<br/>4). 注释：单行注释：-- 注释内容 或 # 注释内容 多行注释：/* 注 释内容 */<br/><br/>SQL分类<br/><br/><br/>DDL<br/># 查询所有数据库<br/>SHOW DATABASES;<br/># 查看当前数据库<br/>SELECT DATABASE();<br/># 创建数据库 方括号内是可选参数<br/># 标准语法 create database [ if not exists ] 数据库名 [ default charset 字符集 ] [ collate 排序规则 ] ;<br/>create database itcast;<br/># 删除数据库 drop database [ if exists ] 数据库名 ; if exists 代表数据库存在在删除<br/>DROP DATABASE IF EXISTS teste;<br/># 切换数据库 use 数据库名<br/>use test<br/>对表定义操作<br/># 查询当前数据库所有表<br/>show tables;<br/># 查询表结构 desc 表名<br/>desc test;<br/># 查看创建表语句 SHOW CREATE TABLE 表名<br/>SHOW CREATE TABLE tb_blog;<br/># 创建表<br/>/* <br/>CREATE TABLE 表名(<br/>字段1 字段1类型 [ COMMENT 字段1注释 ],<br/>字段2 字段2类型 [COMMENT 字段2注释 ],<br/>字段3 字段3类型 [COMMENT 字段3注释 ],<br/>......<br/>字段n 字段n类型 [COMMENT 字段n注释 ]<br/>) [ COMMENT 表注释 ] ; <br/>*/<br/># 创建tb_blog表<br/>CREATE TABLE `tb_blog` (<br/>  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT \'主键\',<br/>  `shop_id` bigint NOT NULL COMMENT \'商户id\',<br/>  `user_id` bigint unsigned NOT NULL COMMENT \'用户id\',<br/>  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT \'标题\',<br/>  `images` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT \'探店的照片，最多9张，多张以\",\"隔开\',<br/>  `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT \'探店的文字描述\',<br/>  `liked` int unsigned DEFAULT \'0\' COMMENT \'点赞数量\',<br/>  `comments` int unsigned DEFAULT NULL COMMENT \'评论数量\',<br/>  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT \'创建时间\',<br/>  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT \'更新时间\',<br/>  PRIMARY KEY (`id`) USING BTREE<br/>) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT<br/># 修改字段  ALTER TABLE 表名 ADD 字段名 类型 (长度) [ COMMENT 注释 ] [ 约束 ];<br/>ALTER TABLE emp ADD nickname varchar(20) COMMENT \'昵称\';<br/># 修改数据库类型 <br/>ALTER TABLE emp MODIMY 字段名 新数据类型 （长度）<br/># 修改数据类型和字段名称<br/>ALTER TABLE 表名 CHANGE 旧字段名 新字段名 新类型 (长度) [ COMMENT 注释 ] [ 约束 ];<br/># 删除字段<br/>ALTER TABLE 表名 drop 字段名称<br/>#　修改表名<br/>ALTER TABLE 表名 ＲＥＮＡＭＥ　ＴＯ　新表名<br/>#　删除表<br/>ＤＲＯＰ　TABLE　表名　或　TRUNCATE TABLE 表名;<br/>＃　TRUNCATE 删除后创建新表<br/>ＤＭＬ<br/><br/>数据操作语言，对数据库表中数据进行增删改操作。　　　　　　　　　<br/>１）添加数据【ＩＮＳＥＲＴ】<br/>２）修改数据【ＵＰＤＡＴＥ】<br/>３）删除数据【ＤＥＬＥＴＥ】<br/><br/>添加数据<br/><br/><br/><br/>修改数据<br/><br/><br/><br/>删除数据<br/><br/><br/><br/>ＤＱＬ<br/><br/>数据查询语言，用来查询数据库表中记录。关键字ＳＥＬＥＣＴ<br/><br/>图片<br/>PART01<br/><br/>语法<br/><br/>语法<br/>/*<br/>SELECT<br/>字段列表<br/>FROM<br/>表名列表<br/>WHERE<br/>条件列表<br/>GROUP BY<br/>分组字段列表<br/>HAVING<br/>分组后条件列表<br/>ORDER BY<br/>排序字段列表<br/>LIMIT<br/>分页参数<br/>*/<br/><br/>基础查询<br/><br/># 查询多个字段语法 SELECT 字段1, 字段2, 字段3 ... FROM 表名 ;<br/># 查询所有字段语法 SELECT * FROM 表名 ;<br/># 例<br/>SELECT name,age FROM emp;<br/>SELECT * FROM emp;<br/># * 代表查询所有字段，影响效率，开发中不推荐使用<br/># 设置别名语法 SELECT 字段1 [ AS 别名1 ] , 字段2 [ AS 别名2 ] ... FROM 表名;<br/># 简写形式  SELECT 字段1 [ 别名1 ] , 字段2 [ 别名2 ] ... FROM 表名;<br/># 例<br/>SELECT name AS ename,workno no,age FROM emp;<br/><br/># 去除重复记录语法 SELECT DISTINCT 字段列表 FROM 表名;<br/># 例<br/>select distinct workaddress \'工作地址\',dep_id from emp;<br/># 注：distinct是对当前记录去重，当distinct后面跟多个字段时，去重条件为这几个字段的值<br/>条件拆查询<br/>语法<br/>SELECT 字段列表 FROM 表名 WHERE 条件列表 ;<br/><br/>案例<br/><br/># 查询年龄等于 88 的员工<br/>select * from emp where age = 88;<br/># 查询年龄小于 20 的员工信息<br/>select * from emp where age &lt; 20;<br/># 查询年龄小于等于 20 的员工信息<br/>select * from emp where age &lt;= 20;<br/># 查询没有身份证号的员工信息<br/>select * from emp where idcard is null;<br/># 查询有身份证号的员工信息<br/>select * from emp where idcard is not null;<br/># 查询年龄不等于 88 的员工信息<br/>select * from emp where age != 88;<br/># 查询年龄在15岁(包含) 到 20岁(包含)之间的员工信息<br/>select * from emp where age &gt;= 15 &amp;&amp; age &lt;= 20;<br/># 查询性别为 女 且年龄小于 25岁的员工信息<br/>select * from emp where gender = \'女\' and age &lt; 25;<br/># 查询年龄等于18 或 20 或 40 的员工信息<br/>select * from emp where age = 18 or age = 20 or age =40;<br/>select * from emp where age in(18,20,40);<br/># 查询姓名为两个字的员工信息 _ %<br/>select * from emp where name like \'__\';<br/># 查询身份证号最后一位是X的员工信息<br/>select * from emp where idcard like \'%X\';<br/>select * from emp where idcard like \'_________________X\';<br/>聚合函数<br/>语法<br/>SELECT 聚合函数(字段列表) FROM 表名 ;<br/>常见聚合函数<br/></p>', 'mysql|java|数据库', '2024-09-10 14:30:07', 0, 0, 0, 'MySQL教程', 'http://localhost:9090/blob/img/background.png', '2024-09-10 14:32:37', NULL, 0);
INSERT INTO `post` VALUES ('P1833392923187191808', 'U1833388335084183552', '<p>2.1 五种常用数据类型介绍<br/>Redis存储的是key-value结构的数据，其中key是字符串类型，value有5种常用的数据类型：<br/><br/>字符串 string<br/>哈希 hash<br/>列表 list<br/>集合 set<br/>有序集合 sorted set / zset<br/>2.2 各种数据类型特点<br/><br/>解释说明：<br/><br/>字符串(string)：普通字符串，Redis中最简单的数据类型<br/>哈希(hash)：也叫散列，类似于Java中的HashMap结构<br/>列表(list)：按照插入顺序排序，可以有重复元素，类似于Java中的LinkedList<br/>集合(set)：无序集合，没有重复元素，类似于Java中的HashSet<br/>有序集合(sorted set/zset)：集合中每个元素关联一个分数(score)，根据分数升序排序，没有重复元素<br/>3. Redis常用命令<br/>3.1 字符串操作命令<br/>Redis 中字符串类型常用命令：<br/><br/>SET key value 设置指定key的值<br/>GET key 获取指定key的值<br/>SETEX key seconds value 设置指定key的值，并将 key 的过期时间设为 seconds 秒<br/>SETNX key value 只有在 key 不存在时设置 key 的值<br/>更多命令可以参考Redis中文网：<a href=\"https://www.redis.net.cn\" target=\"_blank\">https://www.redis.net.cn/</a><br/><br/>3.2 哈希操作命令<br/>Redis hash 是一个string类型的 field 和 value 的映射表，hash特别适合用于存储对象，常用命令：<br/><br/>HSET key field value 将哈希表 key 中的字段 field 的值设为 value<br/>HGET key field 获取存储在哈希表中指定字段的值<br/>HDEL key field 删除存储在哈希表中的指定字段<br/>HKEYS key 获取哈希表中所有字段<br/>HVALS key 获取哈希表中所有值<br/><br/><br/></p>', 'redis|java|nosql', '2024-09-10 14:31:50', 0, 0, 0, 'Redis基础教程', 'http://localhost:9090/blob/post/cover/45d0f78f-c34f-40c9-9c4e-f3f553dc5c3d.jpg', '2024-09-10 14:32:37', NULL, 1);
INSERT INTO `post` VALUES ('P1833393326763122688', 'U1805198690294980608', '<p>test</p>', 'test', '2024-09-10 14:33:27', 0, 0, 0, 'test', 'http://localhost:9090/blob/img/background.png', '2024-09-10 14:35:28', NULL, 0);
INSERT INTO `post` VALUES ('P1833393366076334080', 'U1805198690294980608', '<p>test</p>', 'test', '2024-09-10 14:33:36', 0, 0, 0, 'test2', 'http://localhost:9090/blob/img/background.png', '2024-09-10 14:35:28', NULL, 1);
INSERT INTO `post` VALUES ('P1833393728279650304', 'U1805198690294980608', '<p>景色</p>', 'java', '2024-09-10 14:35:02', 0, 0, 0, '景色', 'http://localhost:9090/blob/post/cover/4bb4a614-eee7-4c1f-9f5a-2fa65d26c57c.jpg', '2024-09-10 14:35:27', NULL, 1);
INSERT INTO `post` VALUES ('P1833393798760734720', 'U1805198690294980608', '<p>景色</p>', '景色', '2024-09-10 14:35:19', 0, 0, 0, '景色', 'http://localhost:9090/blob/post/cover/c096e070-ada4-4d03-8062-90c952cf6490.jpg', '2024-09-10 14:35:27', NULL, 0);
INSERT INTO `post` VALUES ('P1833415726728839168', 'U1805230146664833025', '<p>😇<br/></p>', 'test', '2024-09-10 16:02:27', 0, 0, 0, 'test', 'http://localhost:9090/blob/post/cover/7c946472-93c8-442e-8b50-1ace3b0f56d7.jpg', '2024-09-10 16:04:35', NULL, 0);
INSERT INTO `post` VALUES ('P1833762807486205952', 'U1833414699124359168', '<h1 id=\"rb2b5\">计算机组成原理</h1><ol><li>计算机概述</li><li>数据的机器级表示</li><li>指令系统</li><li>流水线</li><li>存储器层次</li><li>零碎知识点</li></ol>', '计算机组成原理|计算机', '2024-09-11 15:01:37', 0, 0, 0, '计算机组成原理复习大纲', 'http://localhost:9090/blob/post/cover/0b77d970-e4d4-4e3b-9ebd-f2e4e1e3f892.jpg', '2024-09-11 15:02:29', NULL, 0);
INSERT INTO `post` VALUES ('P1833763228439138304', 'U1833414699124359168', '<p>jsp先要打好java的功底</p>', 'jsp|java', '2024-09-11 15:03:18', 0, 0, 0, 'JSP复习思路', 'http://localhost:9090/blob/post/cover/ac2a7211-9e04-46c2-a278-0a523947ef0a.jpeg', '2024-09-11 15:03:58', NULL, 1);
INSERT INTO `post` VALUES ('P1833763355572686848', 'U1833414699124359168', '<p>计算机操作系统很重要</p>', '计算机操作系统|java', '2024-09-11 15:03:48', 0, 0, 0, '计算机操作系统知识点', 'http://localhost:9090/blob/post/cover/5b6b6f8e-1001-457f-87ce-a83d5d28f518.jpg', '2024-09-11 15:03:57', NULL, 1);
INSERT INTO `post` VALUES ('P1833776642989346816', 'U1833414699124359168', '<p>学好jsp要先学java，打好java基础在学html、css。jsp就是前后端的整合。</p>', 'jsp|java', '2024-09-11 15:56:36', 0, 0, 2, 'JSP复习指南', 'http://localhost:9090/blob/img/background.png', NULL, NULL, 0);

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色id',
  `permission_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('R00000001', 'P00000001');
INSERT INTO `role_permission` VALUES ('R00000001', 'P00000002');
INSERT INTO `role_permission` VALUES ('R00000001', 'P00000005');
INSERT INTO `role_permission` VALUES ('R00000001', 'P00000004');
INSERT INTO `role_permission` VALUES ('R00000001', 'P00000003');
INSERT INTO `role_permission` VALUES ('R00000002', 'P00000001');
INSERT INTO `role_permission` VALUES ('R00000002', 'P00000002');
INSERT INTO `role_permission` VALUES ('R00000002', 'P00000003');
INSERT INTO `role_permission` VALUES ('R00000002', 'P00000004');
INSERT INTO `role_permission` VALUES ('R00000002', 'P00000005');
INSERT INTO `role_permission` VALUES ('R00000003', 'P00000006');
INSERT INTO `role_permission` VALUES ('R00000001', 'P00000007');
INSERT INTO `role_permission` VALUES ('R00000001', 'P00000006');

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `role_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('R00000001', '文章管理员');
INSERT INTO `roles` VALUES ('R00000002', '普通用户');
INSERT INTO `roles` VALUES ('R00000003', '公告管理员');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `role_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('U1805198690294980608', 'R00000001');
INSERT INTO `user_role` VALUES ('U1805230146664833024', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833025', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833026', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833027', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833028', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833029', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833030', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833031', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833032', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833033', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833034', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833035', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833036', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833037', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833038', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833039', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833040', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833041', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833042', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833043', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833044', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833045', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833046', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833047', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833048', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833049', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833050', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833051', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833052', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833053', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833054', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833055', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833056', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833057', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833058', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833059', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833060', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833061', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833062', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833063', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833064', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833065', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833066', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833067', 'R00000002');
INSERT INTO `user_role` VALUES ('U1805230146664833068', 'R00000002');
INSERT INTO `user_role` VALUES ('U1833388335084183552', 'R00000002');
INSERT INTO `user_role` VALUES ('U1833414699124359168', 'R00000002');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `telephone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '电话号码',
  `gender` tinyint NULL DEFAULT 0 COMMENT '性别',
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `password` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_off_time` datetime NULL DEFAULT NULL COMMENT '最后一次下线时间',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后一次上线时间',
  `photo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像链接',
  `followers` bigint NULL DEFAULT 0 COMMENT '粉丝数',
  `post` int NULL DEFAULT 0 COMMENT '发布文章数',
  `concern` int NULL DEFAULT 0 COMMENT '关注人数',
  `last_post_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后一个文章id',
  `self_tag` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '自定义标签',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '0:正常 1:异常',
  `love` int UNSIGNED NULL DEFAULT 0 COMMENT '喜欢数',
  `collect` int UNSIGNED NULL DEFAULT 0 COMMENT '收藏数',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `idx_email`(`email` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('U1805198690294980608', '李昊', '19855823027', 0, '805459343@qq.com', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-24 19:18:01', '2024-08-27 10:03:34', '2024-09-11 15:42:04', 'http://localhost:9090/blob/img/158e5864-83ec-499d-b40d-40805f17fec9.png', 1, 12, 1, NULL, 'java|全栈工程师|考研专区|netty', 0, 2, 2);
INSERT INTO `users` VALUES ('U1805230146664833024', '孙泽泰', '19817115293', 0, '2758329629@qq.com', 'efdda259011d0fd051cd193748456ba6', '2024-06-24 21:23:01', NULL, '2024-06-25 19:15:48', 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, 'java|全栈工程师|期末复习|jsp', 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833025', '张三', '19811115293', 0, '123456@test1', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-24 19:18:01', '2024-08-27 10:03:34', '2024-09-10 15:48:01', 'http://localhost:9090/blob/img/c762e720-d7c2-4b6a-a8c5-ce82f9d0f1a9.jpg', 0, 5, 1, NULL, 'java|netty', 0, 3, 3);
INSERT INTO `users` VALUES ('U1805230146664833026', '李四', '19822225293', 0, '123456@test2', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-26 20:43:07', '2024-08-27 10:03:34', '2024-09-10 15:43:12', 'http://121.40.154.188:8080/image/defAva.png', 3, 7, 0, NULL, 'test|test2', 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833027', '王五', '19833335293', 1, '123456@test3', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', '2024-08-25 20:51:47', '2024-08-25 16:52:27', 'http://localhost:9090/blob/img/79f30840-2f92-46bf-917f-15cabe4c21aa.jpg', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833028', '赵六', '19844445293', 1, '123456@test4', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:40:55', NULL, '2024-08-25 18:56:41', 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833029', '李明', '13800138000', 0, '123456@test5', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', '2024-08-25 21:15:42', '2024-09-09 21:00:44', 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, 'setea|java|es|test', 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833030', '李四', '13800138001', 0, '123456@test6', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833031', '王五', '13800138002', 0, '123456@test7', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833032', '赵六', '13800138003', 0, '123456@test8', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833033', '孙七', '13800138004', 0, '123456@test9', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833034', '周八', '13800138005', 0, '123456@test10', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833035', '吴九', '13800138006', 0, '123456@test11', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833036', '郑十', '13800138007', 0, '123456@test12', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833037', '王十一', '13800138008', 0, '123456@test13', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833038', '李十二', '13800138009', 0, '123456@test14', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833039', '张十三', '13800138010', 0, '123456@test15', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833040', '赵十四', '13800138011', 0, '123456@test16', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833041', '孙十五', '13800138012', 0, '123456@test17', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833042', '周十六', '13800138013', 0, '123456@test18', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833043', '吴十七', '13800138014', 0, '123456@test19', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833044', '郑十八', '13800138015', 0, '123456@test20', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833045', '王十九', '13800138016', 0, '123456@test21', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833046', '李二十', '13800138017', 0, '123456@test22', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833047', '张二十一', '13800138018', 0, '123456@test23', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833048', '赵二十二', '13800138019', 0, '123456@test24', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833049', '张二十三', '13800138020', 0, '123456@test25', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833050', '李二十四', '13800138021', 0, '123456@test26', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833051', '王二十五', '13800138022', 0, '123456@test27', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833052', '赵二十六', '13800138023', 0, '123456@test28', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833053', '孙二十七', '13800138024', 0, '123456@test29', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833054', '周二十八', '13800138025', 0, '123456@test30', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833055', '吴二十九', '13800138026', 0, '123456@test31', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833056', '郑三十', '13800138027', 0, '123456@test32', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833057', '王三十一', '13800138028', 0, '123456@test33', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833058', '李三十二', '13800138029', 0, '123456@test34', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833059', '张三十三', '13800138030', 0, '123456@test35', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833060', '赵三十四', '13800138031', 0, '123456@test36', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833061', '孙三十五', '13800138032', 0, '123456@test37', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833062', '周三十六', '13800138033', 0, '123456@test38', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833063', '吴三十七', '13800138034', 0, '123456@test39', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833064', '郑三十八', '13800138035', 0, '123456@test40', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833065', '王三十九', '13800138036', 0, '123456@test41', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833066', '李四十', '13800138037', 0, '123456@test42', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833067', '张四十一', '13800138038', 0, '123456@test43', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1805230146664833068', '赵四十二', '13800138039', 0, '123456@test44', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-06-29 12:33:51', NULL, NULL, 'http://121.40.154.188:8080/image/defAva.png', 0, 0, 0, NULL, NULL, 0, 0, 0);
INSERT INTO `users` VALUES ('U1833388335084183552', '阿良', '', 0, '805459341@qq.com', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-09-10 14:13:36', NULL, '2024-09-10 14:26:01', 'http://localhost:9090/blob/img/5d7f03cc-b4e0-4fc0-9bf3-90eb54df4894.jpg', 0, 3, 0, NULL, 'es', 0, 0, 0);
INSERT INTO `users` VALUES ('U1833414699124359168', '李昊', '19811115293', 0, '805459342@qq.com', '47ec2dd791e31e2ef2076caf64ed9b3d', '2024-09-10 15:58:22', NULL, '2024-09-11 15:55:34', 'http://localhost:9090/blob/img/623dc475-38eb-42b7-9e36-7f5d504af1ca.jpg', 0, 4, 2, NULL, '计算机组成原理|java|期末复习', 0, 2, 1);

SET FOREIGN_KEY_CHECKS = 1;
