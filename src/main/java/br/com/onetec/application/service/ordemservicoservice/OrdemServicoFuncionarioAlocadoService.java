package br.com.onetec.application.service.ordemservicoservice;

import br.com.onetec.application.security.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetOrdemServicoFuncionarioAlocado;
import br.com.onetec.infra.db.repository.ISetOrdemServicoFuncionarioAlocadoRepository;
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
public class OrdemServicoFuncionarioAlocadoService {

    private ISetOrdemServicoFuncionarioAlocadoRepository repository;

    @Autowired
    public void initServices(ISetOrdemServicoFuncionarioAlocadoRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetOrdemServicoFuncionarioAlocado> list(Pageable pageable, Specification<SetOrdemServicoFuncionarioAlocado> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetOrdemServicoFuncionarioAlocado> page = repository.findAll(filter, pageable);
        Specification<SetOrdemServicoFuncionarioAlocado> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetOrdemServicoFuncionarioAlocado> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public List<SetOrdemServicoFuncionarioAlocado> listAllByOrdemServicoId(Integer id) {

        return repository.listAllByOrdemServicoId(id);
    }

    public void save(SetOrdemServicoFuncionarioAlocado dto) throws Exception {
        try {
            repository.save(dto);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void delete(SetOrdemServicoFuncionarioAlocado item) throws Exception {
        try {
            Optional<SetOrdemServicoFuncionarioAlocado> optional = repository.findById(item.getId_ordemservico());
            SetOrdemServicoFuncionarioAlocado entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public SetOrdemServicoFuncionarioAlocado findById(Integer value) {
        Optional<SetOrdemServicoFuncionarioAlocado> optional = repository.findById(value);
        return optional.orElse(null);
    }

    public void update(SetOrdemServicoFuncionarioAlocado p) throws Exception {
        try {
            Optional<SetOrdemServicoFuncionarioAlocado> optional = repository.findById(p.getId_ordemservico());
            SetOrdemServicoFuncionarioAlocado entity = optional.get();
            entity = p;
            entity.setData_alteracao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
