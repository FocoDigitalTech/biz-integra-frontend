package br.com.onetec.application.service.clientesservice;

import br.com.onetec.infra.db.repository.IResponsavelCobrancaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponsavelCobrancaService {

    @Autowired
    IResponsavelCobrancaRepository repository;


}
