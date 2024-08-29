package br.com.onetec.application.service.estoqueservice;

import br.com.onetec.infra.db.model.SetEstoque;
import br.com.onetec.infra.db.model.SetRegiao;
import br.com.onetec.infra.db.repository.IRegiaoRepository;
import br.com.onetec.infra.db.repository.ISetEstoqueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EstoqueService {

    private ISetEstoqueRepository repository;

    @Autowired
    public void initServices (ISetEstoqueRepository repository1){
        this.repository = repository1;
    }

    public Page<SetEstoque> list(Pageable pageable, Specification<SetEstoque> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetEstoque> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetEstoque dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetEstoque item) throws Exception {
        try {
            repository.delete(item);
        } catch (Exception e){
            throw new Exception();
        }
    }
}
