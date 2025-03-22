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
-- Table structure for table `tb_responsavelaprovacao`
--

DROP TABLE IF EXISTS `tb_responsavelaprovacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_responsavelaprovacao` (
  `id_responsavelaprovacao` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int DEFAULT NULL,
  `nome_aprovacao` varchar(255) DEFAULT NULL,
  `telefone_fixo` varchar(255) DEFAULT NULL,
  `telefone_celular` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `cgc_cpf` varchar(255) DEFAULT NULL,
  `nome_social` varchar(255) DEFAULT NULL,
  `observacao` varchar(4235) DEFAULT NULL,
  `valor_aprovado` int DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(45) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_responsavelaprovacao`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `tb_responsavelaprovacao_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `tb_cliente` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_responsavelaprovacao`
--

LOCK TABLES `tb_responsavelaprovacao` WRITE;
/*!40000 ALTER TABLE `tb_responsavelaprovacao` DISABLE KEYS */;
INSERT INTO `tb_responsavelaprovacao` VALUES (1,2,'Maria','4444456546','15643213213','teste@orkut.com.br','665645654','54556','Teste',NULL,NULL,NULL,NULL,NULL,NULL),(2,3,'','','','','','','',NULL,NULL,NULL,NULL,NULL,NULL),(3,4,'','','','',NULL,'','',NULL,'2024-08-24 16:32:06',NULL,NULL,'S',2),(4,5,'Carlos','6456465','454654','teste@666.com.br',NULL,'Carla','564',NULL,'2024-10-01 18:55:24',NULL,NULL,'S',7),(5,6,'Danilo Luiz da Silva','1144545454','16489748919','daniloluiz750@gmail.com',NULL,'Danilo Luiz da Silva','',NULL,'2024-10-01 20:31:59',NULL,NULL,'S',7),(6,7,'','','','',NULL,'','',NULL,'2024-10-03 15:33:28',NULL,NULL,'S',7),(7,8,'Antonio','1144454004','11698961645','teste@orkut.com.br',NULL,'Antoni','Teste',NULL,'2024-10-05 13:37:17','2024-10-05 13:37:17',NULL,'S',7),(8,9,'Carlos','1144424000','16656548131','carlos@teste.com',NULL,'Carlos','responsavel por aprovações de serviço',NULL,'2025-02-06 09:29:44','2025-02-06 09:29:44',NULL,'S',7),(9,10,'','','','',NULL,'','',NULL,'2024-10-14 10:54:23','2024-10-14 10:54:23',NULL,'S',7),(10,11,'Teste','1144424000','','',NULL,'Teste','',NULL,'2024-12-09 02:11:41','2024-12-09 02:11:41',NULL,'S',7),(11,201,'','','','',NULL,'','',NULL,'2024-12-20 16:38:01','2024-12-20 16:38:01',NULL,'S',7),(12,202,'','','','',NULL,'','',NULL,'2024-11-07 15:02:50','2024-11-07 15:02:50',NULL,'S',7),(13,203,'','','','',NULL,'','',NULL,'2024-11-10 12:52:09','2024-11-10 12:52:09',NULL,'S',7),(14,204,'','','','',NULL,'','',NULL,'2024-11-10 12:46:18','2024-11-10 12:46:18',NULL,'S',7),(15,205,'','','','',NULL,'','',NULL,'2024-11-11 21:41:58','2024-11-11 21:41:58',NULL,'S',7),(16,206,'','','','',NULL,'','',NULL,'2024-11-21 00:55:30','2024-11-21 00:55:30',NULL,'S',7),(17,207,'','','','',NULL,'','',NULL,'2025-01-07 14:21:29','2025-01-07 14:21:29',NULL,'S',7),(18,208,'','','','',NULL,'','',NULL,'2025-01-30 10:46:34','2025-01-30 10:46:34',NULL,'S',7),(19,209,'','','','',NULL,'','',NULL,'2025-01-30 10:47:37','2025-01-30 10:47:37',NULL,'S',7),(20,210,'','','','',NULL,'','',NULL,'2025-02-06 12:42:45','2025-02-06 12:42:46',NULL,'S',7),(21,211,'','','','',NULL,'','',NULL,'2025-02-06 13:36:05','2025-02-06 13:36:05',NULL,'S',7),(22,212,'','','','',NULL,'','',NULL,'2025-02-19 21:19:32',NULL,NULL,'S',7),(23,213,'','','','',NULL,'','',NULL,'2025-02-19 21:23:56','2025-02-19 21:23:56',NULL,'S',7);
/*!40000 ALTER TABLE `tb_responsavelaprovacao` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-22 18:18:11
