package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetServico;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISetServicoRepository extends CrudRepository<SetServico, Integer>
        , JpaSpecificationExecutor<SetServico> {

    @Query(value = "SELECT * FROM tb_servico where ativo = 'S'", nativeQuery = true)
    List<SetServico> listAll();
}
