package br.com.onetec.application.service.estoqueservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetEstoque;
import br.com.onetec.infra.db.repository.ISetEstoqueRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class EstoqueService {

    private ISetEstoqueRepository repository;

    @Autowired
    public void initServices(ISetEstoqueRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetEstoque> list(Pageable pageable, Specification<SetEstoque> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetEstoque> page = repository.findAll(filter, pageable);
        Specification<SetEstoque> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetEstoque> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public void save(SetEstoque dto) throws Exception {
        try {
            repository.save(dto);

        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void delete(SetEstoque item) throws Exception {
        try {
            Optional<SetEstoque> optional = repository.findById(item.getId_estoque());
            SetEstoque entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @SneakyThrows
    public void update(SetEstoque p) {
        try {
            Optional<SetEstoque> estoqueOptional = repository.findById(p.getId_estoque());
            SetEstoque entity = estoqueOptional.orElseThrow(() -> new Exception("Estoque Id não encontrado"));
            repository.save(entity);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception();
        }
    }
}
