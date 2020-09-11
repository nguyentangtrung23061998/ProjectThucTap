-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: elearning
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `course` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `is_Active` bit(1) DEFAULT NULL,
  `userid` bigint(20) DEFAULT NULL,
  `createddate` timestamp NULL DEFAULT NULL,
  `modifieddate` timestamp NULL DEFAULT NULL,
  `createdby` varchar(100) DEFAULT NULL,
  `modifiedby` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`),
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1589007399525,'Condition basic 2','Perfect','english12.jpg',_binary '',1590768028203,'2020-05-09 06:56:40','2020-06-15 06:34:40',NULL,NULL),(1590859121340,'test 3','test','enlisgh_10.png',_binary '\0',1590768028203,'2020-05-30 17:18:41','2020-06-16 10:47:06',NULL,NULL),(1590859171612,'Compare Basic','This is basic','english_5.jpg',_binary '',1590768028203,'2020-05-30 17:19:32','2020-05-30 17:22:04',NULL,NULL),(1590859181221,'Compare Advance','This is advance','59665294-learn-english-red-vector-learn-english-flat-vector-learn-english-background-learn-english.jpg',_binary '\0',1590768028203,'2020-05-30 17:19:41','2020-05-30 17:22:26',NULL,NULL),(1590859200771,'Enough grammar','This is basic','english_2.jpg',_binary '',1590768028203,'2020-05-30 17:20:01','2020-05-30 17:22:42',NULL,NULL),(1592063113295,'English Easy','English is easy',NULL,_binary '\0',1590768028203,'2020-06-13 15:45:13','2020-06-13 15:53:41',NULL,NULL),(1592235937514,'Test2','ABC2',NULL,_binary '',1590768028203,'2020-06-15 15:45:38','2020-06-16 11:07:07',NULL,NULL),(1592298474292,'test ','test',NULL,_binary '',1590768028203,'2020-06-16 09:07:54',NULL,NULL,NULL),(1592298542699,'test ','test',NULL,_binary '\0',1590768028203,'2020-06-16 09:09:03',NULL,NULL,NULL),(1592298922740,'test ','test',NULL,_binary '\0',1590768028203,'2020-06-16 09:15:23',NULL,NULL,NULL);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grammar_comment`
--

DROP TABLE IF EXISTS `grammar_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `grammar_comment` (
  `id` bigint(20) NOT NULL,
  `commentname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `grammar_list_content_id` bigint(20) NOT NULL,
  `createddate` timestamp NULL DEFAULT NULL,
  `modifieddate` timestamp NULL DEFAULT NULL,
  `createdby` varchar(100) DEFAULT NULL,
  `modifiedby` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `grammar_list_content_id` (`grammar_list_content_id`),
  CONSTRAINT `grammar_comment_ibfk_1` FOREIGN KEY (`grammar_list_content_id`) REFERENCES `grammar_list_content` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grammar_comment`
--

LOCK TABLES `grammar_comment` WRITE;
/*!40000 ALTER TABLE `grammar_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `grammar_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grammar_content`
--

DROP TABLE IF EXISTS `grammar_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `grammar_content` (
  `id` bigint(20) NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `createddate` timestamp NULL DEFAULT NULL,
  `modifieddate` timestamp NULL DEFAULT NULL,
  `createdby` varchar(100) DEFAULT NULL,
  `modifiedby` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grammar_content`
--

LOCK TABLES `grammar_content` WRITE;
/*!40000 ALTER TABLE `grammar_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `grammar_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grammar_exam`
--

DROP TABLE IF EXISTS `grammar_exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `grammar_exam` (
  `id` bigint(20) NOT NULL,
  `question` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `answerfirst` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `answersecond` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `answerthird` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `answerfourth` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `correctanswer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `grammar_list_id` bigint(20) NOT NULL,
  `createddate` timestamp NULL DEFAULT NULL,
  `modifieddate` timestamp NULL DEFAULT NULL,
  `createdby` varchar(100) DEFAULT NULL,
  `modifiedby` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `grammar_list_id` (`grammar_list_id`),
  CONSTRAINT `grammar_exam_ibfk_1` FOREIGN KEY (`grammar_list_id`) REFERENCES `grammar_list` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grammar_exam`
--

LOCK TABLES `grammar_exam` WRITE;
/*!40000 ALTER TABLE `grammar_exam` DISABLE KEYS */;
/*!40000 ALTER TABLE `grammar_exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grammar_list`
--

DROP TABLE IF EXISTS `grammar_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `grammar_list` (
  `id` bigint(20) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `createddate` timestamp NULL DEFAULT NULL,
  `modifieddate` timestamp NULL DEFAULT NULL,
  `createdby` varchar(100) DEFAULT NULL,
  `modifiedby` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grammar_list`
--

LOCK TABLES `grammar_list` WRITE;
/*!40000 ALTER TABLE `grammar_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `grammar_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grammar_list_content`
--

DROP TABLE IF EXISTS `grammar_list_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `grammar_list_content` (
  `id` bigint(20) NOT NULL,
  `grammar_content_id` bigint(20) NOT NULL,
  `grammar_list_id` bigint(20) NOT NULL,
  `diem` int(11) DEFAULT NULL,
  `createddate` timestamp NULL DEFAULT NULL,
  `modifieddate` timestamp NULL DEFAULT NULL,
  `createdby` varchar(100) DEFAULT NULL,
  `modifiedby` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `grammar_content_id` (`grammar_content_id`),
  KEY `grammar_list_id` (`grammar_list_id`),
  CONSTRAINT `grammar_list_content_ibfk_1` FOREIGN KEY (`grammar_content_id`) REFERENCES `grammar_content` (`id`),
  CONSTRAINT `grammar_list_content_ibfk_2` FOREIGN KEY (`grammar_list_id`) REFERENCES `grammar_list` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grammar_list_content`
--

LOCK TABLES `grammar_list_content` WRITE;
/*!40000 ALTER TABLE `grammar_list_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `grammar_list_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecture`
--

DROP TABLE IF EXISTS `lecture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `lecture` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `video` varchar(255) DEFAULT NULL,
  `audio` varchar(255) DEFAULT NULL,
  `document` varchar(255) DEFAULT NULL,
  `courseid` bigint(20) DEFAULT NULL,
  `createddate` timestamp NULL DEFAULT NULL,
  `modifieddate` timestamp NULL DEFAULT NULL,
  `createdby` varchar(100) DEFAULT NULL,
  `modifiedby` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_LECTURE_COURSE` (`courseid`),
  CONSTRAINT `FK_LECTURE_COURSE` FOREIGN KEY (`courseid`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecture`
--

LOCK TABLES `lecture` WRITE;
/*!40000 ALTER TABLE `lecture` DISABLE KEYS */;
INSERT INTO `lecture` VALUES (1592068099890,'English advance v2','Good',NULL,NULL,NULL,NULL,1592063113295,'2020-06-13 17:08:20',NULL,NULL,NULL),(1592374487015,'Lecture 01','Good',NULL,NULL,NULL,NULL,1590859200771,'2020-06-17 06:14:47',NULL,NULL,NULL),(1592374544737,'Lecture 02','Perfect',NULL,NULL,NULL,NULL,1590859200771,'2020-06-17 06:15:45',NULL,NULL,NULL),(1592374551443,'Lecture 03','Perfect',NULL,NULL,NULL,NULL,1590859200771,'2020-06-17 06:15:51',NULL,NULL,NULL),(1592374555865,'Lecture 04','Perfect',NULL,NULL,NULL,NULL,1590859200771,'2020-06-17 06:15:56',NULL,NULL,NULL),(1592374611603,'Lecture 05','Perfect',NULL,NULL,NULL,NULL,1592063113295,'2020-06-17 06:16:52',NULL,NULL,NULL),(1592374617353,'Lecture 06','Perfect',NULL,NULL,NULL,NULL,1592063113295,'2020-06-17 06:16:57',NULL,NULL,NULL),(1592374624279,'Lecture 01','Perfect',NULL,NULL,NULL,NULL,1592235937514,'2020-06-17 06:17:04',NULL,NULL,NULL),(1592374628243,'Lecture 02','Perfect',NULL,NULL,NULL,NULL,1592235937514,'2020-06-17 06:17:08',NULL,NULL,NULL);
/*!40000 ALTER TABLE `lecture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions_bank`
--

DROP TABLE IF EXISTS `questions_bank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `questions_bank` (
  `id` bigint(20) NOT NULL,
  `question` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `answerfirst` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `answersecond` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `answerthird` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `answerfourth` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `correctanswer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `createddate` timestamp NULL DEFAULT NULL,
  `modifieddate` timestamp NULL DEFAULT NULL,
  `createdby` varchar(100) DEFAULT NULL,
  `modifiedby` varchar(100) DEFAULT NULL,
  `lectureid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exam_course_fk` (`lectureid`),
  CONSTRAINT `questions_bank_lecture_fk` FOREIGN KEY (`lectureid`) REFERENCES `lecture` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions_bank`
--

LOCK TABLES `questions_bank` WRITE;
/*!40000 ALTER TABLE `questions_bank` DISABLE KEYS */;
INSERT INTO `questions_bank` VALUES (1592375448430,'Question 23','Answer one','Answer two','Answer three','Answer four','A','2020-06-17 06:30:48','2020-06-17 06:53:47',NULL,NULL,1592068099890);
/*!40000 ALTER TABLE `questions_bank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `rolename` varchar(255) NOT NULL,
  `createddate` timestamp NULL DEFAULT NULL,
  `modifieddate` timestamp NULL DEFAULT NULL,
  `createdby` varchar(100) DEFAULT NULL,
  `modifiedby` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_epk9im9l9q67xmwi4hbed25do` (`rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN',NULL,NULL,NULL,NULL),(2,'ROLE_STUDENT',NULL,NULL,NULL,NULL),(3,'ROLE_TEACHER',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_course_enrolement`
--

DROP TABLE IF EXISTS `student_course_enrolement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `student_course_enrolement` (
  `userid` bigint(20) NOT NULL,
  `courseid` bigint(20) NOT NULL,
  PRIMARY KEY (`userid`,`courseid`),
  KEY `userid` (`userid`),
  KEY `courseid` (`courseid`),
  CONSTRAINT `student_course_enrolement_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`id`),
  CONSTRAINT `student_course_enrolement_ibfk_2` FOREIGN KEY (`courseid`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_course_enrolement`
--

LOCK TABLES `student_course_enrolement` WRITE;
/*!40000 ALTER TABLE `student_course_enrolement` DISABLE KEYS */;
INSERT INTO `student_course_enrolement` VALUES (1592300722946,1589007399525),(1592300739996,1590859121340),(1592300773706,1590859121340),(1592300773706,1592235937514),(1592300773706,1592298474292);
/*!40000 ALTER TABLE `student_course_enrolement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `createddate` timestamp NULL DEFAULT NULL,
  `modifieddate` timestamp NULL DEFAULT NULL,
  `createdby` varchar(100) DEFAULT NULL,
  `modifiedby` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1590768028203,'admin','$2a$10$ca1BxF6YDDGz.DJ21jOPCO7T2yfe1IVpazM3gpbqCJzcNYCobuwoq','admin@gmail.com','','Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTU5MjM3NjgxMywiZXhwIjoxNTkyMzc4NjEzfQ.WP35oieiXYTUQh2H9drft2BukVZwVBNx3xeWIty2Ue0y1bGc_HOpefHlE1UMjMsvdPWgUTSZRZrhmzv-yRF0kQ','Nguyen Trung','2020-05-29 16:00:28','2020-06-17 06:53:33','',''),(1592300630498,'nguyentangtrung2306','$2a$10$A9ylqCisw3TMS17m9FqmRuyTch5ZJk.NpGa.UrY839f/yT0l7RaIG','nguyentangtrung11@gmail.com','','','Nguyen Tang Trung','2020-06-16 09:43:51','2020-06-16 09:43:51','',''),(1592300656405,'thanhtu','$2a$10$Iz9SFOCLaYoZ63IdmKBkPeqYwRMbWq4UHjdE6H0oJz6inRcLjjqcK','thanhtu@gmail.com','','','Thanh Tú','2020-06-16 09:44:17','2020-06-16 09:44:17','',''),(1592300685696,'quangviet','$2a$10$2a8rgiVyP/K.l00nhpUNHOqw.WIqZgVm8CLdTjtbWzaRipAXyExwa','quangviet@gmail.com','','','Qaung Viet','2020-06-16 09:44:46','2020-06-16 09:44:46','',''),(1592300722946,'trungdeptrai','$2a$10$D1YVepXmtLAAPf8axpDbkOYr/JRZ2Mv6HfJtbXPJJZrSKqTLZErgi','trungdeptrai@gmail.com','','','Qaung Viet','2020-06-16 09:45:23','2020-06-16 10:09:56','',''),(1592300739996,'thanhto','$2a$10$tYKc7CoGfiDb8uCAjTOn9uUmBGklJrOxyEkFMG20WomnjCtIXru6W','thanhto@gmail.com','','','Thanh Tố','2020-06-16 09:45:40','2020-06-16 10:47:42','',''),(1592300773706,'phuongdung','$2a$10$mJWzhVEFRq8ixxUAFUZAc.JwYGfVx5.SEqAkT.3Z/4ZLEa7FH3pvC','duongphuongdung@gmail.com','','Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwaHVvbmdkdW5nIiwiaWF0IjoxNTkyMzE4NDYxLCJleHAiOjE1OTIzMzY0NjF9.LbH4wVyWIFDAJ6uzlujMSSDNWEkAHu7jCWm8KlzDBMlxvK9N2l-ch47IDOnU1dJivsqSVOJnrCse4sZvnGPEtg','Duong Phuong Duong','2020-06-16 09:46:14','2020-06-16 14:41:01','','');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_role` (
  `userid` bigint(20) NOT NULL,
  `roleid` bigint(20) NOT NULL,
  PRIMARY KEY (`userid`,`roleid`),
  KEY `roleid` (`roleid`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`id`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`roleid`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1590768028203,1),(1592300722946,2),(1592300739996,2),(1592300773706,2),(1592300630498,3),(1592300656405,3),(1592300685696,3);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'elearning'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-17 13:55:48
