package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetTipoEventoFinanceiro;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISetTipoEventoFinanceiroRepository extends CrudRepository<SetTipoEventoFinanceiro, Integer>
        , JpaSpecificationExecutor<SetTipoEventoFinanceiro> {

    @Query(value = "SELECT * FROM tb_tipoeventofinanceiro where ativo = 'S'", nativeQuery = true)
    List<SetTipoEventoFinanceiro> listAll();
}
