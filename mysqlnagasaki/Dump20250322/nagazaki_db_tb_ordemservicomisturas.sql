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
-- Table structure for table `tb_ordemservicomisturas`
--

DROP TABLE IF EXISTS `tb_ordemservicomisturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_ordemservicomisturas` (
  `id_ordemservicomisturas` int NOT NULL AUTO_INCREMENT,
  `id_ordemservico` int NOT NULL,
  `id_orcamento` int NOT NULL,
  `id_contrato` int DEFAULT NULL,
  `id_cliente` int NOT NULL,
  `id_produto` int NOT NULL,
  `id_produtosolvente` int DEFAULT NULL,
  `numerolote_ordemservicomisturas` varchar(45) DEFAULT NULL,
  `quantidadeprevistaproduto_ordemservicomisturas` int DEFAULT NULL,
  `unidademedidaproduto_ordemservicomisturas` varchar(45) DEFAULT NULL,
  `quantidadeconsumidaproduto_ordemservicomisturas` int DEFAULT NULL,
  `quantidadeprevistasolvente_ordemservicomisturas` int DEFAULT NULL,
  `unidademedidasolvente_ordemservicomisturas` varchar(45) DEFAULT NULL,
  `quantidadeconsumidasolvente_ordemservicomisturas` int DEFAULT NULL,
  `descricao_ordemservicomisturas` varchar(4235) DEFAULT NULL,
  `data_inclusao` datetime NOT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  `ativo` varchar(1) NOT NULL,
  PRIMARY KEY (`id_ordemservicomisturas`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_ordemservicomisturas`
--

LOCK TABLES `tb_ordemservicomisturas` WRITE;
/*!40000 ALTER TABLE `tb_ordemservicomisturas` DISABLE KEYS */;
INSERT INTO `tb_ordemservicomisturas` VALUES (1,330,28,NULL,9,1,2,'000000000000654',1,'ml',1,1,'ml',1,'teste','2024-11-10 12:27:57',NULL,NULL,7,'S'),(2,330,28,NULL,9,2,2,'000000000000654',1,'ml',1,1,'ml',1,'teste','2024-11-10 12:28:03',NULL,NULL,7,'S');
/*!40000 ALTER TABLE `tb_ordemservicomisturas` ENABLE KEYS */;
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
