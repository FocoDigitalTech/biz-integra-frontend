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
-- Table structure for table `tb_orcamentoposvenda`
--

DROP TABLE IF EXISTS `tb_orcamentoposvenda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_orcamentoposvenda` (
  `id_orcamentoposvenda` int NOT NULL AUTO_INCREMENT,
  `id_orcamento` varchar(45) DEFAULT NULL,
  `id_cliente` varchar(45) DEFAULT NULL,
  `bomatendimento_orcamentoposvenda` varchar(45) DEFAULT NULL,
  `funcionariosuniformizados_orcamentoposvenda` varchar(45) DEFAULT NULL,
  `limpeza_orcamentoposvenda` varchar(45) DEFAULT NULL,
  `duvidas_orcamentoposvenda` varchar(45) DEFAULT NULL,
  `sugestao_orcamentoposvenda` varchar(45) DEFAULT NULL,
  `notegeral_orcamentoposvenda` int DEFAULT NULL,
  `utilizarianovamente_orcamentoposvenda` varchar(45) DEFAULT NULL,
  `data_orcamentoposvenda` datetime DEFAULT NULL,
  `data_inclusao` datetime NOT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  `ativo` varchar(1) NOT NULL,
  `descricaosugestao_orcamentoposvenda` varchar(4235) DEFAULT NULL,
  PRIMARY KEY (`id_orcamentoposvenda`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_orcamentoposvenda`
--

LOCK TABLES `tb_orcamentoposvenda` WRITE;
/*!40000 ALTER TABLE `tb_orcamentoposvenda` DISABLE KEYS */;
INSERT INTO `tb_orcamentoposvenda` VALUES (1,'986','9','SIM','SIM','SIM','SIM','SIM',2,'NÃO','2024-11-12 00:00:00','2024-11-12 20:04:57','2024-11-12 20:05:41',NULL,7,'S','Melhore');
/*!40000 ALTER TABLE `tb_orcamentoposvenda` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-22 18:18:12
