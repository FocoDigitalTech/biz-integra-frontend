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
-- Table structure for table `tb_produto`
--

DROP TABLE IF EXISTS `tb_produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_produto` (
  `id_produto` int NOT NULL AUTO_INCREMENT,
  `id_classificacaoproduto` int DEFAULT NULL,
  `nome_produto` varchar(255) DEFAULT NULL,
  `unidade_entrada` varchar(50) DEFAULT NULL,
  `unidade_aplicacao` varchar(50) DEFAULT NULL,
  `fator_conversao` varchar(50) DEFAULT NULL,
  `quantidade_estoque` varchar(50) DEFAULT NULL,
  `quantidade_minima` varchar(50) DEFAULT NULL,
  `valor_item` decimal(10,0) DEFAULT NULL,
  `utimo_lote` varchar(255) DEFAULT NULL,
  `grupo_quimico` varchar(255) DEFAULT NULL,
  `principio_ativo` varchar(255) DEFAULT NULL,
  `classificacao_nome` varchar(255) DEFAULT NULL,
  `antidoto_nome` varchar(255) DEFAULT NULL,
  `concentrado_nome` varchar(255) DEFAULT NULL,
  `numero_registro` varchar(255) DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_produto`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_produto`
--

LOCK TABLES `tb_produto` WRITE;
/*!40000 ALTER TABLE `tb_produto` DISABLE KEYS */;
INSERT INTO `tb_produto` VALUES (1,NULL,'Veneno de Rato ','gm','kg','gm','50','2',0,'134565','Veneno','Chumbo','Pestes','Seringa','Sim','6897894654','2024-08-05 11:50:18','2024-08-29 11:49:21',NULL,'S',2),(2,NULL,'Gel Formiga','sad','sadasl','sadas','30','11',0,'2','f','f','f','f','f','222','2024-08-29 11:50:18','2024-08-29 11:50:56',NULL,'S',2);
/*!40000 ALTER TABLE `tb_produto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-27  1:03:04
