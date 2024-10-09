package br.com.onetec.application.service.clientesservice;

import br.com.onetec.infra.db.model.SetResponsavelAgendamento;
import br.com.onetec.infra.db.model.SetResponsavelCobranca;
import br.com.onetec.infra.db.repository.IResponsavelCobrancaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class ResponsavelCobrancaService {

    @Autowired
    IResponsavelCobrancaRepository repository;


    public void save(SetResponsavelCobranca cobranca) {
        log.info("Salvando Responsável Cobrança :" + cobranca.toString());
        repository.save(cobranca);
    }

    public SetResponsavelCobranca find(Integer id_cliente) {
        SetResponsavelCobranca cobranca = repository.findByCliente(id_cliente);
        if (cobranca != null) {
            return cobranca;
        } else {
            return null;
        }
    }

    public void update(SetResponsavelCobranca cobranca) {
        Optional<SetResponsavelCobranca> optional = repository.findById(cobranca.getId_responsavelcobranca());
        SetResponsavelCobranca entity = optional.get();
        entity = cobranca;
        entity.setData_alteracao(LocalDateTime.now());
        repository.save(entity);
    }
}
