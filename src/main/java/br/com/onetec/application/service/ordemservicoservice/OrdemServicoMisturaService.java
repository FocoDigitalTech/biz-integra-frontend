package br.com.onetec.application.service.ordemservicoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetOrdemServicoMisturas;
import br.com.onetec.infra.db.repository.ISetOrdemServicoMisturasRepository;
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
public class OrdemServicoMisturaService {

    private ISetOrdemServicoMisturasRepository repository;

    @Autowired
    public void initServices(ISetOrdemServicoMisturasRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetOrdemServicoMisturas> list(Pageable pageable, Specification<SetOrdemServicoMisturas> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetOrdemServicoMisturas> page = repository.findAll(filter, pageable);
        Specification<SetOrdemServicoMisturas> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetOrdemServicoMisturas> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public List<SetOrdemServicoMisturas> listAllByOrdemServicoId(Integer id) {

        return repository.listAllByOrdemServicoId(id);
    }

    public void save(SetOrdemServicoMisturas dto) throws Exception {
        try {
            repository.save(dto);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void delete(SetOrdemServicoMisturas item) throws Exception {
        try {
            Optional<SetOrdemServicoMisturas> optional = repository.findById(item.getId_ordemservico());
            SetOrdemServicoMisturas entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public SetOrdemServicoMisturas findById(Integer value) {
        Optional<SetOrdemServicoMisturas> optional = repository.findById(value);
        return optional.orElse(null);
    }

    public void update(SetOrdemServicoMisturas p) throws Exception {
        try {
            Optional<SetOrdemServicoMisturas> optional = repository.findById(p.getId_ordemservicomisturas());
            SetOrdemServicoMisturas entity = optional.get();
            entity = p;
            entity.setData_alteracao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("Alterado !");
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
