package br.com.onetec.application.service.fornecedorservice;

import br.com.onetec.infra.db.model.SetFornecedor;
import br.com.onetec.infra.db.model.SetFuncionario;
import br.com.onetec.infra.db.repository.IFornecedorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FornecedorService {

    private final IFornecedorRepository repository;

    public FornecedorService(IFornecedorRepository repository) {
        this.repository = repository;
    }

    public Page<SetFornecedor> list(Pageable pageable, Specification<SetFornecedor> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetFornecedor> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }
}
