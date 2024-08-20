package br.com.onetec.domain.usecase.apienderecousecase.imp;

import br.com.onetec.domain.entity.EApiEnderecoResponse;
import br.com.onetec.domain.gateway.IApiEnderecoGateway;
import br.com.onetec.domain.usecase.apienderecousecase.IApiEnderecoUseCase;
import br.com.onetec.infra.rest.dto.Address;
import org.springframework.stereotype.Service;

@Service
public class IApiEnderecoUseCaseImp implements IApiEnderecoUseCase {

    private final IApiEnderecoGateway provider;

    public IApiEnderecoUseCaseImp(IApiEnderecoGateway provider) {
        this.provider = provider;
    }


    @Override
    public EApiEnderecoResponse get(String cep) {
        Address endereco = provider.getAddressByCep(cep);

        return EApiEnderecoResponse.builder()
                .bairro(endereco.getBairro())
                .localidade(endereco.getLocalidade())
                .logradouro(endereco.getLogradouro())
                .uf(endereco.getUf())
                .build();
    }
}
