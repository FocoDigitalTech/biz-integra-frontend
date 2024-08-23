package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetCliente;
import br.com.onetec.infra.db.model.SetRegiao;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRegiaoRepository extends CrudRepository<SetRegiao, Integer>
        , JpaSpecificationExecutor<SetRegiao> {

    @Query(value = "SELECT * FROM tb_regiao where ativo = 'S'", nativeQuery = true)
    List<SetRegiao> listAll();
}
