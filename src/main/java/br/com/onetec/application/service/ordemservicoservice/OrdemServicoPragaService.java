package br.com.onetec.application.service.ordemservicoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetOrdemServico;
import br.com.onetec.infra.db.model.SetOrdemServicoPraga;
import br.com.onetec.infra.db.repository.ISetOrdemServicoPragaRepository;
import br.com.onetec.infra.db.repository.ISetOrdemServicoRepository;
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
public class OrdemServicoPragaService {

    private ISetOrdemServicoPragaRepository repository;

    @Autowired
    public void initServices (ISetOrdemServicoPragaRepository repository1){
        this.repository = repository1;
    }

    public Page<SetOrdemServicoPraga> list(Pageable pageable, Specification<SetOrdemServicoPraga> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetOrdemServicoPraga> page = repository.findAll(filter, pageable);
        Specification<SetOrdemServicoPraga> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetOrdemServicoPraga> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public List<SetOrdemServicoPraga> listAllByOrdemServicoId(Integer id){

        return repository.listAllByOrdemServicoId(id);
    }

    public void save(SetOrdemServicoPraga dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetOrdemServicoPraga item) throws Exception {
        try {
            Optional<SetOrdemServicoPraga> optional = repository.findById(item.getId_ordemservico());
            SetOrdemServicoPraga entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public SetOrdemServicoPraga findById(Integer value) {
        Optional<SetOrdemServicoPraga> optional = repository.findById(value);
        return optional.orElse(null);
    }

    public void update(SetOrdemServicoPraga p) throws Exception {
        try {
            Optional<SetOrdemServicoPraga> optional = repository.findById(p.getId_ordemservico());
            SetOrdemServicoPraga entity = optional.get();
            entity = p;
            entity.setData_alteracao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }
}
