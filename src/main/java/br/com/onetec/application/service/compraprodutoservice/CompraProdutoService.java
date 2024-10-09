package br.com.onetec.application.service.compraprodutoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetCompra;
import br.com.onetec.infra.db.model.SetCompraProduto;
import br.com.onetec.infra.db.repository.ISetCompraProdutoRepository;
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
public class CompraProdutoService {

    private ISetCompraProdutoRepository repository;


    @Autowired
    public void initServices(ISetCompraProdutoRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetCompraProduto> list(Pageable pageable, Specification<SetCompraProduto> filter) {
        log.info("Pageable: {}", pageable);
        Specification<SetCompraProduto> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        // Combina a nova condição com o filtro existente usando and()
        Specification<SetCompraProduto> filtroComCondicao = filter.and(novaCondicao);
        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public void save(SetCompraProduto dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetCompraProduto item) throws Exception {
        try {
            Optional<SetCompraProduto> optional = repository.findById(item.getId_compraproduto());
            SetCompraProduto entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("Cliente excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }

    public void update(SetCompraProduto item) throws Exception {
        try {
            Optional<SetCompraProduto> optional = repository.findById(item.getId_compraproduto());
            SetCompraProduto entity = optional.get();
            entity.setData_alteracao(LocalDateTime.now());
            repository.save(entity);
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<SetCompraProduto> listAll() {
        return repository.listAll();
    }
}
