package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetOrcamento;
import br.com.onetec.infra.db.model.SetOrdemServicoFuncionarioAlocado;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetOrdemServicoFuncionarioAlocadoRepository  extends CrudRepository<SetOrdemServicoFuncionarioAlocado, Integer>
        , JpaSpecificationExecutor<SetOrdemServicoFuncionarioAlocado> {

    @Query(value = "SELECT * FROM tb_ordemservicofuncionarioalocado where ativo = 'S' and id_ordemservico = ?1", nativeQuery = true)
    List<SetOrdemServicoFuncionarioAlocado> listAllByOrdemServicoId(Integer id_ordemservico);
}
