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
-- Table structure for table `tb_arquivosorcamento`
--

DROP TABLE IF EXISTS `tb_arquivosorcamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_arquivosorcamento` (
  `id_arquivosorcamento` int NOT NULL AUTO_INCREMENT,
  `id_orcamento` int NOT NULL,
  `id_cliente` int NOT NULL,
  `caminho_arquivoorcamento` varchar(4345) DEFAULT NULL,
  `nome_arquivoorcamento` varchar(845) DEFAULT NULL,
  `observacao_arquivoorcamento` varchar(4545) DEFAULT NULL,
  `data_inclusao` datetime NOT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `id_usuario` int NOT NULL,
  `ativo` varchar(45) NOT NULL,
  PRIMARY KEY (`id_arquivosorcamento`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_arquivosorcamento`
--

LOCK TABLES `tb_arquivosorcamento` WRITE;
/*!40000 ALTER TABLE `tb_arquivosorcamento` DISABLE KEYS */;
INSERT INTO `tb_arquivosorcamento` VALUES (1,967,11,'C:/SYSTEM_files_NAGASAKI/filesidcliente11orcamento/Solicitação_Vale Refeição (1).docx','Solicitação_Vale Refeição (1).docx',NULL,'2024-10-25 01:22:08',NULL,NULL,7,'S'),(2,967,11,'C:/SYSTEM_files_NAGASAKI/filesidcliente11orcamento/Formulário SulAmérica_2021.doc','Formulário SulAmérica_2021.doc',NULL,'2024-10-25 01:22:08',NULL,'2024-11-04 23:11:46',7,'N'),(3,952,11,'C:/SYSTEM_files_NAGASAKI/filesidcliente11orcamento/html.png','html.png',NULL,'2024-11-04 18:10:43',NULL,'2024-11-04 23:11:46',7,'N'),(4,952,11,'C:/SYSTEM_files_NAGASAKI/filesidcliente11orcamento/Danilo Luiz - Java Developer.pdf','Danilo Luiz - Java Developer.pdf',NULL,'2024-11-04 18:10:43',NULL,NULL,7,'S'),(5,966,11,'C:/SYSTEM_files_NAGASAKI/filesidcliente11orcamento/Danilo Luiz - Java Developer (3).pdf','Danilo Luiz - Java Developer (3).pdf',NULL,'2024-11-04 22:56:48',NULL,'2024-11-04 22:57:43',7,'N'),(6,966,11,'C:/SYSTEM_files_NAGASAKI/filesidcliente11orcamento/Boleto1221472_14990000000002284.pdf','Boleto1221472_14990000000002284.pdf',NULL,'2024-11-04 22:56:48',NULL,'2024-11-04 22:57:43',7,'N'),(7,966,11,'C:/SYSTEM_files_NAGASAKI/filesidcliente11orcamento/DC Template CE - PT 1.pdf','DC Template CE - PT 1.pdf',NULL,'2024-11-04 22:57:41',NULL,NULL,7,'S'),(8,966,11,'C:/SYSTEM_files_NAGASAKI/filesidcliente11orcamento/Boleto - 4525 - DANILO LUIZ DA SILVA.pdf','Boleto - 4525 - DANILO LUIZ DA SILVA.pdf',NULL,'2024-11-04 23:03:47',NULL,'2024-11-04 23:04:14',7,'N'),(9,966,11,'C:/SYSTEM_files_NAGASAKI/filesidcliente11orcamento/Documento-de-arquitetura-de-software.docx','Documento-de-arquitetura-de-software.docx',NULL,'2024-11-04 23:08:54',NULL,'2024-11-04 23:09:11',7,'N'),(10,982,205,'C:/SYSTEM_files_NAGASAKI/filesidcliente205orcamento/Boleto1221472_14990000000002284.pdf','Boleto1221472_14990000000002284.pdf',NULL,'2024-11-11 21:00:23',NULL,NULL,7,'S'),(11,982,205,'C:/SYSTEM_files_NAGASAKI/filesidcliente205orcamento/html (1).png','html (1).png',NULL,'2024-11-11 21:07:00',NULL,NULL,7,'S'),(12,983,205,'C:/SYSTEM_files_NAGASAKI/filesidcliente205orcamento/simulacao_tenda.png','simulacao_tenda.png',NULL,'2024-11-11 21:43:42',NULL,NULL,7,'S'),(13,1020,211,'C:/SYSTEM_files_NAGASAKI/filesidcliente211orcamento/matriz_ordem_serviço131019210Jardim Andaraíordem_serviço.docx','matriz_ordem_serviço131019210Jardim Andaraíordem_serviço.docx',NULL,'2025-02-06 13:42:36',NULL,NULL,7,'S'),(14,1020,211,'C:/SYSTEM_files_NAGASAKI/filesidcliente211orcamento/001 - Dados Ordem de Serviço - Mudar tamanho dos campos (1).pdf','001 - Dados Ordem de Serviço - Mudar tamanho dos campos (1).pdf',NULL,'2025-02-06 13:44:22',NULL,NULL,7,'S'),(15,1020,211,'C:/SYSTEM_files_NAGASAKI/filesidcliente211orcamento/sabesp-fornecimento-593077652001-acordo-0000007726478124-parcela-5.pdf','sabesp-fornecimento-593077652001-acordo-0000007726478124-parcela-5.pdf',NULL,'2025-02-06 13:44:22',NULL,NULL,7,'S'),(16,1020,211,'C:/SYSTEM_files_NAGASAKI/filesidcliente211orcamento/Erro cadastro de contato com os clientes.pdf','Erro cadastro de contato com os clientes.pdf',NULL,'2025-02-06 13:44:22',NULL,NULL,7,'S'),(17,1020,211,'C:/SYSTEM_files_NAGASAKI/filesidcliente211orcamento/cinetv_ycio-site.apk','cinetv_ycio-site.apk',NULL,'2025-02-06 13:56:47',NULL,NULL,7,'S');
/*!40000 ALTER TABLE `tb_arquivosorcamento` ENABLE KEYS */;
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
