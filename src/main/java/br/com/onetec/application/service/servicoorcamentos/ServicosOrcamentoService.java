package br.com.onetec.application.service.servicoorcamentos;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetServicosOrcamento;
import br.com.onetec.infra.db.repository.IServicosOrcamentoRepository;
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
public class ServicosOrcamentoService {


    private IServicosOrcamentoRepository repository;

    @Autowired
    public void initServices (IServicosOrcamentoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetServicosOrcamento> list(Pageable pageable, Specification<SetServicosOrcamento> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetServicosOrcamento> page = repository.findAll(filter, pageable);
        Specification<SetServicosOrcamento> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetServicosOrcamento> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public void save(SetServicosOrcamento dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetServicosOrcamento item) throws Exception {
        try {
            Optional<SetServicosOrcamento> optional = repository.findById(item.getId_servicosorcamento());
            SetServicosOrcamento entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetServicosOrcamento> listByOrcamento(Integer id_orcamento) {
        return repository.findByOrcamento(id_orcamento);
    }
}
