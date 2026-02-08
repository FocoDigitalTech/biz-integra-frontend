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
-- Table structure for table `tb_orcamentocontato`
--

DROP TABLE IF EXISTS `tb_orcamentocontato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_orcamentocontato` (
  `id_orcamentocontato` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int NOT NULL,
  `id_orcamento` int NOT NULL,
  `data_orcamentocontato` date DEFAULT NULL,
  `horario_orcamentocontato` time DEFAULT NULL,
  `nome_orcamentocontato` varchar(45) DEFAULT NULL,
  `telefone_orcamentocontato` varchar(45) DEFAULT NULL,
  `id_funcionario` int DEFAULT NULL,
  `dataretorno_orcamentocontato` date DEFAULT NULL,
  `unidade_orcamentocontato` varchar(45) DEFAULT NULL,
  `descricao_orcamentocontato` varchar(4235) DEFAULT NULL,
  `data_inclusao` datetime NOT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `id_usuario` int NOT NULL,
  `ativo` varchar(45) NOT NULL,
  PRIMARY KEY (`id_orcamentocontato`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_orcamentocontato`
--

LOCK TABLES `tb_orcamentocontato` WRITE;
/*!40000 ALTER TABLE `tb_orcamentocontato` DISABLE KEYS */;
INSERT INTO `tb_orcamentocontato` VALUES (1,9,986,'2024-11-12','10:00:00','teste','54654564564',4,'2024-11-12','teste','teste','2024-11-12 09:26:12','2024-11-12 09:27:29',NULL,7,'S'),(2,9,1014,'2025-01-26','04:00:00','Secretaria','1196565656',2,'2025-01-26','Teste','Teste3','2025-01-26 10:42:31','2025-01-26 11:51:28',NULL,7,'S'),(3,9,1013,'2025-01-26','08:00:00','ESCOLA Senenbi','1199415546',2,'2025-01-27','1','Teste4','2025-01-26 11:52:04','2025-01-26 11:52:45',NULL,7,'S'),(4,9,1018,'2025-02-06','01:00:00','Rua José La Torre 119',NULL,2,'2025-02-06',NULL,'Neste momento, o Navegador Yandex está baixando um arquivo no formato Office para ser aberto em um dos programas do seu sistema. Mas você também pode pular totalmente o processo de download! Documentos de texto, planilhas e apresentações agora podem ser visualizados e editados aqui mesmo no navegador. Os arquivos serão salvos no Yandex Disk e abertos no editor de documentos.','2025-02-06 09:30:56',NULL,NULL,7,'S');
/*!40000 ALTER TABLE `tb_orcamentocontato` ENABLE KEYS */;
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
