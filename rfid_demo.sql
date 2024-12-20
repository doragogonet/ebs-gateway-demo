-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 192.168.31.100    Database: rfid_demo
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `antennas`
--

DROP TABLE IF EXISTS `antennas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `antennas` (
  `antenna_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `reader_id` bigint NOT NULL COMMENT 'このアンテナが接続されているリーダーのID',
  `antenna_name` varchar(100) DEFAULT NULL COMMENT 'アンテナの名称',
  `type` varchar(1) DEFAULT NULL COMMENT 'アンテナのタイプまたは用途（例：1円偏波、2線偏波）',
  `orientation` varchar(20) DEFAULT NULL COMMENT 'アンテナの向きや設置された方向',
  `status` varchar(1) NOT NULL COMMENT 'アンテナの稼働状態（例：1アクティブ、2非アクティブ）',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  PRIMARY KEY (`antenna_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='RFIDアンテナテーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `antennas`
--

LOCK TABLES `antennas` WRITE;
/*!40000 ALTER TABLE `antennas` DISABLE KEYS */;
INSERT INTO `antennas` VALUES (1,1,'アンテナ1',NULL,NULL,'1','',NULL,'',NULL),(2,1,'アンテナ2',NULL,NULL,'1','',NULL,'',NULL),(3,1,'アンテナ3',NULL,NULL,'1','',NULL,'',NULL),(4,1,'アンテナ4',NULL,NULL,'1','',NULL,'',NULL);
/*!40000 ALTER TABLE `antennas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_table`
--

DROP TABLE IF EXISTS `gen_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table` (
  `table_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `table_name` varchar(200) DEFAULT '' COMMENT 'テーブル名',
  `table_comment` varchar(500) DEFAULT '' COMMENT 'テーブル説明',
  `sub_table_name` varchar(64) DEFAULT NULL COMMENT '関連サブテーブルのテーブル名',
  `sub_table_fk_name` varchar(64) DEFAULT NULL COMMENT '関連サブテーブルの外部キー',
  `class_name` varchar(100) DEFAULT '' COMMENT 'エンティティークラス名',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用なテンプレート（crudシングルテーブル treeツリーテーブル subマスタサブテーブル）',
  `package_name` varchar(100) DEFAULT NULL COMMENT 'パッケージパスの生成',
  `module_name` varchar(30) DEFAULT NULL COMMENT 'モジュール名の生成',
  `business_name` varchar(30) DEFAULT NULL COMMENT 'ビジネス名の生成',
  `function_name` varchar(50) DEFAULT NULL COMMENT '機能名の生成',
  `function_author` varchar(50) DEFAULT NULL COMMENT '機能の作成者の生成',
  `gen_type` char(1) DEFAULT '0' COMMENT 'コード生成方式（0zip圧縮パック 1カスタムパス）',
  `gen_path` varchar(200) DEFAULT '/' COMMENT '生成パス（入力しなければ、デフォルトプロジェクトパス）',
  `options` varchar(1000) DEFAULT NULL COMMENT 'その他の生成オプション',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  `remark` varchar(500) DEFAULT NULL COMMENT '備考',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='コード生成テーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table`
--

LOCK TABLES `gen_table` WRITE;
/*!40000 ALTER TABLE `gen_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `gen_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_table_column`
--

DROP TABLE IF EXISTS `gen_table_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table_column` (
  `column_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `table_id` bigint DEFAULT NULL COMMENT '所属テーブルID',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列説明',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列タイプ',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVAタイプ',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVAフィールド名',
  `is_pk` char(1) DEFAULT NULL COMMENT 'プライマリ・キーか（1Y）',
  `is_increment` char(1) DEFAULT NULL COMMENT '自動プラスか（1Y）',
  `is_required` char(1) DEFAULT NULL COMMENT '必須か（1Y）',
  `is_insert` char(1) DEFAULT NULL COMMENT 'insertか（1Y）',
  `is_edit` char(1) DEFAULT NULL COMMENT 'editか（1Y）',
  `is_list` char(1) DEFAULT NULL COMMENT 'listィールドか（1Y）',
  `is_query` char(1) DEFAULT NULL COMMENT 'queryィールドか（1Y）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '検索タイプ（=、<>、>、<、範囲）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '表示タイプ（text、text area、select、checkbox、radio、date）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '辞書タイプ',
  `sort` int DEFAULT NULL COMMENT 'ソート',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='コード生成テーブルフィールド';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table_column`
--

LOCK TABLES `gen_table_column` WRITE;
/*!40000 ALTER TABLE `gen_table_column` DISABLE KEYS */;
/*!40000 ALTER TABLE `gen_table_column` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `port_antenna_connections`
--

DROP TABLE IF EXISTS `port_antenna_connections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `port_antenna_connections` (
  `port_antenna_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `reader_id` bigint NOT NULL COMMENT 'リーダーID',
  `port_number` int NOT NULL COMMENT 'リーダー上の物理的または論理的なポート番号',
  `antenna_id` bigint NOT NULL COMMENT 'ポートに接続されるアンテナのID',
  `connection_times` datetime NOT NULL COMMENT '日付と時間、アンテナがポートに接続された正確な時刻',
  `disconnection_times` datetime NOT NULL COMMENT '日付と時間、アンテナがポートから切断された正確な時刻（接続中の場合はNULLまたは未来の日付',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  PRIMARY KEY (`port_antenna_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='接続したアンテナテーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `port_antenna_connections`
--

LOCK TABLES `port_antenna_connections` WRITE;
/*!40000 ALTER TABLE `port_antenna_connections` DISABLE KEYS */;
/*!40000 ALTER TABLE `port_antenna_connections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `blob_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Blobタイプのトリガテーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_blob_triggers`
--

LOCK TABLES `qrtz_blob_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_blob_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_blob_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) NOT NULL,
  `calendar_name` varchar(200) NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='カレンダ情報テーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_calendars`
--

LOCK TABLES `qrtz_calendars` WRITE;
/*!40000 ALTER TABLE `qrtz_calendars` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `cron_expression` varchar(200) NOT NULL,
  `time_zone_id` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Cronタイプのトリガテーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_cron_triggers`
--

LOCK TABLES `qrtz_cron_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_cron_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_cron_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `entry_id` varchar(95) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `fired_time` bigint NOT NULL,
  `sched_time` bigint NOT NULL,
  `priority` int NOT NULL,
  `state` varchar(16) NOT NULL,
  `job_name` varchar(200) DEFAULT NULL,
  `job_group` varchar(200) DEFAULT NULL,
  `is_nonconcurrent` varchar(1) DEFAULT NULL,
  `requests_recovery` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='トリガされたトリガテーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_fired_triggers`
--

LOCK TABLES `qrtz_fired_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_fired_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_fired_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(120) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `job_class_name` varchar(250) NOT NULL,
  `is_durable` varchar(1) NOT NULL,
  `is_nonconcurrent` varchar(1) NOT NULL,
  `is_update_data` varchar(1) NOT NULL,
  `requests_recovery` varchar(1) NOT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='タスク詳細テーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_job_details`
--

LOCK TABLES `qrtz_job_details` WRITE;
/*!40000 ALTER TABLE `qrtz_job_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_job_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) NOT NULL,
  `lock_name` varchar(40) NOT NULL,
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='保存されている悲観的ロック情報テーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_locks`
--

LOCK TABLES `qrtz_locks` WRITE;
/*!40000 ALTER TABLE `qrtz_locks` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_locks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='一時停止されたトリガテーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_paused_trigger_grps`
--

LOCK TABLES `qrtz_paused_trigger_grps` WRITE;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `last_checkin_time` bigint NOT NULL,
  `checkin_interval` bigint NOT NULL,
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='スケジューラ状態テーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_scheduler_state`
--

LOCK TABLES `qrtz_scheduler_state` WRITE;
/*!40000 ALTER TABLE `qrtz_scheduler_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_scheduler_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `repeat_count` bigint NOT NULL,
  `repeat_interval` bigint NOT NULL,
  `times_triggered` bigint NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='単純トリガの情報テーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simple_triggers`
--

LOCK TABLES `qrtz_simple_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simple_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simple_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simprop_triggers`
--

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `str_prop_1` varchar(512) DEFAULT NULL,
  `str_prop_2` varchar(512) DEFAULT NULL,
  `str_prop_3` varchar(512) DEFAULT NULL,
  `int_prop_1` int DEFAULT NULL,
  `int_prop_2` int DEFAULT NULL,
  `long_prop_1` bigint DEFAULT NULL,
  `long_prop_2` bigint DEFAULT NULL,
  `dec_prop_1` decimal(13,4) DEFAULT NULL,
  `dec_prop_2` decimal(13,4) DEFAULT NULL,
  `bool_prop_1` varchar(1) DEFAULT NULL,
  `bool_prop_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='同期メカニズムのロー・ロック・テーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simprop_triggers`
--

LOCK TABLES `qrtz_simprop_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `next_fire_time` bigint DEFAULT NULL,
  `prev_fire_time` bigint DEFAULT NULL,
  `priority` int DEFAULT NULL,
  `trigger_state` varchar(16) NOT NULL,
  `trigger_type` varchar(8) NOT NULL,
  `start_time` bigint NOT NULL,
  `end_time` bigint DEFAULT NULL,
  `calendar_name` varchar(200) DEFAULT NULL,
  `misfire_instr` smallint DEFAULT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='トリガ詳細テーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_triggers`
--

LOCK TABLES `qrtz_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `readers`
--

DROP TABLE IF EXISTS `readers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `readers` (
  `reader_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `reader_name` varchar(100) NOT NULL COMMENT 'リーダーの名称',
  `serial_number` varchar(100) DEFAULT NULL COMMENT '製造元が割り当てる一意の番号',
  `ip_address` varchar(20) NOT NULL COMMENT 'リーダーに割り当てられたIPアドレス',
  `location` varchar(100) DEFAULT NULL COMMENT 'リーダーが設置されている物理的な場所',
  `status` varchar(1) NOT NULL COMMENT 'リーダーの稼働状態（例：1オンライン、2オフライン、3メンテナンス中）',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  `reader_image` varchar(45) DEFAULT NULL,
  `reader_port` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`reader_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='RFIDリーダーテーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `readers`
--

LOCK TABLES `readers` WRITE;
/*!40000 ALTER TABLE `readers` DISABLE KEYS */;
INSERT INTO `readers` VALUES (1,'zebra_fx9600',NULL,'169.254.10.1',NULL,'1','',NULL,'',NULL,'zebra_fx9600.jpg',0);
/*!40000 ALTER TABLE `readers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rfid_data`
--

DROP TABLE IF EXISTS `rfid_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rfid_data` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `reader_ip` varchar(15) NOT NULL COMMENT 'リーダーIP',
  `tag_id` varchar(30) NOT NULL COMMENT 'タグID',
  `tag_rssi` varchar(10) NOT NULL COMMENT '信号値',
  `tag_rssi_level` int NOT NULL COMMENT '信号レベル',
  `tag_time` varchar(30) NOT NULL COMMENT 'タグ時間',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='RFIDデータテーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rfid_data`
--

LOCK TABLES `rfid_data` WRITE;
/*!40000 ALTER TABLE `rfid_data` DISABLE KEYS */;
INSERT INTO `rfid_data` VALUES (22,'169.254.10.1','1712049C','-71',1,'2024-08-08 16:12:52.664','','2024-08-08 16:12:54','','2024-08-08 16:12:54'),(23,'169.254.10.1','472D53730001','-67',2,'2024-08-08 16:12:58.308','','2024-08-08 16:13:00','','2024-08-08 16:13:00'),(24,'169.254.10.1','472D53730002','-66',2,'2024-08-08 16:13:01.407','','2024-08-08 16:13:03','','2024-08-08 16:13:03'),(25,'169.254.10.1','472D53730003','-63',2,'2024-08-08 16:13:01.502','','2024-08-08 16:13:03','','2024-08-08 16:13:03'),(26,'169.254.10.1','1234FFFE56780F2BF06F','-61',2,'2024-08-08 16:13:04.004','','2024-08-08 16:13:05','','2024-08-08 16:13:05'),(27,'169.254.10.1','1234FFFE56780F2BF06F','-27',6,'2024-08-19 13:24:35.806','','2024-08-19 13:24:35','','2024-08-19 13:24:35'),(28,'169.254.10.1','1234FFFE56780F2BF06F','-28',6,'2024-08-19 13:27:07.428','','2024-08-19 13:27:07','','2024-08-19 13:27:07'),(29,'169.254.10.1','1234FFFE56780F2BF06F','-28',6,'2024-08-19 13:27:38.264','','2024-08-19 13:27:38','','2024-08-19 13:27:38'),(30,'169.254.10.1','1234FFFE56780F2BF06F','-29',6,'2024-08-19 13:29:24.611','','2024-08-19 13:29:24','','2024-08-19 13:29:24'),(31,'169.254.10.1','1234FFFE56780F2BF06F','-30',6,'2024-08-19 13:31:28.602','','2024-08-19 13:31:28','','2024-08-19 13:31:28'),(32,'169.254.10.1','1234FFFE56780F2BF06F','-30',6,'2024-08-19 13:32:51.845','','2024-08-19 13:32:51','','2024-08-19 13:32:51'),(33,'169.254.10.1','1234FFFE56780F2BF06F','-29',6,'2024-08-19 13:35:46.859','','2024-08-19 13:35:46','','2024-08-19 13:35:46'),(34,'169.254.10.1','1234FFFE56780F2BF06F','-30',6,'2024-08-19 13:38:14.282','','2024-08-19 13:38:14','','2024-08-19 13:38:14'),(35,'169.254.10.1','1234FFFE56780F2BF06F','-31',5,'2024-08-19 13:59:39.417','','2024-08-19 13:59:39','','2024-08-19 13:59:39'),(36,'169.254.10.1','1234FFFE56780F2BF06F','-30',6,'2024-08-19 14:14:20.160','','2024-08-19 14:14:20','','2024-08-19 14:14:20'),(37,'169.254.10.1','1234FFFE56780F2BF06F','-31',5,'2024-08-19 14:16:39.196','','2024-08-19 14:16:39','','2024-08-19 14:16:39'),(38,'169.254.10.1','1234FFFE56780F2BF06F','-36',5,'2024-08-20 12:11:44.769','','2024-08-20 12:11:46','','2024-08-20 12:11:46');
/*!40000 ALTER TABLE `rfid_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storage_cell`
--

DROP TABLE IF EXISTS `storage_cell`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storage_cell` (
  `cell_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `rack_id` bigint NOT NULL COMMENT 'ラックのid',
  `store_id` bigint NOT NULL COMMENT '倉庫のid',
  `cell_code` varchar(20) NOT NULL COMMENT '間口のコード',
  `rack_name` varchar(100) NOT NULL COMMENT '間口の名称',
  `cell_bar_code` varchar(50) NOT NULL COMMENT '倉庫-ラック-段数間口のバーコード（例：A0002-1002-0103）',
  `remark` varchar(200) DEFAULT NULL COMMENT '備考',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  PRIMARY KEY (`cell_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='保管場所-段数-間口テーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storage_cell`
--

LOCK TABLES `storage_cell` WRITE;
/*!40000 ALTER TABLE `storage_cell` DISABLE KEYS */;
/*!40000 ALTER TABLE `storage_cell` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storage_rack`
--

DROP TABLE IF EXISTS `storage_rack`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storage_rack` (
  `rack_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `store_id` bigint NOT NULL COMMENT '倉庫のid',
  `rack_name` varchar(100) NOT NULL COMMENT 'ラックの名称',
  `rack_bar_code` varchar(50) NOT NULL COMMENT '倉庫-ラックのバーコード（例：A0002-1002）',
  `remark` varchar(200) DEFAULT NULL COMMENT '備考',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  PRIMARY KEY (`rack_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='保管場所-ラックテーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storage_rack`
--

LOCK TABLES `storage_rack` WRITE;
/*!40000 ALTER TABLE `storage_rack` DISABLE KEYS */;
/*!40000 ALTER TABLE `storage_rack` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storage_store`
--

DROP TABLE IF EXISTS `storage_store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storage_store` (
  `store_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `store_name` varchar(100) NOT NULL COMMENT '倉庫の名称',
  `store_bar_code` varchar(50) NOT NULL COMMENT '倉庫のバーコード（例：A0002）',
  `remark` varchar(200) DEFAULT NULL COMMENT '備考',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='保管場所-倉庫テーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storage_store`
--

LOCK TABLES `storage_store` WRITE;
/*!40000 ALTER TABLE `storage_store` DISABLE KEYS */;
/*!40000 ALTER TABLE `storage_store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_config` (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT 'パラメータ主キー',
  `config_name` varchar(100) DEFAULT '' COMMENT 'パラメータ名',
  `config_key` varchar(100) DEFAULT '' COMMENT 'パラメータキー名',
  `config_value` varchar(500) DEFAULT '' COMMENT 'パラメータキー値',
  `config_type` char(1) DEFAULT 'N' COMMENT 'システム組み込み（Yはい Nいいえ）',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  `remark` varchar(500) DEFAULT NULL COMMENT '備考',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='パラメータ設定テーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'メインフレームページ-デフォルトのスキンスタイル名','sys.index.skinName','skin-blue','Y','admin','2024-05-29 16:50:23','',NULL,'skin-blue、skin-green、skin-purple、skin-red、skin-yellow'),(2,'ユーザー管理-アカウント初期パスワード','sys.user.initPassword','123456','Y','admin','2024-05-29 16:50:23','',NULL,'初期化パスワード 123456'),(3,'メインフレームページ-サイドバーのトピック','sys.index.sideTheme','theme-dark','Y','admin','2024-05-29 16:50:23','',NULL,'theme-dark，theme-light，theme-blue'),(4,'アカウントセルフサービス-ユーザー登録機能を有効にするかどうか','sys.account.registerUser','false','Y','admin','2024-05-29 16:50:23','',NULL,'ユーザー登録機能を有効にするかどうか（trueオープン、false閉じる）'),(5,'ユーザー管理-パスワード文字範囲','sys.account.chrtype','0','Y','admin','2024-05-29 16:50:23','',NULL,'デフォルトの任意の文字範囲、0任意（パスワードは任意の文字を入力できます）、1数字（パスワードは0-9数字）、2英字（パスワードはa-zおよびA-Z文字）、3英数字（パスワードには英字、数字を含める必要があります）、4英数字と特殊文字（現在サポートされている特殊文字：~!@#$%^&*()-=_+）'),(6,'ユーザー管理-初期パスワード変更ポリシー','sys.account.initPasswordModify','1','Y','admin','2024-05-29 16:50:23','',NULL,'0：初期パスワード変更ポリシーはオフで、プロンプトはありません，1：初期パスワードを変更していない場合はログイン時にパスワード変更を警告するダイアログボックス'),(7,'ユーザー管理-アカウントパスワード更新サイクル','sys.account.passwordValidateDays','0','Y','admin','2024-05-29 16:50:23','',NULL,'パスワード更新サイクル（数値を記入し、データ初期化値が0であれば制限せず、修正するには0より365より大きい正の整数でなければならない）、このサイクルを超えてシステムにログインすると、ログイン時にパスワード変更を通知するダイアログボックスが表示されます'),(8,'メインフレームページ-メニューナビゲーション表示スタイル','sys.index.menuStyle','default','Y','admin','2024-05-29 16:50:23','',NULL,'メニューナビゲーション表示スタイル（default左側のナビゲーションメニュー，topnavトップ・ナビゲーション・メニュー）'),(9,'メインフレームページ-フッターを開くかどうか','sys.index.footer','true','Y','admin','2024-05-29 16:50:23','',NULL,'下部フッターの表示をオンにするかどうか（true表示，false非表示）'),(10,'メインフレームページ-ページラベルを開くかどうか','sys.index.tagsView','true','Y','admin','2024-05-29 16:50:23','',NULL,'メニューの複数ページのチェックをオンにするかどうか（true表示，false非表示）'),(11,'ユーザーログイン-ブラックリスト','sys.login.blackIPList','','Y','admin','2024-05-29 16:50:23','',NULL,'ログインIPブラックリスト制限の設定、複数の一致するアイテム「;」で区切り、照合のサポート（*、ネットワークセグメント）');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部門id',
  `parent_id` bigint DEFAULT '0' COMMENT '親部門id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖先リスト',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部門名',
  `order_num` int DEFAULT '0' COMMENT '表示順序',
  `leader` varchar(20) DEFAULT NULL COMMENT '責任者',
  `phone` varchar(11) DEFAULT NULL COMMENT '連絡先電話番号',
  `email` varchar(50) DEFAULT NULL COMMENT 'メール',
  `status` char(1) DEFAULT '0' COMMENT '部門状態（0正常 1停止）',
  `del_flag` char(1) DEFAULT '0' COMMENT '削除フラグ（0存在 2削除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部門テーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (100,0,'0','EBS',0,'EBS','15888888888','kaku@ebskk.com','0','0','admin','2024-05-29 16:50:21','',NULL),(101,100,'0,100','EBS東京本社',1,'EBS','15888888888','kaku@ebskk.com','0','0','admin','2024-05-29 16:50:21','',NULL),(102,100,'0,100','大連支社',2,'EBS','15888888888','dl_guofy@ebskk.com','0','0','admin','2024-05-29 16:50:21','',NULL),(103,101,'0,100,101','開発部',1,'EBS','15888888888','dl_suxt@ebskk.com','0','0','admin','2024-05-29 16:50:21','',NULL),(104,101,'0,100,101','営業部',2,'EBS','15888888888','dl_guofy@ebskk.com','0','0','admin','2024-05-29 16:50:21','',NULL),(105,101,'0,100,101','テスト部',3,'EBS','15888888888','dl_guofy@ebskk.com','0','0','admin','2024-05-29 16:50:21','',NULL),(106,101,'0,100,101','財務部',4,'EBS','15888888888','dl_guofy@ebskk.com','0','0','admin','2024-05-29 16:50:21','',NULL),(107,101,'0,100,101','運用部',5,'EBS','15888888888','dl_guofy@ebskk.com','0','0','admin','2024-05-29 16:50:21','',NULL),(108,102,'0,100,102','営業部',1,'EBS','15888888888','dl_guofy@ebskk.com','0','0','admin','2024-05-29 16:50:21','',NULL),(109,102,'0,100,102','財務部',2,'EBS','15888888888','dl_guofy@ebskk.com','0','0','admin','2024-05-29 16:50:21','',NULL);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '辞書エンコード',
  `dict_sort` int DEFAULT '0' COMMENT '辞書ソート',
  `dict_label` varchar(100) DEFAULT '' COMMENT '辞書タブ',
  `dict_value` varchar(100) DEFAULT '' COMMENT '辞書キー値',
  `dict_type` varchar(100) DEFAULT '' COMMENT '辞書種類',
  `css_class` varchar(100) DEFAULT NULL COMMENT 'スタイルプロパティ（その他のスタイル拡張）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表のエコースタイル',
  `is_default` char(1) DEFAULT 'N' COMMENT 'デフォルトか（Yはい Nいいえ）',
  `status` char(1) DEFAULT '0' COMMENT '状態（0正常 1停止）',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  `remark` varchar(500) DEFAULT NULL COMMENT '備考',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='辞書データテーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` VALUES (1,1,'男','0','sys_user_sex','','','Y','0','admin','2024-05-29 16:50:23','',NULL,'性別男'),(2,2,'女','1','sys_user_sex','','','N','0','admin','2024-05-29 16:50:23','',NULL,'性別女'),(3,3,'不明','2','sys_user_sex','','','N','0','admin','2024-05-29 16:50:23','',NULL,'性別不明'),(4,1,'表示','0','sys_show_hide','','primary','Y','0','admin','2024-05-29 16:50:23','',NULL,'表示菜单'),(5,2,'非表示','1','sys_show_hide','','danger','N','0','admin','2024-05-29 16:50:23','',NULL,'非表示菜单'),(6,1,'正常','0','sys_normal_disable','','primary','Y','0','admin','2024-05-29 16:50:23','',NULL,'正常状態'),(7,2,'停止','1','sys_normal_disable','','danger','N','0','admin','2024-05-29 16:50:23','',NULL,'停止状態'),(8,1,'正常','0','sys_job_status','','primary','Y','0','admin','2024-05-29 16:50:23','',NULL,'正常状態'),(9,2,'一時停止','1','sys_job_status','','danger','N','0','admin','2024-05-29 16:50:23','',NULL,'一時停止状態'),(10,1,'デフォルト','DEFAULT','sys_job_group','','','Y','0','admin','2024-05-29 16:50:23','',NULL,'デフォルトグループ'),(11,2,'システム','SYSTEM','sys_job_group','','','N','0','admin','2024-05-29 16:50:23','',NULL,'システムグループ'),(12,1,'はい','Y','sys_yes_no','','primary','Y','0','admin','2024-05-29 16:50:23','',NULL,'デフォルトはい'),(13,2,'いいえ','N','sys_yes_no','','danger','N','0','admin','2024-05-29 16:50:23','',NULL,'デフォルトいいえ'),(14,1,'お知らせ','1','sys_notice_type','','warning','Y','0','admin','2024-05-29 16:50:23','',NULL,'お知らせ'),(15,2,'公告','2','sys_notice_type','','success','N','0','admin','2024-05-29 16:50:23','',NULL,'公告'),(16,1,'正常','0','sys_notice_status','','primary','Y','0','admin','2024-05-29 16:50:23','',NULL,'正常状態'),(17,2,'閉じる','1','sys_notice_status','','danger','N','0','admin','2024-05-29 16:50:23','',NULL,'閉じる状态'),(18,99,'その他','0','sys_oper_type','','info','N','0','admin','2024-05-29 16:50:23','',NULL,'その他操作'),(19,1,'追加','1','sys_oper_type','','info','N','0','admin','2024-05-29 16:50:23','',NULL,'追加操作'),(20,2,'変更','2','sys_oper_type','','info','N','0','admin','2024-05-29 16:50:23','',NULL,'変更操作'),(21,3,'削除','3','sys_oper_type','','danger','N','0','admin','2024-05-29 16:50:23','',NULL,'削除操作'),(22,4,'権限付与','4','sys_oper_type','','primary','N','0','admin','2024-05-29 16:50:23','',NULL,'権限付与操作'),(23,5,'エクスポート','5','sys_oper_type','','warning','N','0','admin','2024-05-29 16:50:23','',NULL,'エクスポート操作'),(24,6,'インポート','6','sys_oper_type','','warning','N','0','admin','2024-05-29 16:50:23','',NULL,'インポート操作'),(25,7,'強退','7','sys_oper_type','','danger','N','0','admin','2024-05-29 16:50:23','',NULL,'強退操作'),(26,8,'コード生成','8','sys_oper_type','','warning','N','0','admin','2024-05-29 16:50:23','',NULL,'生成操作'),(27,9,'データクリア','9','sys_oper_type','','danger','N','0','admin','2024-05-29 16:50:23','',NULL,'クリア操作'),(28,1,'成功','0','sys_common_status','','primary','N','0','admin','2024-05-29 16:50:23','',NULL,'正常状態'),(29,2,'失敗','1','sys_common_status','','danger','N','0','admin','2024-05-29 16:50:23','',NULL,'停止状態');
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '辞書タブ主キー',
  `dict_name` varchar(100) DEFAULT '' COMMENT '辞書名',
  `dict_type` varchar(100) DEFAULT '' COMMENT '辞書タブ',
  `status` char(1) DEFAULT '0' COMMENT '状態（0正常 1停止）',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  `remark` varchar(500) DEFAULT NULL COMMENT '備考',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='辞書種類テーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` VALUES (1,'ユーザー性別','sys_user_sex','0','admin','2024-05-29 16:50:23','',NULL,'ユーザー性別リスト'),(2,'メニュー状態','sys_show_hide','0','admin','2024-05-29 16:50:23','',NULL,'メニュー状態リスト'),(3,'システムスイッチ','sys_normal_disable','0','admin','2024-05-29 16:50:23','',NULL,'システムスイッチリスト'),(4,'タスクステータス','sys_job_status','0','admin','2024-05-29 16:50:23','',NULL,'タスクステータスリスト'),(5,'タスクグループ','sys_job_group','0','admin','2024-05-29 16:50:23','',NULL,'タスクグループリスト'),(6,'システムはいいいえ','sys_yes_no','0','admin','2024-05-29 16:50:23','',NULL,'システムはいいいえリスト'),(7,'お知らせタイプ','sys_notice_type','0','admin','2024-05-29 16:50:23','',NULL,'通知タイプリスト'),(8,'お知らせ状態','sys_notice_status','0','admin','2024-05-29 16:50:23','',NULL,'通知ステータスリスト'),(9,'操作タイプ','sys_oper_type','0','admin','2024-05-29 16:50:23','',NULL,'操作タイプリスト'),(10,'システム状態','sys_common_status','0','admin','2024-05-29 16:50:23','',NULL,'ログインステータスリスト');
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job`
--

DROP TABLE IF EXISTS `sys_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job` (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT 'タスク名',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT 'タスクグループ名',
  `invoke_target` varchar(500) NOT NULL COMMENT '呼び出し先文字列',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron式の実行',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '計画実行エラー・ポリシー（1今すぐ実行 21回実行 3実行を破棄）',
  `concurrent` char(1) DEFAULT '1' COMMENT '同時実行か（0許可 1禁止）',
  `status` char(1) DEFAULT '0' COMMENT 'ステータス（0正常 1一時停止）',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  `remark` varchar(500) DEFAULT '' COMMENT '備考信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='スケジュールタスクスケジュール';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job`
--

LOCK TABLES `sys_job` WRITE;
/*!40000 ALTER TABLE `sys_job` DISABLE KEYS */;
INSERT INTO `sys_job` VALUES (1,'システムデフォルト（パラメータなし）','DEFAULT','ryTask.ryNoParams','0/10 * * * * ?','3','1','1','admin','2024-05-29 16:50:23','',NULL,''),(2,'システムデフォルト（パラメータあり）','DEFAULT','ryTask.ryParams(\'ry\')','0/15 * * * * ?','3','1','1','admin','2024-05-29 16:50:23','',NULL,''),(3,'システムデフォルト（マルチパラメータ）','DEFAULT','ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)','0/20 * * * * ?','3','1','1','admin','2024-05-29 16:50:23','',NULL,'');
/*!40000 ALTER TABLE `sys_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job_log`
--

DROP TABLE IF EXISTS `sys_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `job_name` varchar(64) NOT NULL COMMENT 'タスク名',
  `job_group` varchar(64) NOT NULL COMMENT 'タスクグループ名',
  `invoke_target` varchar(500) NOT NULL COMMENT '呼び出し先文字列',
  `job_message` varchar(500) DEFAULT NULL COMMENT 'ログ情報',
  `status` char(1) DEFAULT '0' COMMENT '実行ステータス（0正常 1失敗）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '例外情報',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='スケジュールタスクスケジュールログテーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job_log`
--

LOCK TABLES `sys_job_log` WRITE;
/*!40000 ALTER TABLE `sys_job_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_job_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_logininfor`
--

DROP TABLE IF EXISTS `sys_logininfor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'アクセスID',
  `login_name` varchar(50) DEFAULT '' COMMENT 'ログインID',
  `ipaddr` varchar(128) DEFAULT '' COMMENT 'ログインIP',
  `login_location` varchar(255) DEFAULT '' COMMENT 'ログイン場所',
  `browser` varchar(50) DEFAULT '' COMMENT 'ブラウザのタイプ',
  `os` varchar(50) DEFAULT '' COMMENT 'OS',
  `status` char(1) DEFAULT '0' COMMENT 'ログインステータス（0成功 1失敗）',
  `msg` varchar(255) DEFAULT '' COMMENT 'ヒントメッセージ',
  `login_time` datetime DEFAULT NULL COMMENT 'アクセス時間',
  PRIMARY KEY (`info_id`),
  KEY `idx_sys_logininfor_s` (`status`),
  KEY `idx_sys_logininfor_lt` (`login_time`)
) ENGINE=InnoDB AUTO_INCREMENT=266 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='システムアクセスレコード';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_logininfor`
--

LOCK TABLES `sys_logininfor` WRITE;
/*!40000 ALTER TABLE `sys_logininfor` DISABLE KEYS */;
INSERT INTO `sys_logininfor` VALUES (100,'admin','127.0.0.1','内网IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 12:14:34'),(101,'admin','127.0.0.1','内网IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 12:29:34'),(102,'admin','127.0.0.1','内网IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 12:35:58'),(103,'admin','127.0.0.1','内网IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 12:42:57'),(104,'admin','127.0.0.1','内网IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 12:47:06'),(105,'admin','127.0.0.1','内网IP','Chrome 12','Windows 10','1','密码输入错误1次','2024-06-24 12:49:51'),(106,'admin','127.0.0.1','内网IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 12:49:58'),(107,'admin','127.0.0.1','内网IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 12:54:29'),(108,'admin','127.0.0.1','内网IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 12:57:03'),(109,'admin','127.0.0.1','内网IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 13:00:18'),(110,'admin','127.0.0.1','内网IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 13:04:08'),(111,'admin','127.0.0.1','内网IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 13:13:14'),(112,'admin','127.0.0.1','内网IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 13:15:22'),(113,'admin','127.0.0.1','内网IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 13:17:25'),(114,'admin','127.0.0.1','内网IP','Chrome 12','Windows 10','0','退出成功','2024-06-24 13:17:27'),(115,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 13:19:29'),(116,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','退出成功','2024-06-24 13:19:34'),(117,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 13:21:30'),(118,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','退出成功','2024-06-24 13:21:35'),(119,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 13:23:39'),(120,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 13:26:31'),(121,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 13:29:20'),(122,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 13:32:06'),(123,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 13:33:03'),(124,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 13:34:06'),(125,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-06-24 13:47:20'),(126,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-07-29 09:32:20'),(127,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','1','密码输入错误1次','2024-07-29 09:37:33'),(128,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-07-29 09:37:38'),(129,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-07-29 09:39:59'),(130,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-07-29 09:50:47'),(131,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-07-29 10:02:52'),(132,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-07-29 10:08:33'),(133,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-07-29 11:00:01'),(134,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-07-29 11:07:11'),(135,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-07-29 11:23:22'),(136,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-07-29 11:33:59'),(137,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-07-29 11:36:18'),(138,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-07-29 11:39:56'),(139,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-07-29 11:41:52'),(140,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-07-29 11:45:37'),(141,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 10:25:58'),(142,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 10:35:52'),(143,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 10:45:51'),(144,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 10:50:30'),(145,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 10:57:30'),(146,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 11:01:33'),(147,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 11:18:00'),(148,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 11:20:28'),(149,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 11:23:37'),(150,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 11:26:19'),(151,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 11:29:14'),(152,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 11:31:03'),(153,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 11:37:00'),(154,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 11:38:32'),(155,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 11:39:15'),(156,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 11:41:58'),(157,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 11:42:43'),(158,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 11:50:44'),(159,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 11:52:12'),(160,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 12:05:57'),(161,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 12:10:21'),(162,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 12:18:24'),(163,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 12:19:15'),(164,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 12:22:27'),(165,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 12:26:11'),(166,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 12:33:00'),(167,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 12:44:09'),(168,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 12:46:23'),(169,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 12:51:41'),(170,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 12:56:12'),(171,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 13:01:22'),(172,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 13:11:30'),(173,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 13:13:24'),(174,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 13:16:12'),(175,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 13:19:37'),(176,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 13:23:02'),(177,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 13:28:57'),(178,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 13:31:13'),(179,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 13:37:57'),(180,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 13:43:50'),(181,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 15:20:36'),(182,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 15:31:53'),(183,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 15:36:08'),(184,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 15:39:31'),(185,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 15:43:50'),(186,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 15:56:07'),(187,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 15:58:44'),(188,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-08 16:10:27'),(189,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-19 13:24:03'),(190,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-19 13:32:46'),(191,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-19 13:35:40'),(192,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-19 13:38:08'),(193,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-19 13:59:32'),(194,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-19 14:14:14'),(195,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-19 14:16:33'),(196,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-20 12:11:12'),(197,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-21 10:41:02'),(198,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-21 10:44:22'),(199,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-21 13:29:13'),(200,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-21 13:36:24'),(201,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-21 13:39:56'),(202,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-21 13:43:30'),(203,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-21 13:49:18'),(204,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-21 13:50:35'),(205,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-22 10:51:14'),(206,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-22 10:57:57'),(207,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-22 10:59:14'),(208,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-22 11:00:28'),(209,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-22 11:03:51'),(210,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-22 11:05:32'),(211,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-22 11:07:28'),(212,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-22 11:10:52'),(213,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-22 11:11:41'),(214,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-22 11:13:08'),(215,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-22 11:13:57'),(216,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 08:40:22'),(217,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 08:45:38'),(218,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 08:50:17'),(219,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 09:00:00'),(220,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 09:32:35'),(221,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 09:36:09'),(222,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 09:49:39'),(223,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 09:55:49'),(224,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 09:59:28'),(225,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 10:05:23'),(226,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 10:07:57'),(227,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 10:10:12'),(228,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 10:17:33'),(229,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 10:35:46'),(230,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 10:38:43'),(231,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 15:36:19'),(232,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 15:47:05'),(233,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 15:53:27'),(234,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-08-27 15:58:38'),(235,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-09-04 14:23:32'),(236,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-09-04 15:30:43'),(237,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-09-04 15:41:20'),(238,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-09-04 15:51:25'),(239,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-09-04 15:54:21'),(240,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-09-12 09:54:51'),(241,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-10-16 14:40:12'),(242,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-10-16 16:47:19'),(243,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-10-16 16:50:44'),(244,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-10-16 16:58:26'),(245,'admin','127.0.0.1','内部IP','Chrome 12','Windows 10','0','登录成功','2024-10-16 17:04:44'),(246,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-11-08 08:43:47'),(247,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 10:50:46'),(248,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 10:53:01'),(249,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 10:54:33'),(250,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 11:00:01'),(251,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 11:02:30'),(252,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 11:03:49'),(253,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 11:06:57'),(254,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 11:40:21'),(255,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 11:42:48'),(256,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 11:46:04'),(257,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 11:51:20'),(258,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 11:56:41'),(259,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 12:07:27'),(260,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 12:10:55'),(261,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 13:24:41'),(262,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 13:59:21'),(263,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 14:18:26'),(264,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 14:30:05'),(265,'admin','127.0.0.1','内部IP','Chrome 13','Windows 10','0','登录成功','2024-12-18 14:55:45');
/*!40000 ALTER TABLE `sys_logininfor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'メニューID',
  `menu_name` varchar(50) NOT NULL COMMENT 'メニュー名',
  `parent_id` bigint DEFAULT '0' COMMENT '親メニューID',
  `order_num` int DEFAULT '0' COMMENT '表示順序',
  `url` varchar(200) DEFAULT '#' COMMENT 'リクエストアドレス',
  `target` varchar(20) DEFAULT '' COMMENT '開く方法（menuItemタブ menuBlank新しいウィンドウ）',
  `menu_type` char(1) DEFAULT '' COMMENT 'メニュー種類（Mディレクトリ Cメニュー Fボタン）',
  `visible` char(1) DEFAULT '0' COMMENT 'メニュー状態（0表示 1非表示）',
  `is_refresh` char(1) DEFAULT '1' COMMENT 'リフレッシュかどうか（0リフレッシュ 1リフレッシュしない）',
  `perms` varchar(100) DEFAULT NULL COMMENT '権限ID',
  `icon` varchar(100) DEFAULT '#' COMMENT 'メニューアイコン',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  `remark` varchar(500) DEFAULT '' COMMENT '備考',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='メニュー権限テーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'システム管理',0,1,'#','','M','0','1','','fa fa-gear','admin','2024-05-29 16:50:21','',NULL,'システム管理ディレクトリ'),(2,'システム監視',0,2,'#','','M','0','1','','fa fa-video-camera','admin','2024-05-29 16:50:21','',NULL,'システム監視ディレクトリ'),(3,'システムツール',0,3,'#','','M','0','1','','fa fa-bars','admin','2024-05-29 16:50:21','',NULL,'システムツールディレクトリ'),(4,'EBS公式サイト',0,4,'http://www.ebskk.com','menuBlank','C','0','1','','fa fa-location-arrow','admin','2024-05-29 16:50:21','',NULL,'EBS公式サイト'),(100,'ユーザー管理',1,1,'/system/user','','C','0','1','system:user:view','fa fa-user-o','admin','2024-05-29 16:50:21','',NULL,'ユーザー管理メニュー'),(101,'ロール管理',1,2,'/system/role','','C','0','1','system:role:view','fa fa-user-secret','admin','2024-05-29 16:50:21','',NULL,'ロール管理メニュー'),(102,'メニュー管理',1,3,'/system/menu','','C','0','1','system:menu:view','fa fa-th-list','admin','2024-05-29 16:50:21','',NULL,'メニュー管理メニュー'),(103,'部門管理',1,4,'/system/dept','','C','0','1','system:dept:view','fa fa-outdent','admin','2024-05-29 16:50:21','',NULL,'部門管理メニュー'),(104,'ポスト管理',1,5,'/system/post','','C','0','1','system:post:view','fa fa-address-card-o','admin','2024-05-29 16:50:21','',NULL,'ポスト管理メニュー'),(105,'辞書管理',1,6,'/system/dict','','C','0','1','system:dict:view','fa fa-bookmark-o','admin','2024-05-29 16:50:21','',NULL,'辞書管理メニュー'),(106,'パラメータ設定',1,7,'/system/config','','C','0','1','system:config:view','fa fa-sun-o','admin','2024-05-29 16:50:21','',NULL,'パラメータ設定メニュー'),(107,'お知らせ',1,8,'/system/notice','','C','0','1','system:notice:view','fa fa-bullhorn','admin','2024-05-29 16:50:21','',NULL,'お知らせメニュー'),(108,'ログ管理',1,9,'#','','M','0','1','','fa fa-pencil-square-o','admin','2024-05-29 16:50:21','',NULL,'ログ管理メニュー'),(109,'オンラインユーザー',2,1,'/monitor/online','','C','0','1','monitor:online:view','fa fa-user-circle','admin','2024-05-29 16:50:21','',NULL,'オンラインユーザーメニュー'),(110,'タイミングタスク',2,2,'/monitor/job','','C','0','1','monitor:job:view','fa fa-tasks','admin','2024-05-29 16:50:21','',NULL,'タイミングタスクメニュー'),(111,'データ監視',2,3,'/monitor/data','','C','0','1','monitor:data:view','fa fa-bug','admin','2024-05-29 16:50:21','',NULL,'データ監視メニュー'),(112,'サービス監視',2,4,'/monitor/server','','C','0','1','monitor:server:view','fa fa-server','admin','2024-05-29 16:50:21','',NULL,'サービス監視メニュー'),(113,'キャッシュ監視',2,5,'/monitor/cache','','C','0','1','monitor:cache:view','fa fa-cube','admin','2024-05-29 16:50:21','',NULL,'キャッシュ監視メニュー'),(114,'フォーム構築',3,1,'/tool/build','','C','0','1','tool:build:view','fa fa-wpforms','admin','2024-05-29 16:50:21','',NULL,'フォーム構築メニュー'),(115,'コード生成',3,2,'/tool/gen','','C','0','1','tool:gen:view','fa fa-code','admin','2024-05-29 16:50:21','',NULL,'コード生成メニュー'),(116,'システムインタフェース',3,3,'/tool/swagger','','C','0','1','tool:swagger:view','fa fa-gg','admin','2024-05-29 16:50:21','',NULL,'システムインタフェースメニュー'),(500,'操作ログ',108,1,'/monitor/operlog','','C','0','1','monitor:operlog:view','fa fa-address-book','admin','2024-05-29 16:50:21','',NULL,'操作ログメニュー'),(501,'ログインログ',108,2,'/monitor/logininfor','','C','0','1','monitor:logininfor:view','fa fa-file-image-o','admin','2024-05-29 16:50:21','',NULL,'ログインログメニュー'),(1000,'ユーザー検索',100,1,'#','','F','0','1','system:user:list','#','admin','2024-05-29 16:50:21','',NULL,''),(1001,'ユーザー追加',100,2,'#','','F','0','1','system:user:add','#','admin','2024-05-29 16:50:21','',NULL,''),(1002,'ユーザー変更',100,3,'#','','F','0','1','system:user:edit','#','admin','2024-05-29 16:50:21','',NULL,''),(1003,'ユーザー削除',100,4,'#','','F','0','1','system:user:remove','#','admin','2024-05-29 16:50:21','',NULL,''),(1004,'ユーザークスポート',100,5,'#','','F','0','1','system:user:export','#','admin','2024-05-29 16:50:21','',NULL,''),(1005,'ユーザーインポート',100,6,'#','','F','0','1','system:user:import','#','admin','2024-05-29 16:50:21','',NULL,''),(1006,'パスワードリセット',100,7,'#','','F','0','1','system:user:resetPwd','#','admin','2024-05-29 16:50:21','',NULL,''),(1007,'ロール検索',101,1,'#','','F','0','1','system:role:list','#','admin','2024-05-29 16:50:21','',NULL,''),(1008,'ロール追加',101,2,'#','','F','0','1','system:role:add','#','admin','2024-05-29 16:50:21','',NULL,''),(1009,'ロール変更',101,3,'#','','F','0','1','system:role:edit','#','admin','2024-05-29 16:50:21','',NULL,''),(1010,'ロール削除',101,4,'#','','F','0','1','system:role:remove','#','admin','2024-05-29 16:50:21','',NULL,''),(1011,'ロールクスポート',101,5,'#','','F','0','1','system:role:export','#','admin','2024-05-29 16:50:21','',NULL,''),(1012,'メニュー検索',102,1,'#','','F','0','1','system:menu:list','#','admin','2024-05-29 16:50:21','',NULL,''),(1013,'メニュー追加',102,2,'#','','F','0','1','system:menu:add','#','admin','2024-05-29 16:50:21','',NULL,''),(1014,'メニュー変更',102,3,'#','','F','0','1','system:menu:edit','#','admin','2024-05-29 16:50:21','',NULL,''),(1015,'メニュー削除',102,4,'#','','F','0','1','system:menu:remove','#','admin','2024-05-29 16:50:21','',NULL,''),(1016,'部門削除',103,1,'#','','F','0','1','system:dept:list','#','admin','2024-05-29 16:50:21','',NULL,''),(1017,'部門追加',103,2,'#','','F','0','1','system:dept:add','#','admin','2024-05-29 16:50:21','',NULL,''),(1018,'部門変更',103,3,'#','','F','0','1','system:dept:edit','#','admin','2024-05-29 16:50:21','',NULL,''),(1019,'部門削除',103,4,'#','','F','0','1','system:dept:remove','#','admin','2024-05-29 16:50:21','',NULL,''),(1020,'ポスト検索',104,1,'#','','F','0','1','system:post:list','#','admin','2024-05-29 16:50:21','',NULL,''),(1021,'ポスト追加',104,2,'#','','F','0','1','system:post:add','#','admin','2024-05-29 16:50:21','',NULL,''),(1022,'ポスト変更',104,3,'#','','F','0','1','system:post:edit','#','admin','2024-05-29 16:50:21','',NULL,''),(1023,'ポスト削除',104,4,'#','','F','0','1','system:post:remove','#','admin','2024-05-29 16:50:21','',NULL,''),(1024,'ポストエクスポート',104,5,'#','','F','0','1','system:post:export','#','admin','2024-05-29 16:50:21','',NULL,''),(1025,'辞書検索',105,1,'#','','F','0','1','system:dict:list','#','admin','2024-05-29 16:50:21','',NULL,''),(1026,'辞書追加',105,2,'#','','F','0','1','system:dict:add','#','admin','2024-05-29 16:50:21','',NULL,''),(1027,'辞書変更',105,3,'#','','F','0','1','system:dict:edit','#','admin','2024-05-29 16:50:21','',NULL,''),(1028,'辞書削除',105,4,'#','','F','0','1','system:dict:remove','#','admin','2024-05-29 16:50:21','',NULL,''),(1029,'辞書エクスポート',105,5,'#','','F','0','1','system:dict:export','#','admin','2024-05-29 16:50:21','',NULL,''),(1030,'パラメータ検索',106,1,'#','','F','0','1','system:config:list','#','admin','2024-05-29 16:50:21','',NULL,''),(1031,'パラメータ追加',106,2,'#','','F','0','1','system:config:add','#','admin','2024-05-29 16:50:21','',NULL,''),(1032,'パラメータ変更',106,3,'#','','F','0','1','system:config:edit','#','admin','2024-05-29 16:50:21','',NULL,''),(1033,'パラメータ削除',106,4,'#','','F','0','1','system:config:remove','#','admin','2024-05-29 16:50:21','',NULL,''),(1034,'パラメータエクスポート',106,5,'#','','F','0','1','system:config:export','#','admin','2024-05-29 16:50:21','',NULL,''),(1035,'公告検索',107,1,'#','','F','0','1','system:notice:list','#','admin','2024-05-29 16:50:22','',NULL,''),(1036,'公告追加',107,2,'#','','F','0','1','system:notice:add','#','admin','2024-05-29 16:50:22','',NULL,''),(1037,'公告変更',107,3,'#','','F','0','1','system:notice:edit','#','admin','2024-05-29 16:50:22','',NULL,''),(1038,'公告削除',107,4,'#','','F','0','1','system:notice:remove','#','admin','2024-05-29 16:50:22','',NULL,''),(1039,'操作検索',500,1,'#','','F','0','1','monitor:operlog:list','#','admin','2024-05-29 16:50:22','',NULL,''),(1040,'操作削除',500,2,'#','','F','0','1','monitor:operlog:remove','#','admin','2024-05-29 16:50:22','',NULL,''),(1041,'詳細',500,3,'#','','F','0','1','monitor:operlog:detail','#','admin','2024-05-29 16:50:22','',NULL,''),(1042,'ログエクスポート',500,4,'#','','F','0','1','monitor:operlog:export','#','admin','2024-05-29 16:50:22','',NULL,''),(1043,'ログインクエリー',501,1,'#','','F','0','1','monitor:logininfor:list','#','admin','2024-05-29 16:50:22','',NULL,''),(1044,'ログイン削除',501,2,'#','','F','0','1','monitor:logininfor:remove','#','admin','2024-05-29 16:50:22','',NULL,''),(1045,'ログエクスポート',501,3,'#','','F','0','1','monitor:logininfor:export','#','admin','2024-05-29 16:50:22','',NULL,''),(1046,'アカウントのロック解除',501,4,'#','','F','0','1','monitor:logininfor:unlock','#','admin','2024-05-29 16:50:22','',NULL,''),(1047,'オンライン照会',109,1,'#','','F','0','1','monitor:online:list','#','admin','2024-05-29 16:50:22','',NULL,''),(1048,'一括強制終了',109,2,'#','','F','0','1','monitor:online:batchForceLogout','#','admin','2024-05-29 16:50:22','',NULL,''),(1049,'シングルストリップ強制終了',109,3,'#','','F','0','1','monitor:online:forceLogout','#','admin','2024-05-29 16:50:22','',NULL,''),(1050,'タスク検索',110,1,'#','','F','0','1','monitor:job:list','#','admin','2024-05-29 16:50:22','',NULL,''),(1051,'タスク追加',110,2,'#','','F','0','1','monitor:job:add','#','admin','2024-05-29 16:50:22','',NULL,''),(1052,'タスク変更',110,3,'#','','F','0','1','monitor:job:edit','#','admin','2024-05-29 16:50:22','',NULL,''),(1053,'タスク削除',110,4,'#','','F','0','1','monitor:job:remove','#','admin','2024-05-29 16:50:22','',NULL,''),(1054,'ステータス変更',110,5,'#','','F','0','1','monitor:job:changeStatus','#','admin','2024-05-29 16:50:22','',NULL,''),(1055,'タスク詳細',110,6,'#','','F','0','1','monitor:job:detail','#','admin','2024-05-29 16:50:22','',NULL,''),(1056,'タスクエクスポート',110,7,'#','','F','0','1','monitor:job:export','#','admin','2024-05-29 16:50:22','',NULL,''),(1057,'検索生成',115,1,'#','','F','0','1','tool:gen:list','#','admin','2024-05-29 16:50:22','',NULL,''),(1058,'変更生成',115,2,'#','','F','0','1','tool:gen:edit','#','admin','2024-05-29 16:50:22','',NULL,''),(1059,'削除生成',115,3,'#','','F','0','1','tool:gen:remove','#','admin','2024-05-29 16:50:22','',NULL,''),(1060,'プレビューコード',115,4,'#','','F','0','1','tool:gen:preview','#','admin','2024-05-29 16:50:22','',NULL,''),(1061,'コード生成',115,5,'#','','F','0','1','tool:gen:code','#','admin','2024-05-29 16:50:22','',NULL,'');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice` (
  `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告タイトル',
  `notice_type` char(1) NOT NULL COMMENT '公告タイプ（1知らせる 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) DEFAULT '0' COMMENT '公告ステータス（0正常 1閉じる）',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  `remark` varchar(255) DEFAULT NULL COMMENT '備考',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='通知テーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
INSERT INTO `sys_notice` VALUES (1,'EBS-RFID_DEMO','2',_binary '初回','0','admin','2024-05-29 16:50:24','',NULL,'管理者');
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oper_log`
--

DROP TABLE IF EXISTS `sys_oper_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ログのプライマリ・キー',
  `title` varchar(50) DEFAULT '' COMMENT 'モジュールタイトル',
  `business_type` int DEFAULT '0' COMMENT 'ビジネス・タイプ（0その他 1追加 2変更 3削除）',
  `method` varchar(100) DEFAULT '' COMMENT 'メソッド名',
  `request_method` varchar(10) DEFAULT '' COMMENT 'リクエストモード',
  `operator_type` int DEFAULT '0' COMMENT '操作カテゴリ（0その他 1バックグラウンド・ユーザー 2携帯電話端末ユーザー）',
  `oper_name` varchar(50) DEFAULT '' COMMENT 'オペレータ',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部署名',
  `oper_url` varchar(255) DEFAULT '' COMMENT 'リクエストURL',
  `oper_ip` varchar(128) DEFAULT '' COMMENT 'ホストアドレス',
  `oper_location` varchar(255) DEFAULT '' COMMENT 'オペレーション場所',
  `oper_param` varchar(2000) DEFAULT '' COMMENT 'リクエストパラメータ',
  `json_result` varchar(2000) DEFAULT '' COMMENT 'パラメータを返す',
  `status` int DEFAULT '0' COMMENT 'オペレーションステータス（0正常 1異常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT 'エラーメッセージ',
  `oper_time` datetime DEFAULT NULL COMMENT '操作時間',
  `cost_time` bigint DEFAULT '0' COMMENT '使用時間',
  PRIMARY KEY (`oper_id`),
  KEY `idx_sys_oper_log_bt` (`business_type`),
  KEY `idx_sys_oper_log_s` (`status`),
  KEY `idx_sys_oper_log_ot` (`oper_time`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作ログテーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oper_log`
--

LOCK TABLES `sys_oper_log` WRITE;
/*!40000 ALTER TABLE `sys_oper_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_oper_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_post`
--

DROP TABLE IF EXISTS `sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_post` (
  `post_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ポストID',
  `post_code` varchar(64) NOT NULL COMMENT 'ポストコード',
  `post_name` varchar(50) NOT NULL COMMENT 'ポスト名',
  `post_sort` int NOT NULL COMMENT '表示順序',
  `status` char(1) NOT NULL COMMENT '状態（0正常 1停止）',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  `remark` varchar(500) DEFAULT NULL COMMENT '備考',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ポストテーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_post`
--

LOCK TABLES `sys_post` WRITE;
/*!40000 ALTER TABLE `sys_post` DISABLE KEYS */;
INSERT INTO `sys_post` VALUES (1,'ceo','社長',1,'0','admin','2024-05-29 16:50:21','',NULL,''),(2,'se','マネージャ',2,'0','admin','2024-05-29 16:50:21','',NULL,''),(3,'hr','HR',3,'0','admin','2024-05-29 16:50:21','',NULL,''),(4,'user','社員',4,'0','admin','2024-05-29 16:50:21','',NULL,'');
/*!40000 ALTER TABLE `sys_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ロールID',
  `role_name` varchar(30) NOT NULL COMMENT 'ロール名',
  `role_key` varchar(100) NOT NULL COMMENT 'ロール権限文字列',
  `role_sort` int NOT NULL COMMENT '表示順序',
  `data_scope` char(1) DEFAULT '1' COMMENT 'データ範囲（1：すべてデータ権限 2：カスタマイズデータ権限 3：本部門データ権限 4：本部門及び以下データ权限）',
  `status` char(1) NOT NULL COMMENT 'ロール状態（0正常 1停止）',
  `del_flag` char(1) DEFAULT '0' COMMENT '削除フラグ（0存在 2削除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  `remark` varchar(500) DEFAULT NULL COMMENT '備考',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ロール情報テーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'管理者','admin',1,'1','0','0','admin','2024-05-29 16:50:21','',NULL,'管理者'),(2,'普通','common',2,'2','0','0','admin','2024-05-29 16:50:21','',NULL,'普通');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_dept`
--

DROP TABLE IF EXISTS `sys_role_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint NOT NULL COMMENT 'ロールID',
  `dept_id` bigint NOT NULL COMMENT '部門ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ロールと部門の関連表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_dept`
--

LOCK TABLES `sys_role_dept` WRITE;
/*!40000 ALTER TABLE `sys_role_dept` DISABLE KEYS */;
INSERT INTO `sys_role_dept` VALUES (2,100),(2,101),(2,105);
/*!40000 ALTER TABLE `sys_role_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT 'ロールID',
  `menu_id` bigint NOT NULL COMMENT 'メニューID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ロールとメニューの関連表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (2,1),(2,2),(2,3),(2,4),(2,100),(2,101),(2,102),(2,103),(2,104),(2,105),(2,106),(2,107),(2,108),(2,109),(2,110),(2,111),(2,112),(2,113),(2,114),(2,115),(2,116),(2,500),(2,501),(2,1000),(2,1001),(2,1002),(2,1003),(2,1004),(2,1005),(2,1006),(2,1007),(2,1008),(2,1009),(2,1010),(2,1011),(2,1012),(2,1013),(2,1014),(2,1015),(2,1016),(2,1017),(2,1018),(2,1019),(2,1020),(2,1021),(2,1022),(2,1023),(2,1024),(2,1025),(2,1026),(2,1027),(2,1028),(2,1029),(2,1030),(2,1031),(2,1032),(2,1033),(2,1034),(2,1035),(2,1036),(2,1037),(2,1038),(2,1039),(2,1040),(2,1041),(2,1042),(2,1043),(2,1044),(2,1045),(2,1046),(2,1047),(2,1048),(2,1049),(2,1050),(2,1051),(2,1052),(2,1053),(2,1054),(2,1055),(2,1056),(2,1057),(2,1058),(2,1059),(2,1060),(2,1061);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ユーザーID',
  `dept_id` bigint DEFAULT NULL COMMENT '部門ID',
  `login_name` varchar(30) NOT NULL COMMENT 'ログインアカウント',
  `user_name` varchar(30) DEFAULT '' COMMENT 'ユーザーのニックネーム',
  `user_type` varchar(2) DEFAULT '00' COMMENT 'ユーザー・タイプ（00システム 01登録ユーザ）',
  `email` varchar(50) DEFAULT '' COMMENT 'ユーザーメール',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '携帯番号',
  `sex` char(1) DEFAULT '0' COMMENT 'ユーザーの性別（0男 1女  2不明）',
  `avatar` varchar(100) DEFAULT '' COMMENT 'アバターパス',
  `password` varchar(50) DEFAULT '' COMMENT 'パスワード',
  `salt` varchar(20) DEFAULT '' COMMENT '塩暗号化',
  `status` char(1) DEFAULT '0' COMMENT 'アカウントステータス（0正常 1停止）',
  `del_flag` char(1) DEFAULT '0' COMMENT '削除フラグ（0存在 2削除）',
  `login_ip` varchar(128) DEFAULT '' COMMENT '最終ログインIP',
  `login_date` datetime DEFAULT NULL COMMENT '最終ログイン時間',
  `pwd_update_date` datetime DEFAULT NULL COMMENT 'パスワードの最終更新日時',
  `create_by` varchar(64) DEFAULT '' COMMENT '作成者',
  `create_time` datetime DEFAULT NULL COMMENT '作成時間',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  `remark` varchar(500) DEFAULT NULL COMMENT '備考',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ユーザー情報テーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,103,'admin','EBS','00','kaku@ebskk.com','15888888888','1','','29c67a30398638269fe600f73a054934','111111','0','0','127.0.0.1','2024-12-18 14:55:46',NULL,'admin','2024-05-29 16:50:21','','2024-12-18 14:55:45','管理者'),(2,105,'ebs','EBS','00','dl_guofy@ebskk.com','15666666666','1','','8e6d98b90472783cc73c17047ddccf36','222222','0','0','127.0.0.1',NULL,NULL,'admin','2024-05-29 16:50:21','',NULL,'テスト');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_online`
--

DROP TABLE IF EXISTS `sys_user_online`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_online` (
  `sessionId` varchar(50) NOT NULL DEFAULT '' COMMENT 'ユーザーセッションid',
  `login_name` varchar(50) DEFAULT '' COMMENT 'ログインID',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部署名',
  `ipaddr` varchar(128) DEFAULT '' COMMENT 'ログインIP',
  `login_location` varchar(255) DEFAULT '' COMMENT 'ログイン場所',
  `browser` varchar(50) DEFAULT '' COMMENT 'ブラウザのタイプ',
  `os` varchar(50) DEFAULT '' COMMENT 'OS',
  `status` varchar(10) DEFAULT '' COMMENT 'オンライン状態on_lineオンラインoff_lineオフライン',
  `start_timestamp` datetime DEFAULT NULL COMMENT 'session作成時間',
  `last_access_time` datetime DEFAULT NULL COMMENT 'session最終アクセス時間',
  `expire_time` int DEFAULT '0' COMMENT 'タイムアウト時間、分単位',
  PRIMARY KEY (`sessionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='オンラインユーザーレコード';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_online`
--

LOCK TABLES `sys_user_online` WRITE;
/*!40000 ALTER TABLE `sys_user_online` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_online` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_post`
--

DROP TABLE IF EXISTS `sys_user_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_post` (
  `user_id` bigint NOT NULL COMMENT 'ユーザーID',
  `post_id` bigint NOT NULL COMMENT 'ポストID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ユーザーとポストの関連表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_post`
--

LOCK TABLES `sys_user_post` WRITE;
/*!40000 ALTER TABLE `sys_user_post` DISABLE KEYS */;
INSERT INTO `sys_user_post` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `sys_user_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT 'ユーザーID',
  `role_id` bigint NOT NULL COMMENT 'ロールID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ユーザーとロールの関連付けテーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-20  8:25:55
