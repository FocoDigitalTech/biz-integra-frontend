package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetEventoFinanceiro;
import br.com.onetec.infra.db.model.SetExecucaoServico;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetExecucaoServicoRepository extends CrudRepository<SetExecucaoServico, Integer>
        , JpaSpecificationExecutor<SetExecucaoServico> {

    @Query(value = "SELECT * FROM tb_execucaoservico where ativo = 'S'", nativeQuery = true)
    List<SetExecucaoServico> listAll();
}
