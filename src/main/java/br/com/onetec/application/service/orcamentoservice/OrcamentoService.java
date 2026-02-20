package br.com.onetec.application.service.orcamentoservice;

import br.com.onetec.application.security.UsuarioAutenticadoConfig;
import br.com.onetec.application.service.comissoesservice.ComissoesService;
import br.com.onetec.application.service.contratoservice.ContratoService;
import br.com.onetec.application.service.notafiscalservice.NotaFiscalService;
import br.com.onetec.application.service.orcamentocontatoservice.OrcamentoContatoService;
import br.com.onetec.application.service.orcamentoposvendaservice.OrcamentoPosVendasService;
import br.com.onetec.application.service.ordemservicoservice.OrdemServicoService;
import br.com.onetec.application.service.pagamentoservice.PagamentoService;
import br.com.onetec.infra.db.model.SetCliente;
import br.com.onetec.infra.db.model.SetOrcamento;
import br.com.onetec.infra.db.repository.ISetOrcamentoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class OrcamentoService {

    private ISetOrcamentoRepository repository;
    private OrdemServicoService ordemServicoService;
    private OrcamentoPosVendasService orcamentoPosVendasService;
    private OrcamentoContatoService orcamentoContatoService;
    private NotaFiscalService notaFiscalService;
    private PagamentoService pagamentoService;
    private ComissoesService comissoesService;
    private ContratoService contratoService;

    public OrcamentoService(OrdemServicoService ordemServicoService, OrcamentoPosVendasService orcamentoPosVendasService,
                            OrcamentoContatoService orcamentoContatoService, NotaFiscalService notaFiscalService,
                            PagamentoService pagamentoService, ComissoesService comissoesService,
                            ContratoService contratoService) {
        this.ordemServicoService = ordemServicoService;
        this.orcamentoPosVendasService = orcamentoPosVendasService;
        this.orcamentoContatoService = orcamentoContatoService;
        this.notaFiscalService = notaFiscalService;
        this.pagamentoService = pagamentoService;
        this.comissoesService = comissoesService;
        this.contratoService = contratoService;
    }


    @Autowired
    public void initServices(ISetOrcamentoRepository repository1) {
        this.repository = repository1;
    }

    public Page<SetOrcamento> list(Pageable pageable, Specification<SetOrcamento> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetOrcamento> page = repository.findAll(filter, pageable);
        Specification<SetOrcamento> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");

        // Combina a nova condição com o filtro existente usando and()
        Specification<SetOrcamento> filtroComCondicao = filter.and(novaCondicao);

        // Executa a consulta com o filtro combinado
        return repository.findAll(filtroComCondicao, pageable);
    }

    public Page<SetOrcamento> listByCustomer(Pageable pageable, Specification<SetOrcamento> filter, SetCliente entidade) {
        log.info("Pageable: {}", pageable);
        Page<SetOrcamento> page = repository.findAll(filter, pageable);
        Specification<SetOrcamento> novaCondicao = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id_cliente"), entidade.getId_cliente());
        Specification<SetOrcamento> filtroComCondicao = filter.and(novaCondicao);
        Specification<SetOrcamento> novaCondicaoAtivo = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), "S");
        Specification<SetOrcamento> filtroComCondicaoAtivo = filtroComCondicao.and(novaCondicaoAtivo);
        return repository.findAll(filtroComCondicaoAtivo, pageable);
    }

    public SetOrcamento save(SetOrcamento dto) throws Exception {
        try {
            repository.save(dto);
        } catch (Exception e) {
            throw new Exception();
        }
        return dto;
    }

    public void delete(SetOrcamento item) throws Exception {
        try {
            Optional<SetOrcamento> optional = repository.findById(item.getId_orcamento());
            SetOrcamento entity = optional.get();
            entity.setAtivo("N");
            entity.setData_exclusao(LocalDateTime.now());
            entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
            repository.save(entity);
            log.info("excluido !");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public List<SetOrcamento> findAllClienteId(Integer id_cliente) {
        return repository.listAllByClientId(id_cliente);
    }

    public SetOrcamento findAllById(Integer id_orcamento) {
        return repository.findById(id_orcamento).orElse(null);
    }

    public List<SetOrcamento> findAllBySituacaoId(Integer id_situacaocadastro) {
        return repository.findAllBySituacaoId(id_situacaocadastro);
    }

    public Integer findAllMaxId() {
        return repository.findAllMaxId();
    }

    public SetOrcamento update(SetOrcamento dto) throws Exception {
        try {
            Optional<SetOrcamento> optional = repository.findById(dto.getId_orcamento());
            SetOrcamento entity = optional.get();
            entity = dto;
            entity.setData_alteracao(LocalDateTime.now());
            repository.save(entity);
            return entity;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void exclusaoLogica(SetOrcamento orcamento) throws Exception {
        try {
            delete(orcamento);
            var ordemservicos = ordemServicoService.findAllByOrcamentoId(orcamento.getId_orcamento());
            var posvenda = orcamentoPosVendasService.findAllByOrcamentoId(orcamento.getId_orcamento());
            var contatos = orcamentoContatoService.findAllByOrcamentoId(orcamento.getId_orcamento());
            var notafiscal = notaFiscalService.findAllByOrcamentoId(orcamento.getId_orcamento());
            var pagamentos = pagamentoService.findAllByOrcamentoId(orcamento.getId_orcamento());
            var contrati = contratoService.findByIdOrcamento(orcamento.getId_orcamento());
            if (ordemservicos.size() > 0) {
                ordemservicos.forEach(setOrdemServico -> {
                    try {
                        ordemServicoService.delete(setOrdemServico);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if (posvenda.size() > 0) {
                posvenda.forEach(obj -> {
                    try {
                        orcamentoPosVendasService.delete(obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if (contatos.size() > 0) {
                contatos.forEach(obj -> {
                    try {
                        orcamentoContatoService.delete(obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if (notafiscal.size() > 0) {
                notafiscal.forEach(obj -> {
                    try {
                        notaFiscalService.delete(obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if (pagamentos.size() > 0) {
                pagamentos.forEach(obj -> {
                    try {
                        pagamentoService.delete(obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if (Objects.nonNull(contrati)) {
                try {
                    contratoService.delete(contrati);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } catch (Exception e) {
            throw new Exception();
        }
    }
}
