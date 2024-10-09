package br.com.onetec.application.service.condicaopagamentoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.model.Departamento;
import br.com.onetec.infra.db.model.*;
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
        Specification<SetCondicaoPagamento> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetCondicaoPagamento> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
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
            Optional<SetCondicaoPagamento> optional = repository.findById(item.getId_condicaopagamento());
            SetCondicaoPagamento entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("Cliente excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetCondicaoPagamento> listAll() {
        return repository.listAll();
    }
}
