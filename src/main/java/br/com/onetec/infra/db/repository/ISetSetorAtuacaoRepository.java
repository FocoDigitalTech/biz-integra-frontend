package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetServico;
import br.com.onetec.infra.db.model.SetSetorAtuacao;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISetSetorAtuacaoRepository extends CrudRepository<SetSetorAtuacao, Integer>
        , JpaSpecificationExecutor<SetSetorAtuacao> {

    @Query(value = "SELECT * FROM tb_setoratuacao where ativo = 'S'", nativeQuery = true)
    List<SetSetorAtuacao> listAll();
}
