package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetCondicaoPagamento;
import br.com.onetec.infra.db.model.SetGrupoFinanceiro;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISetGrupoFinanceiroRepository extends CrudRepository<SetGrupoFinanceiro, Integer>
        , JpaSpecificationExecutor<SetGrupoFinanceiro> {

    @Query(value = "SELECT * FROM tb_grupoeventofinanceiro where ativo = 'S'", nativeQuery = true)
    List<SetGrupoFinanceiro> listAll();
}
