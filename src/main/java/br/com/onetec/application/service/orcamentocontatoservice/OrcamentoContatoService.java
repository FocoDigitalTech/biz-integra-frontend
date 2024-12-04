package br.com.onetec.application.service.orcamentocontatoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetNotaFiscal;
import br.com.onetec.infra.db.model.SetOrcamentoContato;
import br.com.onetec.infra.db.repository.ISetNotaFiscalRepository;
import br.com.onetec.infra.db.repository.ISetOrcamentoContatoRepository;
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
public class OrcamentoContatoService {

    private ISetOrcamentoContatoRepository repository;

    @Autowired
    public void initServices (ISetOrcamentoContatoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetOrcamentoContato> list(Pageable pageable, Specification<SetOrcamentoContato> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetOrcamentoContato> page = repository.findAll(filter, pageable);
        Specification<SetOrcamentoContato> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetOrcamentoContato> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public void save(SetOrcamentoContato dto) throws Exception {
        try {
            dto.setData_inclusao(LocalDateTime.now());
            dto.setAtivo("S");
            dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetOrcamentoContato item) throws Exception {
        try {
            Optional<SetOrcamentoContato> optional = repository.findById(item.getId_orcamentocontato());
            SetOrcamentoContato entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public void update(SetOrcamentoContato item) throws Exception {
        try {
            Optional<SetOrcamentoContato> optional = repository.findById(item.getId_orcamentocontato());
            SetOrcamentoContato entity = optional.get();
            entity.setData_alteracao(LocalDateTime.now());
            repository.save(entity);
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetOrcamentoContato> findAllByOrcamentoId(Integer id_orcamento) {
        return repository.listAllByOrcamentoId(id_orcamento);
    }

    public List<SetOrcamentoContato> listAll() {
        return repository.listAll();
    }

}
