-- ============================================================
-- 星际相册 NASA每日天文图 - MySQL 数据库初始化脚本
-- 数据库: nasa_album
-- 执行方式: mysql -u root -p < schema.sql
-- ============================================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS nasa_album
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE nasa_album;

-- -----------------------------------------------------------
-- 用户表: 存储登录注册的用户信息
-- 对应 Java 实体: com.nasa.album.entity.User
-- 对应 Mapper: com.nasa.album.mapper.UserMapper
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`    VARCHAR(50)  NOT NULL COMMENT '用户名',
    `password`    VARCHAR(100) NOT NULL COMMENT '密码',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- -----------------------------------------------------------
-- 收藏表: 存储用户收藏的 NASA 天文图片
-- 前端使用 localStorage 收藏，此表用于后端扩展（可选）
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
    `user_id`     BIGINT       NOT NULL COMMENT '用户ID',
    `apod_date`   VARCHAR(20)  NOT NULL COMMENT 'APOD日期 yyyy-MM-dd',
    `title`       VARCHAR(255) DEFAULT NULL COMMENT '图片标题',
    `url`         VARCHAR(500) DEFAULT NULL COMMENT '图片URL',
    `explanation` TEXT         DEFAULT NULL COMMENT '图片说明',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_date` (`user_id`, `apod_date`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户收藏表';

-- -----------------------------------------------------------
-- 插入测试数据
-- 测试账号: admin / 123456
-- -----------------------------------------------------------
INSERT INTO `user` (`username`, `password`) VALUES
    ('admin', '123456'),
    ('test',  '123456');

-- 验证
SELECT '数据库初始化完成！' AS message;
SELECT * FROM `user`;
