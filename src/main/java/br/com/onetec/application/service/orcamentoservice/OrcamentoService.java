package br.com.onetec.application.service.orcamentoservice;

import br.com.onetec.infra.db.model.SetFluxoRecebimentoPagamento;
import br.com.onetec.infra.db.model.SetOrcamento;
import br.com.onetec.infra.db.repository.ISetFuxoRecebimentoPagamentoRepository;
import br.com.onetec.infra.db.repository.ISetOrcamentoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrcamentoService {

    private ISetOrcamentoRepository repository;

    @Autowired
    public void initServices (ISetOrcamentoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetOrcamento> list(Pageable pageable, Specification<SetOrcamento> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetOrcamento> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetOrcamento dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetOrcamento item) throws Exception {
        try {
            repository.delete(item);
        } catch (Exception e){
            throw new Exception();
        }
    }
}
