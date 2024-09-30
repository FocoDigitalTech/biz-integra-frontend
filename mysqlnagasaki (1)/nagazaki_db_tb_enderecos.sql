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
-- Table structure for table `tb_enderecos`
--

DROP TABLE IF EXISTS `tb_enderecos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_enderecos` (
  `id_endereco` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int DEFAULT NULL,
  `id_tipoimovel` int DEFAULT NULL,
  `area_imovel` varchar(255) DEFAULT NULL,
  `numero_imovel` varchar(255) DEFAULT NULL,
  `id_estado` int DEFAULT NULL,
  `endereco_imovel` varchar(255) DEFAULT NULL,
  `complemento_imovel` varchar(255) DEFAULT NULL,
  `bairro_imovel` varchar(255) DEFAULT NULL,
  `cep_imovel` varchar(255) DEFAULT NULL,
  `cidade_imovel` varchar(255) DEFAULT NULL,
  `nome_responsavel` varchar(255) DEFAULT NULL,
  `pagina_guia` varchar(255) DEFAULT NULL,
  `latitude_longitude` varchar(255) DEFAULT NULL,
  `telefone_local` varchar(255) DEFAULT NULL,
  `id_regiao` int DEFAULT NULL,
  `ponto_referencia` varchar(255) DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  `ativo` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id_endereco`),
  KEY `id_cliente` (`id_cliente`),
  KEY `id_estado` (`id_estado`),
  CONSTRAINT `tb_enderecos_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `tb_cliente` (`id_cliente`),
  CONSTRAINT `tb_enderecos_ibfk_2` FOREIGN KEY (`id_estado`) REFERENCES `tb_estado` (`id_estado`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_enderecos`
--

LOCK TABLES `tb_enderecos` WRITE;
/*!40000 ALTER TABLE `tb_enderecos` DISABLE KEYS */;
INSERT INTO `tb_enderecos` VALUES (1,1,NULL,'200','112',NULL,'Rua Avenida 1','Predio','Vila Maria','07714540','São Paulo','Maria','1',NULL,'1144424000',NULL,'Mercado','2024-08-14 21:29:43',NULL,NULL,1,'S'),(2,1,NULL,'1','1',NULL,'Rua 1','Teste','Teste','3','Teste','Irineu','1',NULL,'1144424000',NULL,'1','2024-08-14 21:29:43',NULL,NULL,1,'S'),(3,2,NULL,'1','22',NULL,'adsfsdfas','asfdsa','fdsafasd','1','fasdfsadf','fdsaafasd','1',NULL,'1144424000',NULL,'sadfsadfdfsa','2024-08-14 22:37:49',NULL,NULL,1,'S'),(4,2,NULL,'2222','2',NULL,'fdsfafadsf','asdfdsafsad','sadfdsaf','655465','asdfsadf','asdfsadfsadf','1',NULL,'1',NULL,'asdfasdf','2024-08-14 22:37:49',NULL,NULL,1,'S');
/*!40000 ALTER TABLE `tb_enderecos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-30 15:47:40
