package br.com.onetec.application.service.ordemservicoexecucaoservicoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetOrdemServicoExecucaoServico;
import br.com.onetec.infra.db.repository.ISetOrdemServicoExecucaoServicoRepository;
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
public class OrdemServicoExecucaoServicoService {

    private ISetOrdemServicoExecucaoServicoRepository repository;

    @Autowired
    public void initServices(ISetOrdemServicoExecucaoServicoRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetOrdemServicoExecucaoServico> list(Pageable pageable, Specification<SetOrdemServicoExecucaoServico> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetOrdemServicoExecucaoServico> page = repository.findAll(filter, pageable);
        Specification<SetOrdemServicoExecucaoServico> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetOrdemServicoExecucaoServico> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public List<SetOrdemServicoExecucaoServico> listAllByOrdemServicoId(Integer id) {
        return repository.listAllByOrdemServicoId(id);
    }

    public void save(SetOrdemServicoExecucaoServico dto) throws Exception {
        try {
            repository.save(dto);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void delete(SetOrdemServicoExecucaoServico item) throws Exception {
        try {
            Optional<SetOrdemServicoExecucaoServico> optional = repository.findById(item.getId_ordemservicoexecucaoservico());
            SetOrdemServicoExecucaoServico entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public SetOrdemServicoExecucaoServico findById(Integer value) {
        Optional<SetOrdemServicoExecucaoServico> optional = repository.findById(value);
        return optional.orElse(null);
    }

    public void update(SetOrdemServicoExecucaoServico p) throws Exception {
        try {
            Optional<SetOrdemServicoExecucaoServico> optional = repository.findById(p.getId_ordemservicoexecucaoservico());
            SetOrdemServicoExecucaoServico entity = optional.get();
            entity = p;
            entity.setData_alteracao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("Atualizado !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

}
