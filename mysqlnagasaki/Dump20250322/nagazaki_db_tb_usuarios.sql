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
-- Table structure for table `tb_usuarios`
--

DROP TABLE IF EXISTS `tb_usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_usuarios` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `id_funcionario` int DEFAULT NULL,
  `email_usuario` varchar(145) DEFAULT NULL,
  `nome_usuario` varchar(255) DEFAULT NULL,
  `senha_usuario` varchar(255) DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `id_grupousuario` int DEFAULT NULL,
  `ativo` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_usuarios`
--

LOCK TABLES `tb_usuarios` WRITE;
/*!40000 ALTER TABLE `tb_usuarios` DISABLE KEYS */;
INSERT INTO `tb_usuarios` VALUES (1,NULL,'roberto@666.com.br','administrador','admin','2025-03-16 23:07:48','2025-03-17 01:34:35',NULL,1,'S'),(2,NULL,'danilo.silva@provider-it.com.br','visitante','123456','2024-09-16 02:16:54',NULL,'2025-03-17 09:04:26',6,'N'),(3,NULL,NULL,'Bob','Password','2024-08-21 00:00:00',NULL,'2025-03-17 09:00:13',1,'N'),(4,2,'danilo.silva@provider-it.com.br','Joao','masterteste','2024-09-14 00:17:59',NULL,'2025-03-17 09:00:10',2,'N'),(7,8,'roberto@666.com.br','falagalvao','galvao123','2025-03-16 23:07:48','2025-03-17 01:21:19','2025-03-17 08:59:58',11,'N'),(8,5,'daniloluiz.silva@actdigital.com','larissa','123456','2024-09-15 19:47:16',NULL,'2025-03-17 08:59:55',1,'N'),(10,NULL,NULL,'Bob','Password','2024-08-21 00:00:00',NULL,'2025-03-17 08:52:41',1,'N'),(11,8,'roberto@666.com.br','falagalvao','galvao123','2025-03-16 23:07:48','2025-03-17 01:19:21','2025-03-17 08:59:51',11,'N'),(12,8,'roberto@666.com.br','galvao','123456','2025-03-17 09:00:49','2025-03-17 09:09:28',NULL,7,'S'),(13,4,'teste@666.com.br','maria','123456','2025-03-17 09:01:17','2025-03-17 09:09:41',NULL,6,'S');
/*!40000 ALTER TABLE `tb_usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-22 18:18:10
