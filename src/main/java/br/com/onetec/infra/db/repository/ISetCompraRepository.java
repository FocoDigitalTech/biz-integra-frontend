package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetCompra;
import br.com.onetec.infra.db.model.SetCondicaoPagamento;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetCompraRepository extends CrudRepository<SetCompra, Integer>
        , JpaSpecificationExecutor<SetCompra> {

    @Query(value = "SELECT * FROM tb_compra where ativo = 'S'", nativeQuery = true)
    List<SetCompra> listAll();
}
