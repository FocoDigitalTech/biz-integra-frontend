package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetResponsavelCobranca;
import br.com.onetec.infra.db.model.SetTipoMidia;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITipoMidiaRepository extends CrudRepository<SetTipoMidia, Integer>
        , JpaSpecificationExecutor<SetTipoMidia> {

    @Query(value = "SELECT * FROM tb_tipomidia where ativo = 'S'", nativeQuery = true)
    List<SetTipoMidia> listAll();
}
