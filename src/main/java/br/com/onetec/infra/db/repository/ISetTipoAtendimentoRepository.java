package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetTipoAtendimento;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISetTipoAtendimentoRepository extends CrudRepository<SetTipoAtendimento, Integer>
        , JpaSpecificationExecutor<SetTipoAtendimento> {

    @Query(value = "SELECT * FROM tb_tipoatendimento where ativo = 'S'", nativeQuery = true)
    List<SetTipoAtendimento> listAll();
}
