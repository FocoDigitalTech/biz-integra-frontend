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
-- Table structure for table `tb_fluxorecebimentopagamento`
--

DROP TABLE IF EXISTS `tb_fluxorecebimentopagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_fluxorecebimentopagamento` (
  `id_fluxorecebimentopagamento` int NOT NULL AUTO_INCREMENT,
  `id_eventofinanceiro` int NOT NULL,
  `id_contacorrente` int NOT NULL,
  `id_fornecedor` int DEFAULT NULL,
  `id_tipopagamento` int DEFAULT NULL,
  `nome_fluxorecebimentopagamento` varchar(65) DEFAULT NULL,
  `quantidade_parcelas` varchar(45) DEFAULT NULL,
  `quantidade_intervalo` varchar(45) DEFAULT NULL,
  `data_lancamento` datetime DEFAULT NULL,
  `valor_lancamento` double DEFAULT NULL,
  `data_contabil` datetime DEFAULT NULL,
  `valor_contabil` double DEFAULT NULL,
  `data_pagamento` datetime DEFAULT NULL,
  `valor_pagamento` double DEFAULT NULL,
  `numero_documento` varchar(45) DEFAULT NULL,
  `numero_parcela` varchar(45) DEFAULT NULL,
  `valor_previsto` double DEFAULT NULL,
  `datahora_lancamento` datetime DEFAULT NULL,
  `datahora_baixa` datetime DEFAULT NULL,
  `valor_baixa` double DEFAULT NULL,
  `id_funcionariolancamento` int NOT NULL,
  `id_funcionariobaixa` int DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(45) DEFAULT NULL,
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id_fluxorecebimentopagamento`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_fluxorecebimentopagamento`
--

LOCK TABLES `tb_fluxorecebimentopagamento` WRITE;
/*!40000 ALTER TABLE `tb_fluxorecebimentopagamento` DISABLE KEYS */;
INSERT INTO `tb_fluxorecebimentopagamento` VALUES (1,1,1,NULL,NULL,'1121','10','30','2024-09-23 00:00:00',249.53,'2024-10-08 00:00:00',249.53,'2024-09-24 00:00:00',249.53,'252','2',24.49,'2024-09-23 23:17:19',NULL,NULL,4,NULL,'2024-09-23 23:17:19',NULL,NULL,'S',7);
/*!40000 ALTER TABLE `tb_fluxorecebimentopagamento` ENABLE KEYS */;
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
