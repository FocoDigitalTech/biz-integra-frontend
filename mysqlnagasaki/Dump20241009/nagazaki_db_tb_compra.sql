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
-- Table structure for table `tb_compra`
--

DROP TABLE IF EXISTS `tb_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_compra` (
  `id_compra` int NOT NULL AUTO_INCREMENT,
  `id_fornecedor` int NOT NULL,
  `notafiscal_compra` varchar(45) DEFAULT NULL,
  `id_condicaopagamento` int NOT NULL,
  `id_contacorrente` int DEFAULT NULL,
  `numeronotafiscal_compra` varchar(45) DEFAULT NULL,
  `datanotafiscal_compra` date DEFAULT NULL,
  `datapagamento_compra` date DEFAULT NULL,
  `valoritemstotal_compra` decimal(10,2) DEFAULT NULL,
  `valorfrete_compra` decimal(10,2) DEFAULT NULL,
  `valordesconto_compra` decimal(10,2) DEFAULT NULL,
  `valortotal_compra` decimal(10,2) DEFAULT NULL,
  `observacoes_compra` varchar(245) DEFAULT NULL,
  `responsavelaprovacao_compra` varchar(45) DEFAULT NULL,
  `dataaprovacao_compra` date DEFAULT NULL,
  `horario_compra` time DEFAULT NULL,
  `datavalidate_compra` date DEFAULT NULL,
  `data_compra` date DEFAULT NULL,
  `data_inclusao` datetime NOT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) NOT NULL,
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id_compra`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_compra`
--

LOCK TABLES `tb_compra` WRITE;
/*!40000 ALTER TABLE `tb_compra` DISABLE KEYS */;
INSERT INTO `tb_compra` VALUES (1,1,NULL,3,1,'44454220','2024-10-08','2024-10-09',87.75,25.00,35.00,77.75,'TEste','Treze','2024-10-15','02:00:00','2024-10-24','2024-10-09','2024-10-08 21:22:00',NULL,NULL,'S',7),(2,2,NULL,3,2,'255','2024-10-08','2024-10-08',1768.85,100.00,250.00,1618.85,'Teste','Dan','2024-10-09','03:00:00','2024-10-23','2024-10-08','2024-10-08 21:36:10',NULL,NULL,'S',7);
/*!40000 ALTER TABLE `tb_compra` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-09  0:10:13
