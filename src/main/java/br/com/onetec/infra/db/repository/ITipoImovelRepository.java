package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetResponsavelCobranca;
import br.com.onetec.infra.db.model.SetTipoImovel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITipoImovelRepository extends CrudRepository<SetTipoImovel, Integer>
        , JpaSpecificationExecutor<SetTipoImovel> {

    @Query(value = "SELECT * FROM tb_tipoimovel where ativo = 'S'", nativeQuery = true)
    List<SetTipoImovel> listAll();
}
