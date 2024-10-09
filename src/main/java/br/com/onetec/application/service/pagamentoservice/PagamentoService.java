package br.com.onetec.application.service.pagamentoservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetOrdemServico;
import br.com.onetec.infra.db.model.SetPagamento;
import br.com.onetec.infra.db.repository.ISetOrdemServicoRepository;
import br.com.onetec.infra.db.repository.ISetPagamentoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class PagamentoService {

    private ISetPagamentoRepository repository;

    @Autowired
    public void initServices (ISetPagamentoRepository repository1){
        this.repository = repository1;
    }

    public Page<SetPagamento> list(Pageable pageable, Specification<SetPagamento> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetPagamento> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public List<SetPagamento> findAllByOrcamentoId(Integer orcamentoid){

        return repository.listAllByOrcamentoId(orcamentoid);
    }

    public void save(SetPagamento dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void update(SetPagamento dto) throws Exception {
        try {
            Optional<SetPagamento> optional = repository.findById(dto.getId_pagamento());
            SetPagamento entity = optional.get();
            entity = dto;
            repository.save(entity);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetPagamento item) throws Exception {
        try {
            Optional<SetPagamento> optional = repository.findById(item.getId_pagamento());
            SetPagamento entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e){
            throw new Exception();
        }
    }
}
