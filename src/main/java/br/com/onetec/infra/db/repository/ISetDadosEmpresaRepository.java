package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetDadosEmpresa;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ISetDadosEmpresaRepository extends CrudRepository<SetDadosEmpresa, Integer>
        , JpaSpecificationExecutor<SetDadosEmpresa> {

    @Query(value = "SELECT * FROM tb_dadosempresa where ativo = 'S' limit 1", nativeQuery = true)
    SetDadosEmpresa findActive();
}