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
-- Table structure for table `tb_comissoes`
--

DROP TABLE IF EXISTS `tb_comissoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_comissoes` (
  `id_comissoes` int NOT NULL AUTO_INCREMENT,
  `id_contrato` int NOT NULL,
  `id_cliente` int NOT NULL,
  `id_funcionario` int NOT NULL,
  `id_orcamento` int NOT NULL,
  `parcelas_comissoes` varchar(45) DEFAULT NULL,
  `porcentagem_comissoes` decimal(10,2) DEFAULT NULL,
  `data_comissao` datetime DEFAULT NULL,
  `valor_comissao` decimal(10,2) DEFAULT NULL,
  `parcela_comisao` int DEFAULT NULL,
  `totalparcelas_comissao` int DEFAULT NULL,
  `datapagamento_comissao` datetime DEFAULT NULL,
  `valortotal_comissao` decimal(10,2) DEFAULT NULL,
  `descricao_comissao` varchar(245) DEFAULT NULL,
  `data_inclusao` datetime NOT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `id_usuario` int NOT NULL,
  `ativo` varchar(45) NOT NULL,
  PRIMARY KEY (`id_comissoes`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_comissoes`
--

LOCK TABLES `tb_comissoes` WRITE;
/*!40000 ALTER TABLE `tb_comissoes` DISABLE KEYS */;
INSERT INTO `tb_comissoes` VALUES (1,1,9,2,22,'A Vista',1.00,'2024-10-08 00:00:00',2.50,1,1,'2024-10-23 00:00:00',NULL,'Teste','2024-10-08 22:26:42',NULL,NULL,7,'S'),(2,3,9,5,26,'A Vista',10.00,'2024-10-08 00:00:00',25.00,1,1,'2024-10-08 00:00:00',NULL,'Teste','2024-10-08 22:37:07',NULL,NULL,7,'S'),(3,3,9,5,26,'A Vista',10.00,'2024-10-08 00:00:00',25.00,1,1,'2024-10-08 00:00:00',NULL,'Teste','2024-10-08 22:40:47',NULL,NULL,7,'S'),(4,4,9,3,27,'A Vista',1.00,'2024-10-08 00:00:00',2.50,1,1,'2024-10-08 00:00:00',NULL,'teste','2024-10-08 22:44:42',NULL,NULL,7,'S'),(5,5,9,2,28,'A Vista',1.00,'2024-10-08 00:00:00',2.50,1,1,'2024-10-08 00:00:00',NULL,'Teste','2024-10-08 23:43:00',NULL,NULL,7,'S');
/*!40000 ALTER TABLE `tb_comissoes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-09  0:10:15
