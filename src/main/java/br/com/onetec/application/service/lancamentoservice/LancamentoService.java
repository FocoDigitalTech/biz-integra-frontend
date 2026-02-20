package br.com.onetec.application.service.lancamentoservice;


import br.com.onetec.application.security.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetFluxoRecebimentoPagamento;
import br.com.onetec.infra.db.repository.ISetFuxoRecebimentoPagamentoRepository;
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
public class LancamentoService {

    private ISetFuxoRecebimentoPagamentoRepository repository;

    @Autowired
    public void initServices(ISetFuxoRecebimentoPagamentoRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetFluxoRecebimentoPagamento> list(Pageable pageable, Specification<SetFluxoRecebimentoPagamento> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetFluxoRecebimentoPagamento> page = repository.findAll(filter, pageable);
        Specification<SetFluxoRecebimentoPagamento> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetFluxoRecebimentoPagamento> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public void save(SetFluxoRecebimentoPagamento dto) throws Exception {
        try {
            repository.save(dto);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void delete(SetFluxoRecebimentoPagamento item) throws Exception {
        try {
            Optional<SetFluxoRecebimentoPagamento> optional = repository.findById(item.getId_fluxorecebimentopagamento());
            SetFluxoRecebimentoPagamento entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void update(SetFluxoRecebimentoPagamento dto) throws Exception {
        try {
            Optional<SetFluxoRecebimentoPagamento> optional = repository.findById(dto.getId_fluxorecebimentopagamento());
            SetFluxoRecebimentoPagamento entity = optional.get();
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
