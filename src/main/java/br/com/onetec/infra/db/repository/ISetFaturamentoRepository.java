package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetEventoFinanceiro;
import br.com.onetec.infra.db.model.SetFaturamento;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetFaturamentoRepository extends CrudRepository<SetFaturamento, Integer>
        , JpaSpecificationExecutor<SetFaturamento> {

    @Query(value = "SELECT * FROM tb_faturamento where ativo = 'S'", nativeQuery = true)
    List<SetFaturamento> listAll();
}