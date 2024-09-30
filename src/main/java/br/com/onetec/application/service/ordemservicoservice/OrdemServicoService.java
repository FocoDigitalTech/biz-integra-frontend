package br.com.onetec.application.service.ordemservicoservice;

import br.com.onetec.infra.db.model.SetOrcamento;
import br.com.onetec.infra.db.model.SetOrdemServico;
import br.com.onetec.infra.db.repository.ISetOrcamentoRepository;
import br.com.onetec.infra.db.repository.ISetOrdemServicoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class OrdemServicoService {

    private ISetOrdemServicoRepository repository;

    @Autowired
    public void initServices (ISetOrdemServicoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetOrdemServico> list(Pageable pageable, Specification<SetOrdemServico> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetOrdemServico> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public List<SetOrdemServico> findAllByOrcamentoId(Integer orcamentoid){

        return repository.listAllByOrcamentoId(orcamentoid);
    }

    public void save(SetOrdemServico dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetOrdemServico item) throws Exception {
        try {
            repository.delete(item);
        } catch (Exception e){
            throw new Exception();
        }
    }
}
