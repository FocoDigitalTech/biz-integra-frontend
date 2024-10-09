package br.com.onetec.application.service.eventofinanceiro;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetEstoque;
import br.com.onetec.infra.db.model.SetEventoFinanceiro;
import br.com.onetec.infra.db.model.SetGrupoFinanceiro;
import br.com.onetec.infra.db.repository.ISetEventoFinanceiroRepository;
import br.com.onetec.infra.db.repository.ISetGrupoFinanceiroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EventoFinanceiroService {

    private ISetEventoFinanceiroRepository repository;

    @Autowired
    public void initServices (ISetEventoFinanceiroRepository repository1){
        this.repository = repository1;
    }

    public Page<SetEventoFinanceiro> list(Pageable pageable, Specification<SetEventoFinanceiro> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetEventoFinanceiro> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetEventoFinanceiro dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetEventoFinanceiro item) throws Exception {
        try {
            Optional<SetEventoFinanceiro> optional = repository.findById(item.getId_eventofinanceiro());
            SetEventoFinanceiro entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetEventoFinanceiro> findAll() {
        return repository.listAll();
    }
}
