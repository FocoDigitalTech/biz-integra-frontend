package br.com.onetec.application.service.grupofinanceiroservice;

import br.com.onetec.application.security.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetGrupoFinanceiro;
import br.com.onetec.infra.db.repository.ISetGrupoFinanceiroRepository;
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
public class GrupoFinanceiroService {

    private ISetGrupoFinanceiroRepository repository;

    @Autowired
    public void initServices(ISetGrupoFinanceiroRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetGrupoFinanceiro> list(Pageable pageable, Specification<SetGrupoFinanceiro> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetGrupoFinanceiro> page = repository.findAll(filter, pageable);
        Specification<SetGrupoFinanceiro> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetGrupoFinanceiro> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public void save(SetGrupoFinanceiro dto) throws Exception {
        try {
            repository.save(dto);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void delete(SetGrupoFinanceiro item) throws Exception {
        try {
            Optional<SetGrupoFinanceiro> optional = repository.findById(item.getId_grupoeventofinanceiro());
            SetGrupoFinanceiro entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public List<SetGrupoFinanceiro> findAll() {
        return repository.listAll();
    }

    public void update(SetGrupoFinanceiro dto) throws Exception {
        try {
            Optional<SetGrupoFinanceiro> optional = repository.findById(dto.getId_grupoeventofinanceiro());
            SetGrupoFinanceiro entity = optional.get();
            entity = dto;
            entity.setData_alteracao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("Atualizado !");
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
