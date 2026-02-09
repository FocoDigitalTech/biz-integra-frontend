package br.com.onetec.application.service.faturamentoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetFaturamento;
import br.com.onetec.infra.db.repository.ISetFaturamentoRepository;
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
public class FaturamentoService {

    private ISetFaturamentoRepository repository;

    @Autowired
    public void initServices(ISetFaturamentoRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetFaturamento> list(Pageable pageable, Specification<SetFaturamento> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetFaturamento> page = repository.findAll(filter, pageable);
        Specification<SetFaturamento> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetFaturamento> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public void save(SetFaturamento dto) throws Exception {
        try {
            repository.save(dto);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void delete(SetFaturamento item) throws Exception {
        try {
            Optional<SetFaturamento> optional = repository.findById(item.getId_faturamento());
            SetFaturamento entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void update(SetFaturamento item) throws Exception {
        try {
            Optional<SetFaturamento> optional = repository.findById(item.getId_faturamento());
            SetFaturamento entity = optional.get();
            entity.setData_alteracao(LocalDateTime.now());
            repository.save(entity);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public SetFaturamento findByIdOrcamento(Integer id_orcamento) {
        Optional<SetFaturamento> optional = repository.findByIdOrcamento(id_orcamento);
        return optional.orElse(null);
    }
}
