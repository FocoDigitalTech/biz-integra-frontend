package br.com.onetec.infra.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_enderecos")
public class SetEnderecos{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_endereco;
    private Integer id_cliente;
    private Integer id_tipoimovel;
    private String area_imovel;
    private String numero_imovel;
    private Integer id_estado;
    private String endereco_imovel;
    private String complemento_imovel;
    private String bairro_imovel;
    private String cep_imovel;
    private String cidade_imovel;
    private String nome_responsavel;
    private String pagina_guia;
    private String latitude_longitude;
    private String telefone_local;
    private Integer id_regiao;
    private String ponto_referencia;
    private LocalDateTime data_inclusao;
    private LocalDateTime data_alteracao;
    private LocalDateTime data_exclusao;
    private Integer id_usuario;
    private String ativo;
}
