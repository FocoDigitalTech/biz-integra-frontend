package br.com.onetec.application.service.codigonumeracaoservice;

import br.com.onetec.application.security.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetCodigoNumeracao;
import br.com.onetec.infra.db.repository.ISetCodigoNumeracaoRepository;
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
public class CodigoNumeracaoService {

    private ISetCodigoNumeracaoRepository repository;


    @Autowired
    public void initServices(ISetCodigoNumeracaoRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetCodigoNumeracao> list(Pageable pageable, Specification<SetCodigoNumeracao> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetCodigoNumeracao> page = repository.findAll(filter, pageable);
        Specification<SetCodigoNumeracao> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");

        // Combina a nova condição com o filtro existente usando and()
        Specification<SetCodigoNumeracao> filtroComCondicao = filter.and(novaCondicao);

        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }


    public SetCodigoNumeracao save(SetCodigoNumeracao dto) throws Exception {
        try {

            repository.save(dto);
        } catch (Exception e) {
            throw new Exception();
        }
        return dto;
    }

    public void delete(SetCodigoNumeracao item) throws Exception {
        try {
            Optional<SetCodigoNumeracao> optional = repository.findById(item.getId_codigonumeracao().intValue());
            SetCodigoNumeracao entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }


    public List<SetCodigoNumeracao> findAll() {
        return repository.findAll();
    }


    public Integer verificaCodigoOrcamento() {
        return findAll().get(0).getOrcamento_codigonumeracao().intValue();
    }

    public void update(SetCodigoNumeracao codigoNumeracao) throws Exception {
        try {
            Optional<SetCodigoNumeracao> optional = repository.findById(codigoNumeracao.getId_codigonumeracao().intValue());
            SetCodigoNumeracao entity = optional.get();
            entity.setOrcamento_codigonumeracao(codigoNumeracao.getOrcamento_codigonumeracao());
            entity.setContrato_codigonumeracao(codigoNumeracao.getContrato_codigonumeracao());
            entity.setOrdemservico_codigonumeracao(codigoNumeracao.getOrdemservico_codigonumeracao());
            entity.setCliente_codigonumeracao(codigoNumeracao.getCliente_codigonumeracao());
            entity.setData_alteracao(codigoNumeracao.getData_alteracao());
            entity.setId_usuario(codigoNumeracao.getId_usuario());
            repository.save(entity);
            log.info("Atualizado !");
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
