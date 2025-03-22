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
-- Table structure for table `tb_contrato`
--

DROP TABLE IF EXISTS `tb_contrato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_contrato` (
  `id_contrato` int NOT NULL AUTO_INCREMENT,
  `id_cliente` varchar(45) NOT NULL,
  `id_orcamento` varchar(45) NOT NULL,
  `aplicacoes_periodicas` varchar(45) DEFAULT NULL,
  `valor_total` decimal(10,2) DEFAULT NULL,
  `valor_nagasaki` decimal(10,2) DEFAULT NULL,
  `data_venda` date DEFAULT NULL,
  `tipo_cobranca` varchar(45) DEFAULT NULL,
  `id_condicaopagamento` int DEFAULT NULL,
  `datainicio_execucao` date DEFAULT NULL,
  `datainicio_vencimento` date DEFAULT NULL,
  `meses_garantia` varchar(45) DEFAULT NULL,
  `datafim_garantia` date DEFAULT NULL,
  `quantidade_aplicacoes` varchar(5) DEFAULT NULL,
  `observacoes_contrato` varchar(4235) DEFAULT NULL,
  `data_inclusao` datetime NOT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) NOT NULL,
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id_contrato`)
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_contrato`
--

LOCK TABLES `tb_contrato` WRITE;
/*!40000 ALTER TABLE `tb_contrato` DISABLE KEYS */;
INSERT INTO `tb_contrato` VALUES (1,'9','22','SIM',250.00,250.00,'2024-10-08','Por Serviço',3,'2024-10-08','2024-10-08','3','2025-01-08','1','Teste','2025-01-06 00:33:24','2025-01-06 00:33:25',NULL,'S',7),(2,'9','23','SIM',250.00,250.00,'2024-10-08','Por Serviço',3,'2024-10-08','2024-10-08','1','2024-11-08','1','Teste','2024-12-11 00:09:21','2024-12-11 00:09:21',NULL,'S',7),(3,'9','26','SIM',250.00,250.00,'2024-10-08','Por Serviço',3,'2024-10-08','2024-10-08','1','2024-11-08','5','Teste','2024-12-04 07:34:32','2024-12-04 07:34:32',NULL,'S',7),(4,'9','27','SIM',250.00,250.00,'2024-10-08','Por Serviço',3,'2024-10-08','2024-10-08','3','2025-01-08','1','teste','2024-10-08 22:45:57',NULL,NULL,'S',7),(5,'9','28','SIM',300.52,300.52,'2024-10-08','Por Serviço',3,'2024-10-08','2024-10-08','1','2024-11-08','1','Teste','2024-11-28 14:11:59','2024-11-28 14:11:59',NULL,'S',7),(201,'9','984','SIM',250.00,255.00,'2024-11-12','Por Serviço',3,'2024-11-12','2024-11-12','2','2025-01-12','1','','2024-11-12 09:02:00',NULL,NULL,'S',7),(202,'9','986','SIM',2500.00,2500.00,'2024-11-12','Por Serviço',3,'2024-11-12','2024-11-12','1','2024-12-12','1','','2025-02-03 20:13:51','2025-02-03 20:13:51',NULL,'S',7),(203,'206','988','SIM',352.00,352.00,'2024-11-21','Por Serviço',3,'2024-11-21','2024-11-21','3','2025-02-21','1','teste','2025-01-07 14:15:27','2025-01-07 14:15:27',NULL,'S',7),(204,'9','1000','SIM',25529.00,35329.00,'2024-11-28','Mensal',3,'2024-11-28','2024-11-28','1','2024-12-28','1','teste','2025-02-20 19:47:30','2025-02-20 19:47:30',NULL,'S',7),(205,'9','1004','SIM',0.00,0.00,NULL,'Mensal',3,NULL,NULL,'1',NULL,NULL,'','2024-11-28 14:10:03',NULL,NULL,'S',7),(206,'9','1014','SIM',2522.52,2522.52,'2024-12-03','Por Serviço',6,'2024-12-04','2024-12-04','17','2024-12-21','7','teste','2025-02-20 08:41:49','2025-02-20 08:41:49',NULL,'S',7),(207,'11','967','SIM',752.13,752.13,'2024-12-09','Por Serviço',7,'2024-12-09','2024-12-09','60','2025-02-07','3','teSTE TESTE','2024-12-09 02:13:11',NULL,NULL,'S',7),(208,'9','21','SIM',250.00,250.00,'2024-12-11','Mensal',6,'2024-12-11','2024-12-11','4','2024-12-15','3','Desratização','2024-12-11 17:21:08',NULL,NULL,'S',7),(209,'9','15','SIM',250.00,250.00,'2024-12-11','Mensal',3,'2024-12-11','2024-12-18','1','2024-12-12','1','','2025-02-19 21:17:56','2025-02-19 21:17:56',NULL,'S',7),(210,'9','1018','NÃO',10000.00,10000.00,'2025-01-20','Por Serviço',6,'2025-01-20','2025-01-20','30','2025-02-19','1','Neste momento, o Navegador Yandex está baixando um arquivo no formato Office para ser aberto em um dos programas do seu sistema. Mas você também pode pular totalmente o processo de download! Documentos de texto, planilhas e apresentações agora podem ser visualizados e editados aqui mesmo no navegador. Os arquivos serão salvos no Yandex Disk e abertos no editor de documentos.\n\nNeste momento, o Navegador Yandex está baixando um arquivo no formato Office para ser aberto em um dos programas do seu sistema. Mas você também pode pular totalmente o processo de download! Documentos de texto, planilhas e apresentações agora podem ser visualizados e editados aqui mesmo no navegador. Os arquivos serão salvos no Yandex Disk e abertos no editor de documentos.','2025-03-02 13:27:50','2025-03-02 13:27:50',NULL,'S',7),(211,'210','1019','NÃO',2055.00,2000.00,'2025-02-06','Por Serviço',6,'2025-02-06','2025-02-20','40','2025-03-18','3','Neste momento, o Navegador Yandex está baixando um arquivo no formato Office para ser aberto em um dos programas do seu sistema. Mas você também pode pular totalmente o processo de download! Documentos de texto, planilhas e apresentações agora podem ser visualizados e editados aqui mesmo no navegador. Os arquivos serão salvos no Yandex Disk e abertos no editor de documentos.Ferramenta para contar caracteres, palavras, linhas, espaços, vogais e números. Digite o texto e confira o resultado em baixo.','2025-02-06 12:46:22','2025-02-06 12:46:22',NULL,'S',7);
/*!40000 ALTER TABLE `tb_contrato` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-22 18:18:09
