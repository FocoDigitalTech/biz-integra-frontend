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
-- Table structure for table `tb_ordemservicoexecucaoservico`
--

DROP TABLE IF EXISTS `tb_ordemservicoexecucaoservico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_ordemservicoexecucaoservico` (
  `id_ordemservicoexecucaoservico` int NOT NULL AUTO_INCREMENT,
  `id_ordemservico` int NOT NULL,
  `id_orcamento` int NOT NULL,
  `id_contrato` int DEFAULT NULL,
  `id_cliente` int NOT NULL,
  `id_execucaoservico` int NOT NULL,
  `valor_ordemservicoexecucaoservico` decimal(10,2) DEFAULT NULL,
  `garantia_ordemservicoexecucaoservico` varchar(45) DEFAULT NULL,
  `descricao_ordemservicoexecucaoservico` varchar(4235) DEFAULT NULL,
  `data_inclusao` datetime NOT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  `ativo` varchar(1) NOT NULL,
  PRIMARY KEY (`id_ordemservicoexecucaoservico`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_ordemservicoexecucaoservico`
--

LOCK TABLES `tb_ordemservicoexecucaoservico` WRITE;
/*!40000 ALTER TABLE `tb_ordemservicoexecucaoservico` DISABLE KEYS */;
INSERT INTO `tb_ordemservicoexecucaoservico` VALUES (1,330,28,NULL,9,1,252.35,'teste','Teste','2024-11-10 12:26:56',NULL,NULL,7,'S'),(2,330,28,NULL,9,1,2523.50,'teste','Teste','2024-11-10 12:27:06',NULL,NULL,7,'S'),(3,362,1014,NULL,9,4,12.12,'32','Verificar Vazamentos','2025-01-07 07:27:02',NULL,NULL,7,'S'),(4,362,1014,NULL,9,3,12.12,'32','Reparo em Caixa daqgua','2025-01-07 07:28:29',NULL,NULL,7,'S'),(5,362,1014,NULL,9,3,12.12,'32','Reparo em Caixa dagua','2025-01-07 07:28:29',NULL,NULL,7,'S'),(6,371,1018,NULL,9,1,12.12,'32','teste2','2025-01-30 10:34:23','2025-01-30 10:34:23',NULL,7,'S'),(7,382,1019,NULL,210,1,2566.65,'34','Neste momento, o Navegador Yandex está baixando um arquivo no formato Office para ser aberto em um dos programas do seu sistema. Mas você também pode pular totalmente o processo de download! Documentos de texto, planilhas e apresentações agora podem ser visualizados e editados aqui mesmo no navegador. Os arquivos serão salvos no Yandex Disk e abertos no editor de documentos.Ferramenta para contar caracteres, palavras, linhas, espaços, vogais e números. Digite o texto e confira o resultado em baixo.','2025-02-06 13:20:09',NULL,NULL,7,'S');
/*!40000 ALTER TABLE `tb_ordemservicoexecucaoservico` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-22 18:18:10
