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
-- Table structure for table `tb_fornecedores`
--

DROP TABLE IF EXISTS `tb_fornecedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_fornecedores` (
  `id_fornecedor` int NOT NULL AUTO_INCREMENT,
  `data_cadastro` datetime DEFAULT NULL,
  `id_setoratuacao` int DEFAULT NULL,
  `tipo_naturezajuridica` varchar(255) DEFAULT NULL,
  `numero_naturezajuridica` varchar(255) DEFAULT NULL,
  `razaosocial_fornecedor` varchar(255) DEFAULT NULL,
  `nomefantasia_fornecedor` varchar(255) DEFAULT NULL,
  `cep_fornecedor` varchar(255) DEFAULT NULL,
  `endereco_fornecedor` varchar(255) DEFAULT NULL,
  `numero_endereco` varchar(255) DEFAULT NULL,
  `bairro_fornecedor` varchar(255) DEFAULT NULL,
  `cidade_fornecedor` varchar(255) DEFAULT NULL,
  `id_estado` int DEFAULT NULL,
  `telefone_fornecedor` varchar(255) DEFAULT NULL,
  `email_fornecedor` varchar(255) DEFAULT NULL,
  `nomecontato_fornecedor` varchar(255) DEFAULT NULL,
  `cargocontato_fornecedor` varchar(255) DEFAULT NULL,
  `inscicaoestadual_fornecedor` varchar(255) DEFAULT NULL,
  `observacao_fornecedor` varchar(255) DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(255) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_fornecedor`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_fornecedores`
--

LOCK TABLES `tb_fornecedores` WRITE;
/*!40000 ALTER TABLE `tb_fornecedores` DISABLE KEYS */;
INSERT INTO `tb_fornecedores` VALUES (1,'2024-10-09 00:00:00',1,'Pessoa Fisica','564.564.654-65','Jose Bosco','ESCOLA OBJETIVO','07714-540','Rua José La Torre','','Serpa','Caieiras',1,'(11) 4442-4000','roberto@666.com.br','Jardim Andaraí','','','','2024-10-08 15:59:55',NULL,'2024-10-08 16:59:28','S',7),(2,'2024-10-10 00:00:00',1,'Pessoa Juridica','321.254.566-500001','Insetizaeu','Insetex','02167-080','Rua Manguari','6564','Jardim Andaraí','São Paulo',1,'(11) 9941-5546','roberto@666.com.br','Pecanha','Diretor','123231321','Caieiras','2024-10-08 16:19:13',NULL,'2024-10-08 16:52:15','S',7);
/*!40000 ALTER TABLE `tb_fornecedores` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-09  0:10:14
