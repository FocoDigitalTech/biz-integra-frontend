package br.com.onetec.application.service.fornecedorservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetFornecedor;
import br.com.onetec.infra.db.repository.ISetFornecedorRepository;
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
public class FornecedorService {

    private final ISetFornecedorRepository repository;

    @Autowired
    public FornecedorService(ISetFornecedorRepository repository) {
        this.repository = repository;
    }

    public Page<SetFornecedor> list(Pageable pageable, Specification<SetFornecedor> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetFornecedor> page = repository.findAll(filter, pageable);
        Specification<SetFornecedor> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetFornecedor> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public List<SetFornecedor> findAll() {
        return repository.listAll();
    }

    public void delete(SetFornecedor item) throws Exception {
        try {
            Optional<SetFornecedor> optional = repository.findById(item.getId_fornecedor());
            SetFornecedor entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void save(SetFornecedor dto) throws Exception {
        try {
            repository.save(dto);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public SetFornecedor findById(Integer id_usuario) {
        Optional<SetFornecedor> optionalSetFornecedor = repository.findById(id_usuario);
        return optionalSetFornecedor.get();
    }

    public void update(SetFornecedor fornecedor) throws Exception {
        try {
            Optional<SetFornecedor> optional = repository.findById(fornecedor.getId_fornecedor());
            SetFornecedor entity = optional.get();
            entity = fornecedor;
            entity.setData_alteracao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("Alterado !");
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
