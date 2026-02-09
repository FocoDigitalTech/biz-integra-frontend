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
-- Table structure for table `tb_fluxorecebimentopagamento`
--

DROP TABLE IF EXISTS `tb_fluxorecebimentopagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_fluxorecebimentopagamento` (
  `id_fluxorecebimentopagamento` int NOT NULL AUTO_INCREMENT,
  `id_eventofinanceiro` int NOT NULL,
  `id_contacorrente` int DEFAULT NULL,
  `id_fornecedor` int DEFAULT NULL,
  `id_tipopagamento` int DEFAULT NULL,
  `nome_fluxorecebimentopagamento` varchar(65) DEFAULT NULL,
  `quantidade_parcelas` varchar(45) DEFAULT NULL,
  `quantidade_intervalo` varchar(45) DEFAULT NULL,
  `data_lancamento` datetime DEFAULT NULL,
  `valor_lancamento` decimal(10,2) DEFAULT NULL,
  `data_contabil` datetime DEFAULT NULL,
  `valor_contabil` decimal(10,2) DEFAULT NULL,
  `data_pagamento` datetime DEFAULT NULL,
  `valor_pagamento` decimal(10,2) DEFAULT NULL,
  `numero_documento` varchar(45) DEFAULT NULL,
  `numero_parcela` varchar(45) DEFAULT NULL,
  `valor_previsto` decimal(10,2) DEFAULT NULL,
  `datahora_lancamento` datetime DEFAULT NULL,
  `datahora_baixa` datetime DEFAULT NULL,
  `valor_baixa` decimal(10,2) DEFAULT NULL,
  `id_funcionariolancamento` int NOT NULL,
  `id_funcionariobaixa` int DEFAULT NULL,
  `id_tipoeventofinanceiro` int DEFAULT NULL,
  `status_pagamento` varchar(45) DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(45) DEFAULT NULL,
  `id_usuario` int NOT NULL,
  `descricao_fluxorecebimentopagamento` varchar(1445) DEFAULT NULL,
  `data_vencimento` date DEFAULT NULL,
  PRIMARY KEY (`id_fluxorecebimentopagamento`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_fluxorecebimentopagamento`
--

LOCK TABLES `tb_fluxorecebimentopagamento` WRITE;
/*!40000 ALTER TABLE `tb_fluxorecebimentopagamento` DISABLE KEYS */;
INSERT INTO `tb_fluxorecebimentopagamento` VALUES (32,3,NULL,3,NULL,'Café da manhã semanal','12','30','2025-02-18 00:00:00',9852.10,NULL,NULL,NULL,0.00,'','1',0.00,'2025-02-16 11:39:06',NULL,NULL,4,NULL,2,'Previsão (P)','2025-02-16 11:39:06',NULL,NULL,'S',7,'Café da manhã semanal','2025-02-18'),(33,3,NULL,3,NULL,'Café da manhã semanal','12','30','2025-02-18 00:00:00',9852.10,NULL,NULL,NULL,0.00,'','2',0.00,'2025-02-16 11:39:06',NULL,NULL,4,NULL,2,'Previsão (P)','2025-02-16 11:39:06',NULL,NULL,'S',7,'Café da manhã semanal','2025-03-20'),(34,3,NULL,3,NULL,'Café da manhã semanal','12','30','2025-02-18 00:00:00',9852.10,NULL,NULL,'2025-02-18 00:00:00',985.21,'','3',0.00,'2025-02-16 11:39:06','2025-02-18 09:24:54',985.21,4,4,2,'Consolidado (C)','2025-02-16 11:39:06','2025-02-18 09:24:54','2025-02-18 09:25:48','N',7,'Café da manhã semanal','2025-04-19'),(35,3,NULL,3,NULL,'Café da manhã semanal','12','30','2025-02-18 00:00:00',985.21,NULL,NULL,NULL,0.00,'000000000032665','4',0.00,'2025-02-17 09:13:26',NULL,NULL,4,NULL,2,'Previsão (P)','2025-02-16 11:39:06','2025-02-17 09:13:26',NULL,'S',7,'Café da manhã semanal','2025-05-19'),(36,3,NULL,3,NULL,'Café da manhã semanal','12','30','2025-02-18 00:00:00',9852.10,NULL,NULL,NULL,0.00,'','5',0.00,'2025-02-16 11:39:06',NULL,NULL,4,NULL,2,'Previsão (P)','2025-02-16 11:39:06',NULL,NULL,'S',7,'Café da manhã semanal','2025-06-18'),(37,3,NULL,3,NULL,'Café da manhã semanal','12','30','2025-02-18 00:00:00',9852.10,NULL,NULL,NULL,0.00,'','6',0.00,'2025-02-16 11:39:06',NULL,NULL,4,NULL,2,'Previsão (P)','2025-02-16 11:39:06',NULL,NULL,'S',7,'Café da manhã semanal','2025-07-18'),(38,3,NULL,3,NULL,'Café da manhã semanal','12','30','2025-02-18 00:00:00',9852.10,NULL,NULL,NULL,0.00,'','7',0.00,'2025-02-16 11:39:06',NULL,NULL,4,NULL,2,'Previsão (P)','2025-02-16 11:39:06',NULL,NULL,'S',7,'Café da manhã semanal','2025-08-17'),(39,3,NULL,3,NULL,'Café da manhã semanal','12','30','2025-02-18 00:00:00',9852.10,NULL,NULL,NULL,0.00,'','8',0.00,'2025-02-16 11:39:06',NULL,NULL,4,NULL,2,'Previsão (P)','2025-02-16 11:39:06',NULL,NULL,'S',7,'Café da manhã semanal','2025-09-16'),(40,3,NULL,3,NULL,'Café da manhã semanal','12','30','2025-02-18 00:00:00',9852.10,NULL,NULL,NULL,0.00,'','9',0.00,'2025-02-16 11:39:06',NULL,NULL,4,NULL,2,'Previsão (P)','2025-02-16 11:39:06',NULL,NULL,'S',7,'Café da manhã semanal','2025-10-16'),(41,3,NULL,3,NULL,'Café da manhã semanal','12','30','2025-02-18 00:00:00',9852.10,NULL,NULL,NULL,0.00,'','10',0.00,'2025-02-16 11:39:06',NULL,NULL,4,NULL,2,'Previsão (P)','2025-02-16 11:39:06',NULL,NULL,'S',7,'Café da manhã semanal','2025-11-15'),(42,3,NULL,3,NULL,'Café da manhã semanal','12','30','2025-02-18 00:00:00',9852.10,NULL,NULL,NULL,0.00,'','11',0.00,'2025-02-16 11:39:06',NULL,NULL,4,NULL,2,'Previsão (P)','2025-02-16 11:39:06',NULL,NULL,'S',7,'Café da manhã semanal','2025-12-15'),(43,3,NULL,3,NULL,'Café da manhã semanal','12','30','2025-02-18 00:00:00',9852.10,NULL,NULL,NULL,0.00,'','12',0.00,'2025-02-16 11:39:06',NULL,NULL,4,NULL,2,'Previsão (P)','2025-02-16 11:39:06',NULL,NULL,'S',7,'Café da manhã semanal','2026-01-14'),(44,4,NULL,1,NULL,'Hotelaria','2','10','2025-02-20 00:00:00',9852.10,NULL,NULL,NULL,0.00,'','1',0.00,'2025-02-16 11:45:33',NULL,NULL,4,NULL,1,'Real (R)','2025-02-16 11:45:33',NULL,NULL,'S',7,'teste','2025-02-20'),(45,4,NULL,1,NULL,'Hotelaria','2','10','2025-02-20 00:00:00',9852.10,NULL,NULL,NULL,0.00,'','2',0.00,'2025-02-16 11:45:57',NULL,NULL,4,NULL,1,'Real (R)','2025-02-16 11:45:57',NULL,NULL,'S',7,'teste','2025-03-02'),(46,1,NULL,2,NULL,'Canetas','1','1','2025-02-25 00:00:00',25.50,NULL,NULL,NULL,0.00,'','1',0.00,'2025-02-17 09:20:33',NULL,NULL,4,NULL,1,'Previsão (P)','2025-02-17 09:15:34','2025-02-17 09:20:33',NULL,'S',7,'Teste','2025-02-25'),(47,1,NULL,1,NULL,'1121','1','1','2025-02-20 00:00:00',326.60,NULL,NULL,NULL,0.00,'','1',0.00,'2025-02-19 20:15:04',NULL,NULL,4,NULL,1,'Real (R)','2025-02-19 20:15:04',NULL,NULL,'S',7,'','2025-02-20');
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

-- Dump completed on 2025-03-22 18:18:09
