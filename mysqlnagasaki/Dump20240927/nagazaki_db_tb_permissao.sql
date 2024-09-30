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
-- Table structure for table `tb_permissao`
--

DROP TABLE IF EXISTS `tb_permissao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_permissao` (
  `id_permissao` int NOT NULL AUTO_INCREMENT,
  `id_grupousuario` int DEFAULT NULL,
  `id_tela` int DEFAULT NULL,
  `descricao_permissao` varchar(255) DEFAULT NULL,
  `tela_view` varchar(45) DEFAULT NULL,
  `nome_tela` varchar(45) DEFAULT NULL,
  `escrita` tinyint(1) DEFAULT NULL,
  `leitura` tinyint(1) DEFAULT NULL,
  `exclusao` tinyint(1) DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_permissao`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_permissao`
--

LOCK TABLES `tb_permissao` WRITE;
/*!40000 ALTER TABLE `tb_permissao` DISABLE KEYS */;
INSERT INTO `tb_permissao` VALUES (1,1,NULL,'ADMINISTRADOR','all','all',1,1,1,NULL,NULL,NULL,'S',1),(2,1,NULL,'Acesso a tela Financeiro','FinanceiroView.class','Financeiro',0,1,0,NULL,NULL,NULL,'S',1),(3,1,NULL,'Relatorios','RelatoriosView.class','Relatorios',0,0,0,NULL,NULL,NULL,'S',1),(4,6,NULL,NULL,'Sistema','Sistema',0,0,NULL,'2024-09-16 02:15:21',NULL,NULL,'S',7),(5,6,NULL,NULL,'Relatórios','Relatórios',0,0,NULL,'2024-09-16 02:15:21',NULL,NULL,'S',7),(6,6,NULL,NULL,'Estoque','Estoque',0,0,NULL,'2024-09-16 02:15:21',NULL,NULL,'S',7),(7,6,NULL,NULL,'Financeiro','Financeiro',0,0,NULL,'2024-09-16 02:15:21',NULL,NULL,'S',7),(8,6,NULL,NULL,'Administrativo','Administrativo',0,0,NULL,'2024-09-16 02:15:21',NULL,NULL,'S',7),(9,6,NULL,NULL,'Clientes','Clientes',1,1,NULL,'2024-09-16 02:15:21',NULL,NULL,'S',7),(10,6,NULL,NULL,'Configurações de Segurança','Configurações de Segurança',0,0,NULL,'2024-09-16 02:15:21',NULL,NULL,'S',7);
/*!40000 ALTER TABLE `tb_permissao` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-27  1:03:01
