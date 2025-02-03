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
-- Table structure for table `tb_ordemservico`
--

DROP TABLE IF EXISTS `tb_ordemservico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_ordemservico` (
  `id_ordemservico` int NOT NULL AUTO_INCREMENT,
  `id_orcamento` int NOT NULL,
  `id_contrato` int DEFAULT NULL,
  `id_cliente` int NOT NULL,
  `id_situacaoservico` int NOT NULL,
  `datainicio_ordemservico` date DEFAULT NULL,
  `diasemanainicio_ordemservico` varchar(45) DEFAULT NULL,
  `horarioinicio_ordemservico` time DEFAULT NULL,
  `quantidade_ordemservico` varchar(45) DEFAULT NULL,
  `intervalo_ordemservico` varchar(45) DEFAULT NULL,
  `nome_pontofocal` varchar(45) DEFAULT NULL,
  `id_funcionarioassistente` int DEFAULT NULL,
  `confirmado_ordemservico` varchar(1) DEFAULT NULL,
  `id_veiculo` int DEFAULT NULL,
  `kminicial_ordemservico` varchar(45) DEFAULT NULL,
  `kmfinal_ordemservico` varchar(45) DEFAULT NULL,
  `ocorrencias_ordemservico` varchar(255) DEFAULT NULL,
  `descricao_ordemservico` varchar(45) DEFAULT NULL,
  `data_inclusao` datetime NOT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) NOT NULL,
  `id_usuario` int NOT NULL,
  `id_endereco` int NOT NULL,
  `id_funcionariotecnico` int DEFAULT NULL,
  PRIMARY KEY (`id_ordemservico`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_ordemservico`
--

LOCK TABLES `tb_ordemservico` WRITE;
/*!40000 ALTER TABLE `tb_ordemservico` DISABLE KEYS */;
INSERT INTO `tb_ordemservico` VALUES (1,1,NULL,1,1,'2024-10-01','Monday','20:00:00','1','1','Carlos',3,NULL,NULL,NULL,NULL,'Entupimento',NULL,'2024-09-30 18:14:16',NULL,NULL,'S',7,1,2),(2,1,NULL,1,1,'2024-10-01',NULL,'18:00:00','1','2','Carlos',5,NULL,NULL,NULL,NULL,'Desaratização',NULL,'2024-09-30 18:16:22',NULL,NULL,'S',7,1,2),(3,1,NULL,1,2,'2024-10-01',NULL,'21:00:00','1','1','Carlos',3,NULL,NULL,NULL,NULL,'Fazer Limpeza',NULL,'2024-09-30 18:19:23',NULL,NULL,'S',7,1,2),(4,1,NULL,1,1,'2024-10-01',NULL,'07:00:00','','1','Carlos',3,NULL,NULL,NULL,NULL,'Caixa Daqgua',NULL,'2024-09-30 18:21:15',NULL,NULL,'S',7,2,2),(5,1,NULL,1,1,'2024-10-01',NULL,'02:00:00','1','','Carlos',4,NULL,NULL,NULL,NULL,'Teste',NULL,'2024-09-30 18:23:21',NULL,NULL,'S',7,2,3),(6,1,NULL,2,1,'2024-10-01',NULL,'21:00:00','1','1','Roberta',6,'s',NULL,NULL,NULL,'Teste',NULL,'2024-09-30 18:23:21',NULL,NULL,'S',7,3,6),(7,1,NULL,2,1,'2024-10-01',NULL,'22:00:00','1','1','Roberta',6,'s',NULL,NULL,NULL,'Teste',NULL,'2024-09-30 18:23:21',NULL,NULL,'S',7,4,6),(8,1,NULL,2,1,'2024-10-01',NULL,'22:00:00','1','1','Roberta',6,'s',NULL,NULL,NULL,'Teste',NULL,'2024-09-30 18:23:21',NULL,NULL,'S',7,5,7),(9,1,NULL,2,1,'2024-10-01',NULL,'22:00:00','1','1','Roberta',6,'s',NULL,NULL,NULL,'Teste',NULL,'2024-09-30 18:23:21',NULL,NULL,'S',7,6,7),(10,5,NULL,8,1,'2024-10-03','quinta-feira','01:00:00','1','','Datena',2,'S',NULL,NULL,NULL,'teste',NULL,'2024-10-03 22:36:23',NULL,NULL,'S',7,9,NULL),(11,3,NULL,8,1,'2024-10-03','quinta-feira','08:00:00','1','1','Datena',6,'S',NULL,NULL,NULL,'Teste',NULL,'2024-10-03 22:45:01',NULL,NULL,'S',7,9,NULL),(12,2,NULL,8,1,'2024-10-03','quinta-feira','01:00:00','1','1','Datena',2,'S',NULL,NULL,NULL,'teste',NULL,'2024-10-03 23:00:29',NULL,NULL,'S',7,9,NULL),(13,5,NULL,8,1,'2024-10-06','domingo','10:00:00','1','1','Amanda',2,'S',NULL,NULL,NULL,'',NULL,'2024-10-06 12:19:44',NULL,NULL,'S',7,9,3),(14,2,NULL,8,1,'2024-10-07','segunda-feira','18:00:00','1','1','Sergio',7,'S',NULL,NULL,NULL,'',NULL,'2024-10-07 08:14:22',NULL,NULL,'N',7,9,6),(15,5,NULL,8,1,'2024-10-07','segunda-feira','16:00:00','1','1','Datena',5,'S',NULL,NULL,NULL,'teste',NULL,'2024-10-07 08:17:29',NULL,NULL,'N',7,10,6),(16,13,NULL,9,1,'2024-10-07','segunda-feira','08:00:00','1','30','Carlos',5,'S',NULL,NULL,NULL,'Infestação de ratos',NULL,'2024-10-07 17:44:16',NULL,NULL,'N',7,77,7),(17,27,NULL,9,1,'2024-10-08','terça-feira','14:00:00','1','1','Edna',6,'S',NULL,NULL,NULL,'1',NULL,'2024-10-08 23:41:15',NULL,NULL,'S',7,77,7);
/*!40000 ALTER TABLE `tb_ordemservico` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-09  0:10:13
