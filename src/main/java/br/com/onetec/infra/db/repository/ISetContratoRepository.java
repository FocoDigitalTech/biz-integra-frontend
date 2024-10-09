package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetContrato;
import br.com.onetec.infra.db.model.SetServicosOrcamento;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetContratoRepository extends CrudRepository<SetContrato, Integer>
        , JpaSpecificationExecutor<SetContrato> {

    @Query(value = "SELECT * FROM tb_contrato where id_contrato = ?1 and ativo = 'S'", nativeQuery = true)
    List<SetContrato> findByContratoId(Integer id_contrato);
}
