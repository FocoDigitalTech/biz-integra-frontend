package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetCompraProduto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ISetCompraProdutoRepository extends CrudRepository<SetCompraProduto, Integer>
        , JpaSpecificationExecutor<SetCompraProduto> {

    @Query(value = "SELECT * FROM tb_compra where ativo = 'S'", nativeQuery = true)
    List<SetCompraProduto> listAll();

    @Query(value = "SELECT * FROM tb_compraproduto where id_compra = ?1 and ativo = 'S'", nativeQuery = true)
    List<SetCompraProduto> findByIdCompra(Integer id_compra);
}
