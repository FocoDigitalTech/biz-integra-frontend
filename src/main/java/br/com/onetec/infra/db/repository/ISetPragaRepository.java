package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetPraga;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISetPragaRepository extends CrudRepository<SetPraga, Integer>
        , JpaSpecificationExecutor<SetPraga> {

    @Query(value = "SELECT * FROM tb_praga where ativo = 'S'", nativeQuery = true)
    List<SetPraga> listAll();
}
