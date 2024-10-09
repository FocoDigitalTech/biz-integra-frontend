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
-- Table structure for table `tb_compraproduto`
--

DROP TABLE IF EXISTS `tb_compraproduto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_compraproduto` (
  `id_compraproduto` int NOT NULL AUTO_INCREMENT,
  `id_compra` varchar(45) NOT NULL,
  `id_produto` varchar(45) NOT NULL,
  `quantidade_compraproduto` int DEFAULT NULL,
  `valorunitario_compraproduto` decimal(10,2) DEFAULT NULL,
  `valortotal_compraproduto` decimal(10,2) DEFAULT NULL,
  `numerolote_compraproduto` varchar(45) DEFAULT NULL,
  `datafabricacao_compraproduto` date DEFAULT NULL,
  `datavalidade_compraproduto` date DEFAULT NULL,
  `datarecebimento_compraproduto` date DEFAULT NULL,
  `responsavelrecebimento_compraproduto` varchar(45) DEFAULT NULL,
  `data_inclusao` datetime NOT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) NOT NULL,
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id_compraproduto`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_compraproduto`
--

LOCK TABLES `tb_compraproduto` WRITE;
/*!40000 ALTER TABLE `tb_compraproduto` DISABLE KEYS */;
INSERT INTO `tb_compraproduto` VALUES (1,'2','1',5,32.66,163.30,'4324','2024-10-08','2024-10-08','2024-10-08','','2024-10-08 21:34:48',NULL,NULL,'S',7),(2,'2','2',5,321.11,1605.55,'32432','2024-10-08','2024-10-08','2024-10-08','','2024-10-08 21:35:13',NULL,NULL,'S',7);
/*!40000 ALTER TABLE `tb_compraproduto` ENABLE KEYS */;
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
