package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetCodigoNumeracao;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetCodigoNumeracaoRepository extends CrudRepository<SetCodigoNumeracao, Integer>
        , JpaSpecificationExecutor<SetCodigoNumeracao> {

    @Query(value = "SELECT * FROM tb_codigonumeracao where ativo = 'S'", nativeQuery = true)
    List<SetCodigoNumeracao> findAll();
}
