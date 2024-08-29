package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetProduto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISetProdutoRepository extends CrudRepository<SetProduto, Integer>
        , JpaSpecificationExecutor<SetProduto> {

    @Query(value = "SELECT * FROM tb_produto where ativo = 'S'", nativeQuery = true)
    List<SetProduto> listAll();
}
