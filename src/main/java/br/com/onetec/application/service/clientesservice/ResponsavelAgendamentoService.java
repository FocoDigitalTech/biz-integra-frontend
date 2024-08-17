package br.com.onetec.application.service.clientesservice;

import br.com.onetec.infra.db.model.SetResponsavelAgendamento;
import br.com.onetec.infra.db.repository.IResponsavelAgendamentoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ResponsavelAgendamentoService {

    @Autowired
    IResponsavelAgendamentoRepository repository;

    public void save(SetResponsavelAgendamento agendamento) {
        log.info("Salvando Responsável agendamento :" + agendamento.toString());
        repository.save(agendamento);
    }

    public SetResponsavelAgendamento find(Integer id_cliente) {
        SetResponsavelAgendamento agendamento = repository.findByCliente(id_cliente);
        if (agendamento != null) {
            return agendamento;
        } else {
            return null;
        }
    }
}
