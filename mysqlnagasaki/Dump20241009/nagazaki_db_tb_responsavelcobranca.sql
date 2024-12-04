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
-- Table structure for table `tb_responsavelcobranca`
--

DROP TABLE IF EXISTS `tb_responsavelcobranca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_responsavelcobranca` (
  `id_responsavelcobranca` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int DEFAULT NULL,
  `nome_cobranca` varchar(255) DEFAULT NULL,
  `telefone_fixo` varchar(255) DEFAULT NULL,
  `telefone_celular` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `cgc_cpf` varchar(255) DEFAULT NULL,
  `nome_social` varchar(255) DEFAULT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `valor_cobranca` float DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(45) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_responsavelcobranca`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `tb_responsavelcobranca_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `tb_cliente` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_responsavelcobranca`
--

LOCK TABLES `tb_responsavelcobranca` WRITE;
/*!40000 ALTER TABLE `tb_responsavelcobranca` DISABLE KEYS */;
INSERT INTO `tb_responsavelcobranca` VALUES (1,2,'','','','','','','',10,NULL,NULL,NULL,NULL,NULL),(2,3,'','','','','','','',10,NULL,NULL,NULL,NULL,NULL),(3,4,'','','','',NULL,'','',10,'2024-08-24 16:32:06',NULL,NULL,'S',2),(4,5,'','','','',NULL,'','',10,'2024-10-01 18:55:24',NULL,NULL,'S',7),(5,6,'','','','',NULL,'','',10,'2024-10-01 20:31:59',NULL,NULL,'S',7),(6,7,'','','','',NULL,'','',10,'2024-10-03 15:33:28',NULL,NULL,'S',7),(7,8,'','','','',NULL,'','',10,'2024-10-05 13:37:17','2024-10-05 13:37:17',NULL,'S',7),(8,9,'','','','',NULL,'','',10,'2024-10-07 17:24:37',NULL,NULL,'S',7);
/*!40000 ALTER TABLE `tb_responsavelcobranca` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-09  0:10:14
