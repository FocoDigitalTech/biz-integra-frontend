package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetCondicaoPagamento;
import br.com.onetec.infra.db.model.SetContaCorrente;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISetContaCorrenteRepository extends CrudRepository<SetContaCorrente, Integer>
        , JpaSpecificationExecutor<SetContaCorrente> {

    @Query(value = "SELECT * FROM tb_contacorrente where ativo = 'S'", nativeQuery = true)
    List<SetContaCorrente> listAll();
}
