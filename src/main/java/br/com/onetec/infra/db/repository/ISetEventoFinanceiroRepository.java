package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetEventoFinanceiro;
import br.com.onetec.infra.db.model.SetGrupoFinanceiro;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISetEventoFinanceiroRepository extends CrudRepository<SetEventoFinanceiro, Integer>
        , JpaSpecificationExecutor<SetEventoFinanceiro> {

    @Query(value = "SELECT * FROM tb_eventofinanceiro where ativo = 'S'", nativeQuery = true)
    List<SetEventoFinanceiro> listAll();
}
