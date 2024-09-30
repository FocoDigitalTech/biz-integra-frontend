package br.com.onetec.infra.db.repository;


import br.com.onetec.infra.db.model.SetResponsavelAprovacao;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IResponsavelAprovacaoRepository extends CrudRepository<SetResponsavelAprovacao, Integer>
        , JpaSpecificationExecutor<SetResponsavelAprovacao> {

    @Query(value = "SELECT * FROM tb_responsavelaprovacao where id_cliente = ?1", nativeQuery = true)
    SetResponsavelAprovacao findByCliente(Integer id_cliente);
}