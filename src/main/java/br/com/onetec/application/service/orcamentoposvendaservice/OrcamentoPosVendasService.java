package br.com.onetec.application.service.orcamentoposvendaservice;


import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetOrcamentoPosVenda;
import br.com.onetec.infra.db.repository.ISetOrcamentoPosVendaRepository;
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
public class OrcamentoPosVendasService {

    private ISetOrcamentoPosVendaRepository repository;

    @Autowired
    public void initServices(ISetOrcamentoPosVendaRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetOrcamentoPosVenda> list(Pageable pageable, Specification<SetOrcamentoPosVenda> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetOrcamentoPosVenda> page = repository.findAll(filter, pageable);
        Specification<SetOrcamentoPosVenda> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetOrcamentoPosVenda> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public void save(SetOrcamentoPosVenda dto) throws Exception {
        try {
            dto.setData_inclusao(LocalDateTime.now());
            dto.setAtivo("S");
            dto.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(dto);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void delete(SetOrcamentoPosVenda item) throws Exception {
        try {
            Optional<SetOrcamentoPosVenda> optional = repository.findById(item.getId_orcamentoposvenda());
            SetOrcamentoPosVenda entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void update(SetOrcamentoPosVenda item) throws Exception {
        try {
            Optional<SetOrcamentoPosVenda> optional = repository.findById(item.getId_orcamentoposvenda());
            SetOrcamentoPosVenda entity = optional.get();
            entity = item;
            entity.setData_alteracao(LocalDateTime.now());
            repository.save(entity);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public List<SetOrcamentoPosVenda> findAllByOrcamentoId(Integer id_orcamento) {
        return repository.listAllByOrcamentoId(id_orcamento);
    }

    public List<SetOrcamentoPosVenda> listAll() {
        return repository.listAll();
    }
}
