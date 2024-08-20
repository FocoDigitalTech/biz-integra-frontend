package br.com.onetec.domain.usecase.apienderecousecase;

import br.com.onetec.domain.entity.EApiEnderecoResponse;

public interface IApiEnderecoUseCase {

    EApiEnderecoResponse get (String cep);
}
