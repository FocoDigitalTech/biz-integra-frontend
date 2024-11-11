package br.com.onetec.application.service.arquivoorcamentoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetArquivoOrcamento;
import br.com.onetec.infra.db.repository.ISetArquivoOrcamentoRepository;
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
public class ArquivoOrcamentoService {

    private ISetArquivoOrcamentoRepository repository;


    @Autowired
    public void initServices (ISetArquivoOrcamentoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetArquivoOrcamento> list(Pageable pageable, Specification<SetArquivoOrcamento> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetArquivoOrcamento> page = repository.findAll(filter, pageable);
        Specification<SetArquivoOrcamento> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");

        // Combina a nova condição com o filtro existente usando and()
        Specification<SetArquivoOrcamento> filtroComCondicao = filter.and(novaCondicao);

        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }


    public SetArquivoOrcamento save(SetArquivoOrcamento dto) throws Exception {
        try {

            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
        return dto;
    }

    public void delete(SetArquivoOrcamento item) throws Exception {
        try {
            Optional<SetArquivoOrcamento> optional = repository.findById(item.getId_arquivosorcamento());
            SetArquivoOrcamento entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetArquivoOrcamento> findAllByOrcamentoId(Integer id_orcamento) {
        return repository.listAllByOrcamentoId(id_orcamento);
    }

}
