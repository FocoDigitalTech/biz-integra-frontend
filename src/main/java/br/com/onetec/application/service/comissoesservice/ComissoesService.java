package br.com.onetec.application.service.comissoesservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetCliente;
import br.com.onetec.infra.db.model.SetComissoes;
import br.com.onetec.infra.db.model.SetCondicaoPagamento;
import br.com.onetec.infra.db.model.SetContrato;
import br.com.onetec.infra.db.repository.ISetComissoesRepository;
import br.com.onetec.infra.db.repository.ISetCondicaoPagamentoRepository;
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
public class ComissoesService {

    private ISetComissoesRepository repository;


    @Autowired
    public void initServices(ISetComissoesRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetComissoes> list(Pageable pageable, Specification<SetComissoes> filter) {
        log.info("Pageable: {}", pageable);
        Specification<SetComissoes> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetComissoes> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public void save(SetComissoes dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetComissoes item) throws Exception {
        try {
            Optional<SetComissoes> optional = repository.findById(item.getId_comissoes());
            SetComissoes entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("Cliente excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public void update(SetComissoes item) throws Exception {
        try {
            Optional<SetComissoes> optional = repository.findById(item.getId_comissoes());
            SetComissoes entity = optional.get();
            entity.setData_alteracao(LocalDateTime.now());
            repository.save(entity);
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetComissoes> listAll(Integer id_orcamento) {
        return repository.findByOrcamento(id_orcamento);
    }
}
