USE nagazaki_db;

CREATE TABLE `tb_cliente` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `nome_cliente` varchar(255) DEFAULT NULL,
  `nome_fantasia_cliente` varchar(255) DEFAULT NULL,
  `telefone_cliente` varchar(255) DEFAULT NULL,
  `fax_cliente` varchar(255) DEFAULT NULL,
  `email_cliente` varchar(255) DEFAULT NULL,
  `nome_contato_cliente` varchar(255) DEFAULT NULL,
  `cargo_contato_cliente` varchar(255) DEFAULT NULL,
  `id_anuncio` int DEFAULT NULL,
  `id_indicacao` int DEFAULT NULL,
  `cpf_cgc_cliente` varchar(255) DEFAULT NULL,
  `iest_cliente` varchar(255) DEFAULT NULL,
  `pf_pj_cliente` varchar(255) DEFAULT NULL,
  `observacoes_cliente` varchar(255) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_departamento` (
  `id_departamento` int NOT NULL AUTO_INCREMENT,
  `descricao_departamento` varchar(255) DEFAULT NULL,
  `id_funcionario` int DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_departamento`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_enderecos` (
  `id_endereco` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int DEFAULT NULL,
  `id_tipoimovel` int DEFAULT NULL,
  `area_imovel` varchar(255) DEFAULT NULL,
  `numero_imovel` varchar(255) DEFAULT NULL,
  `id_estado` int DEFAULT NULL,
  `endereco_imovel` varchar(255) DEFAULT NULL,
  `complemento_imovel` varchar(255) DEFAULT NULL,
  `bairro_imovel` varchar(255) DEFAULT NULL,
  `cep_imovel` varchar(255) DEFAULT NULL,
  `cidade_imovel` varchar(255) DEFAULT NULL,
  `nome_responsavel` varchar(255) DEFAULT NULL,
  `pagina_guia` varchar(255) DEFAULT NULL,
  `latitude_longitude` varchar(255) DEFAULT NULL,
  `telefone_local` varchar(255) DEFAULT NULL,
  `id_regiao` int DEFAULT NULL,
  `ponto_referencia` varchar(255) DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  `ativo` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id_endereco`),
  KEY `id_cliente` (`id_cliente`),
  KEY `id_estado` (`id_estado`),
  CONSTRAINT `tb_enderecos_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `tb_cliente` (`id_cliente`),
  CONSTRAINT `tb_enderecos_ibfk_2` FOREIGN KEY (`id_estado`) REFERENCES `tb_estado` (`id_estado`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_estado` (
  `id_estado` int NOT NULL AUTO_INCREMENT,
  `uf_estado` varchar(255) DEFAULT NULL,
  `nome_estado` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_estado`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_fornecedores` (
  `id_fornecedor` int NOT NULL AUTO_INCREMENT,
  `data_cadastro` datetime DEFAULT NULL,
  `id_setoratuacao` int DEFAULT NULL,
  `tipo_naturezajuridica` varchar(255) DEFAULT NULL,
  `numero_naturezajuridica` varchar(255) DEFAULT NULL,
  `razaosocial_fornecedor` varchar(255) DEFAULT NULL,
  `nomefantasia_fornecedor` varchar(255) DEFAULT NULL,
  `cep_fornecedor` varchar(255) DEFAULT NULL,
  `endereco_fornecedor` varchar(255) DEFAULT NULL,
  `numero_endereco` varchar(255) DEFAULT NULL,
  `bairro_fornecedor` varchar(255) DEFAULT NULL,
  `cidade_fornecedor` varchar(255) DEFAULT NULL,
  `id_estado` int DEFAULT NULL,
  `telefone_fornecedor` varchar(255) DEFAULT NULL,
  `email_fornecedor` varchar(255) DEFAULT NULL,
  `nomecontato_fornecedor` varchar(255) DEFAULT NULL,
  `cargocontato_fornecedor` varchar(255) DEFAULT NULL,
  `inscicaoestadual_fornecedor` varchar(255) DEFAULT NULL,
  `observacao_fornecedor` varchar(255) DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(255) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_fornecedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_funcionario` (
  `id_funcionario` int NOT NULL AUTO_INCREMENT,
  `id_departamento` int DEFAULT NULL,
  `nome_funcionario` varchar(255) DEFAULT NULL,
  `nome_carteira` varchar(255) DEFAULT NULL,
  `endereco_funcionario` varchar(255) DEFAULT NULL,
  `numeroimovel_funcionario` varchar(45) DEFAULT NULL,
  `complemento_funcionario` varchar(255) DEFAULT NULL,
  `bairro_funcionario` varchar(255) DEFAULT NULL,
  `cep_funcionario` varchar(255) DEFAULT NULL,
  `cidade_funcionario` varchar(255) DEFAULT NULL,
  `id_estado` int DEFAULT NULL,
  `celular_funcionario` varchar(255) DEFAULT NULL,
  `rg_funcionario` varchar(255) DEFAULT NULL,
  `cpf_funcionario` varchar(255) DEFAULT NULL,
  `titulo_eleitor` varchar(255) DEFAULT NULL,
  `reservista_militar` varchar(255) DEFAULT NULL,
  `numero_ctps` varchar(255) DEFAULT NULL,
  `serie_ctps` varchar(255) DEFAULT NULL,
  `pis_funcionario` varchar(255) DEFAULT NULL,
  `cnh_funcionario` varchar(255) DEFAULT NULL,
  `vencimento_cnh` varchar(255) DEFAULT NULL,
  `data_admissao` datetime DEFAULT NULL,
  `data_desligamento` datetime DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_funcionario`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_responsavelagendamento` (
  `id_responsavelagendamento` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int DEFAULT NULL,
  `nome_agendamento` varchar(255) DEFAULT NULL,
  `telefone_fixo` varchar(255) DEFAULT NULL,
  `telefone_celular` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `cgc_cpf` varchar(255) DEFAULT NULL,
  `inscricao_estatual` varchar(255) DEFAULT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `data_agendamento` datetime DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(45) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_responsavelagendamento`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `tb_responsavelagendamento_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `tb_cliente` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_responsavelaprovacao` (
  `id_responsavelaprovacao` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int DEFAULT NULL,
  `nome_aprovacao` varchar(255) DEFAULT NULL,
  `telefone_fixo` varchar(255) DEFAULT NULL,
  `telefone_celular` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `cgc_cpf` varchar(255) DEFAULT NULL,
  `inscricao_estatual` varchar(255) DEFAULT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `valor_aprovado` int DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(45) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_responsavelaprovacao`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `tb_responsavelaprovacao_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `tb_cliente` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_responsavelcobranca` (
  `id_responsavelcobranca` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int DEFAULT NULL,
  `nome_cobranca` varchar(255) DEFAULT NULL,
  `telefone_fixo` varchar(255) DEFAULT NULL,
  `telefone_celular` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `cgc_cpf` varchar(255) DEFAULT NULL,
  `inscricao_estatual` varchar(255) DEFAULT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `valor_cobranca` float DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(45) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_responsavelcobranca`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `tb_responsavelcobranca_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `tb_cliente` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_tipoimovel` (
  `id_tipoimovel` int NOT NULL AUTO_INCREMENT,
  `descricao_tipoimovel` varchar(255) DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_tipoimovel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_usuarios` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `id_funcionario` int DEFAULT NULL,
  `email_usuario` varchar(145) DEFAULT NULL,
  `nome_usuario` varchar(255) DEFAULT NULL,
  `senha_usuario` varchar(255) DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `data_exclusao` datetime DEFAULT NULL,
  `ativo` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



ALTER TABLE `tb_responsavelagendamento` ADD FOREIGN KEY (`id_cliente`) REFERENCES `tb_cliente` (`id_cliente`);

ALTER TABLE `tb_responsavelcobranca` ADD FOREIGN KEY (`id_cliente`) REFERENCES `tb_cliente` (`id_cliente`);

ALTER TABLE `tb_responsavelaprovacao` ADD FOREIGN KEY (`id_cliente`) REFERENCES `tb_cliente` (`id_cliente`);

ALTER TABLE `tb_enderecos` ADD FOREIGN KEY (`id_cliente`) REFERENCES `tb_cliente` (`id_cliente`);

ALTER TABLE `tb_enderecos` ADD FOREIGN KEY (`id_estado`) REFERENCES `tb_estado` (`id_estado`);

ALTER TABLE `tb_usuarios` ADD FOREIGN KEY (`id_usuario`) REFERENCES `tb_cliente` (`id_usuario`);

ALTER TABLE `tb_funcionario` ADD FOREIGN KEY (`id_funcionario`) REFERENCES `tb_usuarios` (`id_funcionario`);

ALTER TABLE `tb_departamento` ADD FOREIGN KEY (`id_departamento`) REFERENCES `tb_funcionario` (`id_departamento`);

