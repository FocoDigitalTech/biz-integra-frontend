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
-- Table structure for table `tb_dadosempresa`
--

DROP TABLE IF EXISTS `tb_dadosempresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_dadosempresa` (
  `id_dadosempresa` int NOT NULL AUTO_INCREMENT,
  `nome_dadosempresa` varchar(45) DEFAULT NULL,
  `endereco_dadosempresa` varchar(45) DEFAULT NULL,
  `bairro_dadosempresa` varchar(45) DEFAULT NULL,
  `cep_dadosempresa` varchar(45) DEFAULT NULL,
  `cidade_dadosempresa` varchar(45) DEFAULT NULL,
  `estado_dadosempresa` varchar(45) DEFAULT NULL,
  `telefone_dadosempresa` varchar(45) DEFAULT NULL,
  `celular_dadosempresa` varchar(45) DEFAULT NULL,
  `email_dadosempresa` varchar(45) DEFAULT NULL,
  `cnpj_dadosempresa` varchar(45) DEFAULT NULL,
  `agencia_dadosempresa` varchar(45) DEFAULT NULL,
  `conta_dadosempresa` varchar(45) DEFAULT NULL,
  `dataestoque_dadosempresa` datetime DEFAULT NULL,
  `nomequimico_dadosempresa` varchar(45) DEFAULT NULL,
  `numeroalvaraquimico_dadosempresa` varchar(45) DEFAULT NULL,
  `telefonequimico_dadosempresa` varchar(45) DEFAULT NULL,
  `celularquimico_dadosempresa` varchar(45) DEFAULT NULL,
  `emailquimico_dadosempresa` varchar(45) DEFAULT NULL,
  `data_inclusao` datetime NOT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `id_usuario` int NOT NULL,
  `ativo` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id_dadosempresa`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_dadosempresa`
--

LOCK TABLES `tb_dadosempresa` WRITE;
/*!40000 ALTER TABLE `tb_dadosempresa` DISABLE KEYS */;
INSERT INTO `tb_dadosempresa` VALUES (1,'INSETEX COMÉRCIO E SERVIÇOS EIRELI','RUA JOÃO BERTACCHI, 49','INTERLAGOS','04777-110','SÃO PAULO','ap','(11) 5666-1444','','info@nagasaki.com.br','04.004.186/0001-08',NULL,NULL,'2024-11-12 00:00:00','DANIEL BRUNO BELUTI','3550 30 890-812-000008-1-7','(50) 6208-9159','','','2024-10-12 00:00:00','2024-11-12 23:03:16',NULL,1,'S');
/*!40000 ALTER TABLE `tb_dadosempresa` ENABLE KEYS */;
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
