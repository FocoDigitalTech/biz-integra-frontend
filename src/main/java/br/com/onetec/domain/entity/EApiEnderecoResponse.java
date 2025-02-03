package br.com.onetec.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EApiEnderecoResponse {

    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;

}
