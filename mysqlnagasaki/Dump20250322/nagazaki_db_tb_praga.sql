-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: nagazaki_db
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `tb_praga`
--

DROP TABLE IF EXISTS `tb_praga`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_praga` (
  `id_praga` int NOT NULL AUTO_INCREMENT,
  `codigo_praga` int DEFAULT NULL,
  `descricao_praga` varchar(255) DEFAULT NULL,
  `nomecientifico_praga` varchar(255) DEFAULT NULL,
  `caminhofoto_praga` varchar(255) DEFAULT NULL,
  `nomefoto_praga` varchar(255) DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_praga`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_praga`
--

LOCK TABLES `tb_praga` WRITE;
/*!40000 ALTER TABLE `tb_praga` DISABLE KEYS */;
INSERT INTO `tb_praga` VALUES (1,NULL,'Ratos e Cupins',NULL,NULL,NULL,'2024-08-27 18:55:37','2025-01-14 09:13:01',NULL,'S',7),(2,NULL,'Barata',NULL,NULL,NULL,'2024-10-28 10:11:35',NULL,NULL,'S',7),(3,NULL,'Teste',NULL,NULL,NULL,'2024-10-28 10:11:41',NULL,'2024-10-28 10:38:20','N',7),(4,NULL,'Teste1',NULL,NULL,NULL,'2024-10-28 10:11:46',NULL,'2024-10-28 10:38:23','N',7),(5,NULL,'Bug',NULL,NULL,NULL,'2024-10-28 10:19:09',NULL,'2024-10-28 10:38:13','N',7),(6,NULL,'Bicho',NULL,NULL,NULL,'2024-10-28 10:19:17',NULL,'2024-10-28 10:38:26','N',7),(7,NULL,'Teste',NULL,NULL,NULL,'2024-10-28 10:50:26',NULL,'2024-10-28 10:50:30','N',7);
/*!40000 ALTER TABLE `tb_praga` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-22 18:18:08
