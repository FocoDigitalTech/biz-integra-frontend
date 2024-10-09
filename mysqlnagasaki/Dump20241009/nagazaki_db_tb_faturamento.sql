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
-- Table structure for table `tb_faturamento`
--

DROP TABLE IF EXISTS `tb_faturamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_faturamento` (
  `id_faturamento` int NOT NULL AUTO_INCREMENT,
  `id_orcamento` int NOT NULL,
  `id_contrato` int NOT NULL,
  `id_cliente` int NOT NULL,
  `nome_faturamento` varchar(45) DEFAULT NULL,
  `endereco_faturamento` varchar(45) DEFAULT NULL,
  `bairro_faturamento` varchar(45) DEFAULT NULL,
  `cep_faturamento` varchar(45) DEFAULT NULL,
  `cidade_faturamento` varchar(45) DEFAULT NULL,
  `estado_faturamento` varchar(45) DEFAULT NULL,
  `pfpj_faturamento` varchar(45) DEFAULT NULL,
  `cpfcnpf_faturamento` varchar(45) DEFAULT NULL,
  `incricaoestadual_faturamento` varchar(45) DEFAULT NULL,
  `observacao_faturamento` varchar(45) DEFAULT NULL,
  `data_inclusao` datetime NOT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) NOT NULL,
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id_faturamento`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_faturamento`
--

LOCK TABLES `tb_faturamento` WRITE;
/*!40000 ALTER TABLE `tb_faturamento` DISABLE KEYS */;
INSERT INTO `tb_faturamento` VALUES (1,22,1,9,'ESCOLA ZIZ','Rua José La Torre 119','Teste','07714-540','Caieiras','Sp','Pessoa Fisica','544.646.545-64','36161516','Teste','2024-10-08 22:28:24',NULL,NULL,'S',7),(2,23,2,9,'ESCOLA ZIZ','Rua José La Torre 119','Teste','07714540','Caieiras','S','Pessoa Fisica','324.324.324-32','243324324','Teste','2024-10-08 22:38:09',NULL,NULL,'S',7),(3,26,3,9,'ESCOLA ZIZ','Rua José La Torre 119','Teste','07714540','Caieiras','S','Pessoa Fisica','324.324.324-32','243324324','Teste','2024-10-08 22:41:01',NULL,NULL,'S',7),(4,27,4,9,'ESCOLA ZIZ','Rua José La Torre 111','teste','07714-540','Caieiras','teste','Pessoa Fisica','326.591.212-11','32316464545','teste','2024-10-08 22:45:57',NULL,NULL,'S',7),(5,28,5,9,'ESCOLA ZIZ','Rua José La Torre 119','teste','07714540','Caieiras','teste','Pessoa Fisica','655.464.456-45','321123','Teste','2024-10-08 23:44:26',NULL,NULL,'S',7);
/*!40000 ALTER TABLE `tb_faturamento` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-09  0:10:12
