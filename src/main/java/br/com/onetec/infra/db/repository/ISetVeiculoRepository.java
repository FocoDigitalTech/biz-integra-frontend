package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetTipoPagamento;
import br.com.onetec.infra.db.model.SetVeiculo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetVeiculoRepository extends CrudRepository<SetVeiculo, Integer>
        , JpaSpecificationExecutor<SetVeiculo> {

    @Query(value = "SELECT * FROM tb_veiculo where ativo = 'S'", nativeQuery = true)
    List<SetVeiculo> listAll();
}