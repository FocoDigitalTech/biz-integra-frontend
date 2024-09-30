package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetPermissao;
import br.com.onetec.infra.db.model.SetPraga;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISetPermissaoRepository extends CrudRepository<SetPermissao, Integer>
        , JpaSpecificationExecutor<SetPermissao> {

    @Query(value = "SELECT * FROM tb_permissao where id_grupousuario = ?1 ", nativeQuery = true)
    List<SetPermissao> listAllById(Integer id_grupousuario);
}
