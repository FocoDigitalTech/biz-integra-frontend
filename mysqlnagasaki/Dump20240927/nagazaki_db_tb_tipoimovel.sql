-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: nagazaki_db
-- ------------------------------------------------------
-- Server version	8.0.39-0ubuntu0.22.04.1

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
-- Table structure for table `tb_tipoimovel`
--

DROP TABLE IF EXISTS `tb_tipoimovel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_tipoimovel` (
  `id_tipoimovel` int NOT NULL AUTO_INCREMENT,
  `descricao_tipoimovel` varchar(255) DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_tipoimovel`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_tipoimovel`
--

LOCK TABLES `tb_tipoimovel` WRITE;
/*!40000 ALTER TABLE `tb_tipoimovel` DISABLE KEYS */;
INSERT INTO `tb_tipoimovel` VALUES (2,'Galpão Comercial','2024-08-23 12:52:12',NULL,NULL,'S',1),(3,'Fazenda','2024-08-23 12:52:22',NULL,NULL,'S',1),(4,'Edificio','2024-08-23 12:52:31',NULL,NULL,'S',1),(5,'Residencia','2024-08-23 12:52:37',NULL,NULL,'S',1),(6,'Indicação','2024-08-23 17:11:07',NULL,NULL,'S',1),(7,'Escritório','2024-08-25 13:20:04',NULL,NULL,'S',2);
/*!40000 ALTER TABLE `tb_tipoimovel` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-30 15:47:07
