package br.com.onetec.application.service.execucaoservico;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetEventoFinanceiro;
import br.com.onetec.infra.db.model.SetExecucaoServico;
import br.com.onetec.infra.db.repository.ISetEventoFinanceiroRepository;
import br.com.onetec.infra.db.repository.ISetExecucaoServicoRepository;
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
public class ExecucaoServicoService {

    private ISetExecucaoServicoRepository repository;

    @Autowired
    public void initServices (ISetExecucaoServicoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetExecucaoServico> list(Pageable pageable, Specification<SetExecucaoServico> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetExecucaoServico> page = repository.findAll(filter, pageable);
        Specification<SetExecucaoServico> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetExecucaoServico> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public void save(SetExecucaoServico dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetExecucaoServico item) throws Exception {
        try {
            Optional<SetExecucaoServico> optional = repository.findById(item.getId_execucaoservico());
            SetExecucaoServico entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetExecucaoServico> findAll() {
        return repository.listAll();
    }

    public SetExecucaoServico findById(Integer id_execucaoservico) {
        Optional<SetExecucaoServico> optional = repository.findById(id_execucaoservico);
        return optional.orElse(null);
    }
}
