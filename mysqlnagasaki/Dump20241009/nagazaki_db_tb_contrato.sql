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
-- Table structure for table `tb_contrato`
--

DROP TABLE IF EXISTS `tb_contrato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_contrato` (
  `id_contrato` int NOT NULL AUTO_INCREMENT,
  `id_cliente` varchar(45) NOT NULL,
  `id_orcamento` varchar(45) NOT NULL,
  `aplicacoes_periodicas` varchar(45) DEFAULT NULL,
  `valor_total` decimal(10,0) DEFAULT NULL,
  `valor_nagasaki` decimal(10,0) DEFAULT NULL,
  `data_venda` date DEFAULT NULL,
  `tipo_cobranca` varchar(45) DEFAULT NULL,
  `id_condicaopagamento` int DEFAULT NULL,
  `datainicio_execucao` date DEFAULT NULL,
  `datainicio_vencimento` date DEFAULT NULL,
  `meses_garantia` varchar(45) DEFAULT NULL,
  `datafim_garantia` date DEFAULT NULL,
  `quantidade_aplicacoes` varchar(5) DEFAULT NULL,
  `observacoes_contrato` varchar(255) DEFAULT NULL,
  `data_inclusao` datetime NOT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) NOT NULL,
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id_contrato`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_contrato`
--

LOCK TABLES `tb_contrato` WRITE;
/*!40000 ALTER TABLE `tb_contrato` DISABLE KEYS */;
INSERT INTO `tb_contrato` VALUES (1,'9','22','SIM',250,250,'2024-10-08','Por Serviço',3,'2024-10-08','2024-10-08','3','2025-01-08','1','Teste','2024-10-08 22:28:24',NULL,NULL,'S',7),(2,'9','23','SIM',250,250,'2024-10-08','Por Serviço',3,'2024-10-08','2024-10-08','1','2024-11-08','1','Teste','2024-10-08 22:38:09',NULL,NULL,'S',7),(3,'9','26','SIM',250,250,'2024-10-08','Por Serviço',3,'2024-10-08','2024-10-08','1','2024-11-08','1','Teste','2024-10-08 22:41:01',NULL,NULL,'S',7),(4,'9','27','SIM',250,250,'2024-10-08','Por Serviço',3,'2024-10-08','2024-10-08','3','2025-01-08','1','teste','2024-10-08 22:45:57',NULL,NULL,'S',7),(5,'9','28','SIM',250,250,'2024-10-08','Por Serviço',3,'2024-10-08','2024-10-08','1','2024-11-08','1','Teste','2024-10-08 23:44:26',NULL,NULL,'S',7);
/*!40000 ALTER TABLE `tb_contrato` ENABLE KEYS */;
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
