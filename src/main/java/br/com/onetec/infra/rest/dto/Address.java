package br.com.onetec.infra.rest.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@Builder
public class Address {

    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
}
