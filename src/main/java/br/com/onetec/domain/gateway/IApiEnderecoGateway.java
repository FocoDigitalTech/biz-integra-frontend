package br.com.onetec.domain.gateway;

import br.com.onetec.infra.rest.dto.Address;

public interface IApiEnderecoGateway {

    Address getAddressByCep (String cep);
}
