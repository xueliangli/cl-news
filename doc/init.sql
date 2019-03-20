CREATE TABLE `news` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `source` varchar(32) NOT NULL DEFAULT '' COMMENT '来源',
  `source_name` varchar(64) NOT NULL DEFAULT '' COMMENT '来源名称',
  `title` varchar(1024) NOT NULL DEFAULT '' COMMENT '标题',
  `content` text COMMENT '内容',
  `content_url` varchar(128) NOT NULL DEFAULT '' COMMENT '内容链接',
  `image_url` varchar(128) NOT NULL DEFAULT '' COMMENT '图片连接',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_create_date` (`create_date`),
  KEY `idx_update_date` (`update_date`),
  KEY `idx_source` (`source`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;