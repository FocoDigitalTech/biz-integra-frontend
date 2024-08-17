package br.com.onetec.application.service.clientesservice;

import br.com.onetec.infra.db.model.SetResponsavelAprovacao;
import br.com.onetec.infra.db.model.SetResponsavelCobranca;
import br.com.onetec.infra.db.repository.IResponsavelAprovacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResponsavelAprovacaoService {

    @Autowired
    IResponsavelAprovacaoRepository repository;

    public void save(SetResponsavelAprovacao aprovacao) {
        log.info("Salvando Responsável aprovacao :" + aprovacao.toString());
        repository.save(aprovacao);
    }

    public SetResponsavelAprovacao find(Integer id_cliente) {
        SetResponsavelAprovacao aprovacao = repository.findByCliente(id_cliente);
        if (aprovacao != null) {
            return aprovacao;
        } else {
            return null;
        }
    }
}
