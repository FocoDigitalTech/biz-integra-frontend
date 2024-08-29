package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetProduto;
import br.com.onetec.infra.db.model.SetTecnicoAssistente;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISetTecnicoAssistenteRepository extends CrudRepository<SetTecnicoAssistente, Integer>
        , JpaSpecificationExecutor<SetTecnicoAssistente> {

    @Query(value = "SELECT * FROM tb_tecnicoassistente where ativo = 'S'", nativeQuery = true)
    List<SetTecnicoAssistente> listAll();
}
