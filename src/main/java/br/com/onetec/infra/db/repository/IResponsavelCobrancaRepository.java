package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetResponsavelCobranca;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IResponsavelCobrancaRepository extends CrudRepository<SetResponsavelCobranca, Integer>
        , JpaSpecificationExecutor<SetResponsavelCobranca> {

    @Query(value = "SELECT * FROM tb_responsavelcobranca where id_cliente = ?1", nativeQuery = true)
    SetResponsavelCobranca findByCliente(Integer id_cliente);
}
