package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetEnderecos;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEnderecosRepository extends CrudRepository<SetEnderecos, Integer>
        , JpaSpecificationExecutor<SetEnderecos> {

    @Query(value = "SELECT * FROM tb_enderecos where id_cliente = ?1", nativeQuery = true)
    List<SetEnderecos> findAllByCliente(Integer id_cliente);
}
