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
-- Table structure for table `tb_notafiscal`
--

DROP TABLE IF EXISTS `tb_notafiscal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_notafiscal` (
  `id_notafiscal` int NOT NULL AUTO_INCREMENT,
  `id_orcamento` int NOT NULL,
  `id_contrato` int NOT NULL,
  `id_cliente` int NOT NULL,
  `numero_notafiscal` varchar(45) DEFAULT NULL,
  `serie_notafiscal` varchar(45) DEFAULT NULL,
  `chaveacesso_notafiscal` varchar(45) DEFAULT NULL,
  `dataemissao_notafiscal` date DEFAULT NULL,
  `natureza_notafiscal` varchar(45) DEFAULT NULL,
  `unidade_notafiscal` varchar(45) DEFAULT NULL,
  `quantidade_notafiscal` varchar(45) DEFAULT NULL,
  `valorunitario_notafiscal` decimal(10,2) DEFAULT NULL,
  `valortotal_notafiscal` decimal(10,2) DEFAULT NULL,
  `descricao_notafiscal` varchar(4235) DEFAULT NULL,
  `caminhoarquivo_notafiscal` varchar(245) DEFAULT NULL,
  `nomearquivo_notafiscal` varchar(245) DEFAULT NULL,
  `data_inclusao` datetime NOT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) NOT NULL,
  `id_usuario` int NOT NULL,
  `datavencimento_notafiscal` date DEFAULT NULL,
  PRIMARY KEY (`id_notafiscal`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_notafiscal`
--

LOCK TABLES `tb_notafiscal` WRITE;
/*!40000 ALTER TABLE `tb_notafiscal` DISABLE KEYS */;
INSERT INTO `tb_notafiscal` VALUES (1,22,1,9,'12331','3212',NULL,'2024-10-08','PF','1','1',250.00,250.00,'Teste',NULL,NULL,'2024-10-08 22:28:24',NULL,NULL,'S',7,NULL),(2,26,3,9,'321321','3213',NULL,'2024-10-08','Teste','3','1',324.32,423.44,'Teste',NULL,NULL,'2024-10-08 22:41:01',NULL,NULL,'S',7,NULL),(3,26,3,9,'32123','32',NULL,'2024-10-10','dsf','2','1',256.51,515.11,'',NULL,NULL,'2024-10-08 22:41:01',NULL,NULL,'S',7,NULL),(4,27,4,9,'32654','9999',NULL,'2024-10-08','teste','1','1',250.00,250.00,'teste',NULL,NULL,'2024-10-08 22:45:57',NULL,NULL,'S',7,NULL),(5,28,5,9,'3221232','3232',NULL,'2024-10-08','teste','32212','1',32151.21,65.65,'teste',NULL,NULL,'2024-10-08 23:44:26',NULL,'2024-11-12 09:03:39','N',7,NULL),(6,1000,204,9,'00AS1ASD12222223',NULL,NULL,'2025-02-03',NULL,NULL,NULL,NULL,2665.56,'',NULL,NULL,'2025-02-20 19:47:30',NULL,NULL,'S',7,'2025-02-12'),(7,1000,204,9,'234444444444',NULL,NULL,'2025-02-06',NULL,NULL,NULL,NULL,235.65,'Neste momento, o Navegador Yandex está baixando um arquivo no formato Office para ser aberto em um dos programas do seu sistema. Mas você também pode pular totalmente o processo de download! Documentos de texto, planilhas e apresentações agora podem ser visualizados e editados aqui mesmo no navegador. Os arquivos serão salvos no Yandex Disk e abertos no editor de documentos.',NULL,NULL,'2025-02-20 19:47:30',NULL,NULL,'S',7,'2025-02-06');
/*!40000 ALTER TABLE `tb_notafiscal` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-22 18:18:11
