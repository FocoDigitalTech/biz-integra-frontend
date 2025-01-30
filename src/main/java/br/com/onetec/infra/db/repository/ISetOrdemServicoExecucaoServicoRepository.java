package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetOrdemServicoExecucaoServico;
import br.com.onetec.infra.db.model.SetOrdemServicoFuncionarioAlocado;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetOrdemServicoExecucaoServicoRepository extends CrudRepository<SetOrdemServicoExecucaoServico, Integer>
        , JpaSpecificationExecutor<SetOrdemServicoExecucaoServico> {

    @Query(value = "SELECT * FROM tb_ordemservicoexecucaoservico where ativo = 'S' and id_ordemservico = ?1", nativeQuery = true)
    List<SetOrdemServicoExecucaoServico> listAllByOrdemServicoId(Integer id_ordemservico);

}
