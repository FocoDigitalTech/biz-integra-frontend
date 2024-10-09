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
-- Table structure for table `tb_funcionario`
--

DROP TABLE IF EXISTS `tb_funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_funcionario` (
  `id_funcionario` int NOT NULL AUTO_INCREMENT,
  `id_departamento` int DEFAULT NULL,
  `nome_funcionario` varchar(255) DEFAULT NULL,
  `nome_carteira` varchar(255) DEFAULT NULL,
  `endereco_funcionario` varchar(255) DEFAULT NULL,
  `numeroimovel_funcionario` varchar(45) DEFAULT NULL,
  `complemento_funcionario` varchar(255) DEFAULT NULL,
  `bairro_funcionario` varchar(255) DEFAULT NULL,
  `cep_funcionario` varchar(255) DEFAULT NULL,
  `cidade_funcionario` varchar(255) DEFAULT NULL,
  `id_estado` int DEFAULT NULL,
  `celular_funcionario` varchar(255) DEFAULT NULL,
  `rg_funcionario` varchar(255) DEFAULT NULL,
  `cpf_funcionario` varchar(255) DEFAULT NULL,
  `titulo_eleitor` varchar(255) DEFAULT NULL,
  `reservista_militar` varchar(255) DEFAULT NULL,
  `numero_ctps` varchar(255) DEFAULT NULL,
  `serie_ctps` varchar(255) DEFAULT NULL,
  `pis_funcionario` varchar(255) DEFAULT NULL,
  `cnh_funcionario` varchar(255) DEFAULT NULL,
  `vencimento_cnh` varchar(255) DEFAULT NULL,
  `data_admissao` datetime DEFAULT NULL,
  `data_desligamento` datetime DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_funcionario`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_funcionario`
--

LOCK TABLES `tb_funcionario` WRITE;
/*!40000 ALTER TABLE `tb_funcionario` DISABLE KEYS */;
INSERT INTO `tb_funcionario` VALUES (1,NULL,'','','',NULL,'','','','',NULL,'','','','','','','','','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,NULL,'João Silva','65456456','Rua José La Torre',NULL,'','Serpa','07714540','Caieiras',1,'11930755555','4707777789','35525332333','12345646','123165465','123134566','132356','1234654987','1315468971','2026-02-08','2024-08-20 00:00:00',NULL,NULL,NULL,NULL,'S',7),(3,20,'Nadia Cunha','65456456','Rua José La Torre',NULL,'','Serpa','07714540','Caieiras',1,'11930737271','3000000006666','35525332333','12345646','1465656456','1111','1111','1234654987','1315468971','2029-09-01','2024-08-20 00:00:00',NULL,NULL,NULL,NULL,'S',7),(4,22,'Maria','22123','Rua Manguari','255','','Jardim Andaraí','02167080','São Paulo',1,'12345646655','1465656456','33333333333','132356','1234654987','1315468971','1111','123564564654','2312564','2024-08-23','2024-08-21 00:00:00',NULL,NULL,NULL,NULL,'S',7),(5,20,'Larissa','','Rua José La Torre','123546','','Serpa','07714540','Caieiras',1,'11111111111','456456465','34880533333','','','','','','',NULL,NULL,NULL,NULL,NULL,NULL,'S',7),(6,23,'Ademir','','Rua José La Torre','119','','Serpa','07714540','Caieiras',1,'11111111111','111111111111111','11111111111','1','1','1','1','1','1','2024-10-12','2024-09-06 00:00:00',NULL,'2024-09-30 19:19:20',NULL,'2024-10-08 16:57:55','N',7),(7,23,'José','','Rua José La Torre','119','','Serpa','07714540','Caieiras',1,'11111111111','111111111111111','11111111111','1','1','1','1','1','1','2024-10-12','2024-09-06 00:00:00',NULL,'2024-09-30 19:19:38',NULL,'2024-10-08 16:57:55','N',7);
/*!40000 ALTER TABLE `tb_funcionario` ENABLE KEYS */;
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
