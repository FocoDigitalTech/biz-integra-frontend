package br.com.onetec.application.service.condicaopagamentoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.model.Departamento;
import br.com.onetec.infra.db.model.SetCondicaoPagamento;
import br.com.onetec.infra.db.model.SetDepartamento;
import br.com.onetec.infra.db.model.SetEstoque;
import br.com.onetec.infra.db.model.SetFuncionario;
import br.com.onetec.infra.db.repository.IDepartamentoRepository;
import br.com.onetec.infra.db.repository.IFuncionarioRepository;
import br.com.onetec.infra.db.repository.ISetCondicaoPagamentoRepository;
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
public class CondicaoPagamentoService {

    private ISetCondicaoPagamentoRepository repository;


    @Autowired
    public void initServices(ISetCondicaoPagamentoRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetCondicaoPagamento> list(Pageable pageable, Specification<SetCondicaoPagamento> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetCondicaoPagamento> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetCondicaoPagamento dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetCondicaoPagamento item) throws Exception {
        try {
            repository.delete(item);
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetCondicaoPagamento> listAll() {
        return repository.listAll();
    }
}
