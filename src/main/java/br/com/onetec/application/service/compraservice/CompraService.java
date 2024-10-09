package br.com.onetec.application.service.compraservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetComissoes;
import br.com.onetec.infra.db.model.SetCompra;
import br.com.onetec.infra.db.repository.ISetComissoesRepository;
import br.com.onetec.infra.db.repository.ISetCompraRepository;
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
public class CompraService {

    private ISetCompraRepository repository;


    @Autowired
    public void initServices(ISetCompraRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetCompra> list(Pageable pageable, Specification<SetCompra> filter) {
        log.info("Pageable: {}", pageable);
        Specification<SetCompra> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetCompra> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public void save(SetCompra dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetCompra item) throws Exception {
        try {
            Optional<SetCompra> optional = repository.findById(item.getId_compra());
            SetCompra entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("Cliente excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public void update(SetCompra item) throws Exception {
        try {
            Optional<SetCompra> optional = repository.findById(item.getId_compra());
            SetCompra entity = optional.get();
            entity.setData_alteracao(LocalDateTime.now());
            repository.save(entity);
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetCompra> listAll() {
        return repository.listAll();
    }
}
