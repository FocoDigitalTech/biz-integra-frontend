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
-- Table structure for table `tb_cliente`
--

DROP TABLE IF EXISTS `tb_cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_cliente` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `nome_cliente` varchar(255) DEFAULT NULL,
  `nome_fantasia_cliente` varchar(255) DEFAULT NULL,
  `telefone_cliente` varchar(255) DEFAULT NULL,
  `email_cliente` varchar(255) DEFAULT NULL,
  `nome_contato_cliente` varchar(255) DEFAULT NULL,
  `cargo_contato_cliente` varchar(255) DEFAULT NULL,
  `id_anuncio` int DEFAULT NULL,
  `id_indicacao` int DEFAULT NULL,
  `tipo_naturezajuridica` varchar(25) DEFAULT NULL,
  `cpf_cgc_cliente` varchar(255) DEFAULT NULL,
  `iest_cliente` varchar(255) DEFAULT NULL,
  `numero_naturezajuridica` varchar(265) DEFAULT NULL,
  `observacoes_cliente` varchar(4235) DEFAULT NULL,
  `marca_cliente` varchar(255) DEFAULT NULL,
  `administradora_cliente` varchar(255) DEFAULT NULL,
  `celular_cliente` varchar(255) DEFAULT NULL,
  `hora_ligacao_cliente` time DEFAULT NULL,
  `id_tipo_imovel` int DEFAULT NULL,
  `endereco_cliente` varchar(255) DEFAULT NULL,
  `numero_res_cliente` decimal(10,0) DEFAULT NULL,
  `complemento_cliente` varchar(255) DEFAULT NULL,
  `bairro_cliente` varchar(255) DEFAULT NULL,
  `cep_cliente` varchar(255) DEFAULT NULL,
  `cidade_cliente` varchar(255) DEFAULT NULL,
  `id_estado` int DEFAULT NULL,
  `responsavel_cliente` varchar(255) DEFAULT NULL,
  `ponto_referencia_cliente` varchar(255) DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_cliente`
--

LOCK TABLES `tb_cliente` WRITE;
/*!40000 ALTER TABLE `tb_cliente` DISABLE KEYS */;
INSERT INTO `tb_cliente` VALUES (1,'ESCOLA OBJETIVO','ESCOLA OBJETIVO','1144424000','roberto@666.com.br','44454220','',1,1,'Juridica','666.666.666.06','AG',NULL,'Cliente com Muito Rato','Marca Ltda','Concecionaria','(11) 93073-7274','12:30:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Carlos','Teste','2024-08-14 21:29:43',NULL,'2024-10-03 15:26:36','N',7),(2,'ESCOLA ZIZ','ESCOLA ZIZ','1144424000','teste@666.com.br','44454220','',1,1,'Fisica','666.666.666.06','AG',NULL,'Cliente com Muito Rato','Marca Ltda','Concecionaria','(11) 93073-7274','02:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Carlos','Teste','2024-08-14 22:37:49',NULL,'2024-10-03 15:26:40','N',7),(3,'','','','','','',1,1,'','','AG',NULL,'','Marca Ltda','Concecionaria','',NULL,1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Carlos','Teste','2024-08-19 21:58:33',NULL,'2024-10-03 15:16:32','N',1),(4,'ESCOLA TEC1','ESCOLA TEC1','1144424000','teste@666.com.br','a','',1,1,'Pessoa Fisica','348.805.388-17','AG',NULL,'dsaasfffffffffffffffffffffffffffffffffffffffff','Marca Ltda','Concecionaria','(11) 93333-3333','00:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Carlos','Teste','2024-08-24 16:32:06',NULL,'2024-10-03 15:26:43','N',7),(5,'Cartorio Municipal','Cartorio Municipal','1144424000','roberto@666.com.br','1144424000','',1,1,'Pessoa Juridica','014.465.454-564564','AG',NULL,'Ligar Depois','Marca Ltda','Concecionaria','(11) 93030-6545','09:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Carlos','Teste','2024-10-01 18:55:24',NULL,'2024-10-03 15:26:12','N',7),(6,'FocoDigital','FocoDigital','1144454004','daniloluiz750@gmail.com','Danilo','',7,1,'Pessoa Fisica','32326566565','AG',NULL,'Teste','Marca Ltda','Concecionaria','11930737274','05:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Carlos','Teste','2024-10-01 20:31:59',NULL,'2024-10-03 15:32:09','N',7),(7,'Carrefour','Carrefour','1144424000','teste@666.com.br','Alberto','',4,1,'Pessoa Juridica','65546110156456','54564654000021',NULL,'Cliente com duvidas de contrato','Marca Ltda','Concecionaria','11998556655','12:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Carlos','Teste','2024-10-03 15:33:28',NULL,'2024-10-03 15:34:30','N',7),(8,'ESCOLA Senenbi','ESCOLA Senenbi','1144424000','roberto@666.com.br','Amanda','',5,1,'Pessoa Juridica','16556556000001','111224650000012',NULL,'Teste','Marca Ltda','Concecionaria','11930737274','09:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Amanda','Teste','2024-10-03 15:43:11','2024-10-05 13:37:17','2024-10-14 10:54:08','N',7),(9,'Seasa','Seasa','1144424000','teste@666.com.br','Carlos','',4,1,'Pessoa Juridica','123546000010001','13335464502',NULL,'Neste momento, o Navegador Yandex está baixando um arquivo no formato Office para ser aberto em um dos programas do seu sistema. Mas você também pode pular totalmente o processo de download! Documentos de texto.','Marca Ltda','Concecionaria','11930737274','12:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Carlos','Teste','2024-10-07 17:24:37','2025-02-06 09:29:44',NULL,'S',7),(10,'ESCOLA TEC1','ESCOLA TEC1','1144424000','teste@666.com.br','Amorin','',4,1,'Pessoa Juridica','65454564564500','6455646465',NULL,'teste','Marca Ltda','Concecionaria','02167080002','06:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Amorin','Teste','2024-10-14 10:52:43','2024-10-14 10:54:23','2024-10-14 11:13:46','N',7),(11,'ESCOLA ZIZ EDUCADORA LDTA','ESCOLA ZIZ EDUCADORA LDTA','1144424000','roberto@666.com.br','Dias','',4,1,'Pessoa Juridica','56465451321001','13123123132',NULL,'teste','Marca Ltda','Concecionaria','11444240000','03:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Dias','Teste','2024-10-14 11:15:56','2024-12-09 02:11:41',NULL,'S',7),(201,'Dunlop','Dunlop','1144424000','roberto@666.com.br','Carla','',4,1,'Pessoa Fisica','31315646545','456789789123132',NULL,'','Marca Ltda','Concecionaria','00321213156','15:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Carla','Teste','2024-10-31 23:35:30','2024-12-20 16:38:01',NULL,'S',7),(202,'Escola Municipal','Escola Municipal','1144424000','teste@666.com.br','Carla','',4,1,'Pessoa Juridica','31654546545454','456789789123132',NULL,'','Marca Ltda','Concecionaria','21312365456','03:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Carla','Teste','2024-10-31 23:36:52','2024-11-07 15:02:50',NULL,'S',7),(203,'Teste','Teste','11944987034','lidyazu@gmail.com','teste','',5,1,'Pessoa Fisica','35545645646','006164650605',NULL,'teste','Marca Ltda','Concecionaria','16454465564','05:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'teste','Teste','2024-11-07 09:12:28','2024-11-10 12:52:09',NULL,'S',7),(204,'Teste2','Teste2','11996084315','nadiacunha@live.com','Teste2','',5,1,'Pessoa Fisica','54654564564','15615615',NULL,'Teste2','Marca Ltda','Concecionaria','12315645645','02:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Teste2','Teste','2024-11-07 09:20:58','2024-11-10 12:46:18',NULL,'S',7),(205,'TesteDa','TesteDa','465461656','daiane.luiza.silva@gmail.com','TEste','',4,1,'Pessoa Juridica','00005454515115','0000000000000003215551',NULL,'teste','Marca Ltda','Concecionaria','12312313212','03:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'TEste','Teste','2024-11-11 20:56:10','2024-11-11 21:41:58',NULL,'S',7),(206,'Zoo teste','Zoo teste','1144655655','teste@gmail.com','teste','',5,1,'Pessoa Juridica','00000054556445','0000006545645',NULL,'teste','Marca Ltda','Concecionaria','11995565655','02:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'teste','Teste','2024-11-21 00:52:10','2024-11-21 00:55:30',NULL,'S',7),(207,'Cartorio Municipal','Cartorio Municipal','1144424000','roberto@666.com.br','','',4,1,'Pessoa Juridica','65653201111111','000000023456',NULL,'Verificar orçamento','Marca Ltda','Concecionaria','11444240000','01:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'','Teste','2025-01-07 14:20:54','2025-01-07 14:21:29',NULL,'S',7),(208,'Dedrex','Dedrex','3333333333','roberto@666.com.br','Roberto','',4,1,'Pessoa Fisica','11111111111','',NULL,'testeeeeeeeeeeeeeeeeee','Marca Ltda','Concecionaria','11111111111','06:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Roberto','Teste','2025-01-30 10:38:00','2025-01-30 10:46:34',NULL,'S',7),(209,'Cliente1','Cliente1','0000000000','carlos@testerweassadasd.com','2222','',4,1,'Pessoa Fisica','22222222222','2222222222222222',NULL,'2','Marca Ltda','Concecionaria','00000000000','00:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'2222','Teste','2025-01-30 10:47:17','2025-01-30 10:47:37',NULL,'S',7),(210,'Jardim Andaraí','Jardim Andaraí','1144454004','teste@666.com.br','vazamentos','',4,1,'Pessoa Juridica','35525332333002','12345646',NULL,'Ferramenta para contar caracteres, palavras, linhas, espaços, vogais e números. Digite o texto e confira o resultado em baixo.Ferramenta para contar caracteres, palavras, linhas, espaços, vogais e números. Digite o texto e confira o resultado em baixo.Ferramenta para contar caracteres, palavras, linhas, espaços, vogais e números. Digite o texto e confira o resultado em baixo.','Marca Ltda','Concecionaria','25523232322','10:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'vazamentos','Teste','2025-02-06 12:41:52','2025-02-06 12:42:45',NULL,'S',7),(211,'Cartorio Municipal','Cartorio Municipal','1144454004','','','',4,1,NULL,'','',NULL,'','Marca Ltda','Concecionaria','',NULL,1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'','Teste','2025-02-06 13:35:35','2025-02-06 13:36:05',NULL,'S',7),(212,'Teste','Teste','1144424000','roberto@666.com.br','teste','',NULL,1,'Pessoa Fisica','11111111111','',NULL,'','Marca Ltda','Concecionaria','16555555555','02:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Carlos','Teste','2025-02-19 21:19:32',NULL,NULL,'S',7),(213,'Andaraí Jardim','Andaraí Jardim','1144424000','roberto@666.com.br','Parque Hippolyto','',NULL,1,'Pessoa Fisica','43222222222','',NULL,'','Marca Ltda','Concecionaria','1144424000','10:00:00',1,'Rua 1',655,'Complemento','Teste','000000','Teste',1,'Parque Hippolyto','Teste','2025-02-19 21:23:31','2025-02-19 21:23:56',NULL,'S',7);
/*!40000 ALTER TABLE `tb_cliente` ENABLE KEYS */;
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
