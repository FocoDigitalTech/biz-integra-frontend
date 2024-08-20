package br.com.onetec.application.service.utilservices;

import br.com.onetec.domain.entity.EApiEnderecoResponse;
import br.com.onetec.domain.usecase.apienderecousecase.IApiEnderecoUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiEnderecoService {

    private final IApiEnderecoUseCase useCase;

    @Autowired
    public ApiEnderecoService(IApiEnderecoUseCase useCase) {
        this.useCase = useCase;
    }

    public EApiEnderecoResponse get (String cep) {
        return useCase.get(cep);
    }
}
