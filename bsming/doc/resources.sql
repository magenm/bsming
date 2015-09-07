
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dalsequence`
-- ----------------------------
DROP TABLE IF EXISTS `dalsequence`;
CREATE TABLE `dalsequence` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '',
  `last_value` bigint(20) DEFAULT '0',
  `min_value` int(11) DEFAULT '0',
  `max_value` int(11) DEFAULT '0',
  `cache_count` int(11) DEFAULT '100',
  `last_update_time` bigint(20) DEFAULT NULL,
  `serviceShortName` varchar(20) DEFAULT NULL,
  `description` varchar(150) DEFAULT NULL,
  `SPAN` tinyint(4) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='InnoDB free: 0 kB; InnoDB free: 0 kB';

-- ----------------------------
-- Records of dalsequence
-- ----------------------------

-- ----------------------------
-- Table structure for `resources`
-- ----------------------------
DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) DEFAULT NULL,
  `resource` varchar(1000) DEFAULT NULL,
  `strategy` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=116 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resources
-- ----------------------------

-- ----------------------------
-- Procedure structure for `getSequence`
-- ----------------------------
DROP PROCEDURE IF EXISTS `getSequence`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getSequence`(IN seqName VARCHAR(50), OUT oldValue INTEGER,   OUT newValue INTEGER)
BEGIN 
START TRANSACTION;

    select last_value+1,last_value+cache_count
    INTO oldValue,newValue 
    from dalsequence where  name=seqName  for update;

    update dalsequence set last_value = last_value+cache_count where 
    name = seqName;

   COMMIT;
   END
;;
DELIMITER ;
