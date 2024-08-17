package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetEstado;
import br.com.onetec.infra.db.model.SetResponsavelAgendamento;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IResponsavelAgendamentoRepository extends CrudRepository<SetResponsavelAgendamento, Integer>
        , JpaSpecificationExecutor<SetResponsavelAgendamento> {

    List<SetResponsavelAgendamento> findAll();

    @Query(value = "SELECT * FROM tb_responsavelagendamento where id_cliente = ?1", nativeQuery = true)
    SetResponsavelAgendamento findByCliente (Integer id_cliente);

}
