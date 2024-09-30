package br.com.onetec.application.service.eventofinanceiro;

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

import java.util.List;

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
            repository.delete(item);
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetEventoFinanceiro> findAll() {
        return repository.listAll();
    }
}
