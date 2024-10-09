package br.com.onetec.infra.db.repository;

import br.com.onetec.infra.db.model.SetOrdemServico;
import br.com.onetec.infra.db.model.SetPagamento;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISetPagamentoRepository extends CrudRepository<SetPagamento, Integer>
        , JpaSpecificationExecutor<SetPagamento> {

    @Query(value = "SELECT * FROM tb_pagamento where id_orcamento = ?1 and ativo = 'S'", nativeQuery = true)
    List<SetPagamento> listAllByOrcamentoId(Integer id_orcamento);
}
