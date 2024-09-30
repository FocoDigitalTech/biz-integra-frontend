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
-- Table structure for table `tb_cliente`
--

DROP TABLE IF EXISTS `tb_cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_cliente` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `nome_cliente` varchar(255) DEFAULT NULL,
  `nome_fantasia_cliente` varchar(255) DEFAULT NULL,
  `telefone_cliente` varchar(255) DEFAULT NULL,
  `email_cliente` varchar(255) DEFAULT NULL,
  `nome_contato_cliente` varchar(255) DEFAULT NULL,
  `cargo_contato_cliente` varchar(255) DEFAULT NULL,
  `id_anuncio` int DEFAULT NULL,
  `id_indicacao` int DEFAULT NULL,
  `tipo_naturezajuridica` varchar(25) DEFAULT NULL,
  `cpf_cgc_cliente` varchar(255) DEFAULT NULL,
  `iest_cliente` varchar(255) DEFAULT NULL,
  `numero_naturezajuridica` varchar(265) DEFAULT NULL,
  `observacoes_cliente` varchar(255) DEFAULT NULL,
  `marca_cliente` varchar(255) DEFAULT NULL,
  `administradora_cliente` varchar(255) DEFAULT NULL,
  `celular_cliente` varchar(255) DEFAULT NULL,
  `hora_ligacao_cliente` time DEFAULT NULL,
  `id_tipo_imovel` int DEFAULT NULL,
  `endereco_cliente` varchar(255) DEFAULT NULL,
  `numero_res_cliente` decimal(10,0) DEFAULT NULL,
  `complemento_cliente` varchar(255) DEFAULT NULL,
  `bairro_cliente` varchar(255) DEFAULT NULL,
  `cep_cliente` varchar(255) DEFAULT NULL,
  `cidade_cliente` varchar(255) DEFAULT NULL,
  `id_estado` int DEFAULT NULL,
  `responsavel_cliente` varchar(255) DEFAULT NULL,
  `ponto_referencia_cliente` varchar(255) DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_cliente`
--

LOCK TABLES `tb_cliente` WRITE;
/*!40000 ALTER TABLE `tb_cliente` DISABLE KEYS */;
INSERT INTO `tb_cliente` VALUES (1,'ESCOLA OBJETIVO','ESCOLA OBJETIVO','1144424000','roberto@666.com.br','44454220','',1,1,'Juridica','666.666.666.06','AG',NULL,'Cliente com Muito Rato','Marca Ltda','Concecionaria','(11) 93073-7274','12:30:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Carlos','Teste','2024-08-14 21:29:43',NULL,NULL,'S',1),(2,'ESCOLA ZIZ','ESCOLA ZIZ','1144424000','teste@666.com.br','44454220','',1,1,'Fisica','666.666.666.06','AG',NULL,'Cliente com Muito Rato','Marca Ltda','Concecionaria','(11) 93073-7274','02:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Carlos','Teste','2024-08-14 22:37:49',NULL,NULL,'S',1),(3,'','','','','','',1,1,'','','AG',NULL,'','Marca Ltda','Concecionaria','',NULL,1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Carlos','Teste','2024-08-19 21:58:33',NULL,NULL,'S',1),(4,'ESCOLA TEC1','ESCOLA TEC1','1144424000','teste@666.com.br','a','',1,1,'Pessoa Fisica','348.805.388-17','AG',NULL,'dsaasfffffffffffffffffffffffffffffffffffffffff','Marca Ltda','Concecionaria','(11) 93333-3333','00:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Carlos','Teste','2024-08-24 16:32:06',NULL,NULL,'S',2);
/*!40000 ALTER TABLE `tb_cliente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-30 15:48:05
