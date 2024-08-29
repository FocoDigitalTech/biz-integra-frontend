package br.com.onetec.application.service.produtoservice;

import br.com.onetec.infra.db.model.SetPraga;
import br.com.onetec.infra.db.model.SetProduto;
import br.com.onetec.infra.db.model.SetTipoMidia;
import br.com.onetec.infra.db.repository.ISetPragaRepository;
import br.com.onetec.infra.db.repository.ISetProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProdutoService {

    private ISetProdutoRepository repository;

    @Autowired
    public void initServices (ISetProdutoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetProduto> list(Pageable pageable, Specification<SetProduto> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetProduto> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetProduto dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetProduto item) throws Exception {
        try {
            repository.delete(item);
        } catch (Exception e){
            throw new Exception();
        }
    }

    @Transactional
    public void update(SetProduto dto) throws Exception {
        try {
            Optional<SetProduto> produtoOptional = repository.findById(dto.getId_produto());
            SetProduto entity = produtoOptional.orElseThrow(() -> new Exception("Produto não encontrado"));
            // Copiando os valores do DTO para a entidade existente
            BeanUtils.copyProperties(dto, entity, "id_produto", "data_inclusao");
            repository.save(entity);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public SetProduto findById(Integer id_produto) {
        try {
            Optional<SetProduto> produtoOptional = repository.findById(id_produto);
            return produtoOptional.orElseThrow(() -> new Exception("Produto não encontrado"));
        }catch (Exception e){
            return null;
        }
    }


    public List<SetProduto> findAll() {
            return repository.listAll();
    }
}