package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetOrcamento;
import br.com.onetec.infra.db.model.SetOrdemServicoFuncionarioAlocado;
import br.com.onetec.infra.db.model.SetOrdemServicoMateriais;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetOrdemServicoMateriaisRepository extends CrudRepository<SetOrdemServicoMateriais, Integer>
        , JpaSpecificationExecutor<SetOrdemServicoMateriais> {

    @Query(value = "SELECT * FROM tb_ordemservicomateriais where ativo = 'S' and id_ordemservico = ?1", nativeQuery = true)
    List<SetOrdemServicoMateriais> listAllByOrdemServicoId(Integer id_ordemservicomateriais);
}