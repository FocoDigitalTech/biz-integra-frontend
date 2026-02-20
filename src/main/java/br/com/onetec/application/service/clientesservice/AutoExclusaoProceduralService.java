package br.com.onetec.application.service.clientesservice;

import br.com.onetec.application.security.UsuarioAutenticadoConfig;
import br.com.onetec.infra.db.model.SetContrato;
import br.com.onetec.infra.db.model.SetEnderecos;
import br.com.onetec.infra.db.model.SetOrcamento;
import br.com.onetec.infra.db.model.SetOrdemServico;
import br.com.onetec.infra.db.repository.IEnderecosRepository;
import br.com.onetec.infra.db.repository.ISetContratoRepository;
import br.com.onetec.infra.db.repository.ISetOrcamentoRepository;
import br.com.onetec.infra.db.repository.ISetOrdemServicoRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class AutoExclusaoProceduralService {

    private final IEnderecosRepository enderecosRepository;

    private final ISetContratoRepository setContratoRepository;

    private final ISetOrcamentoRepository orcamentoRepository;

    private final ISetOrdemServicoRepository ordemServicoRepository;


    @Autowired
    public AutoExclusaoProceduralService(IEnderecosRepository enderecosRepository, ISetContratoRepository setContratoRepository, ISetOrcamentoRepository orcamentoRepository, ISetOrdemServicoRepository ordemServicoRepository) {
        this.enderecosRepository = enderecosRepository;
        this.setContratoRepository = setContratoRepository;
        this.orcamentoRepository = orcamentoRepository;
        this.ordemServicoRepository = ordemServicoRepository;
    }

    @SneakyThrows
    public void executeProcessClienteExclude(Integer id_cliente) {
        log.info("INICIANDO PROCESSO DE EXCLUSÃO PROCEDURAL");
        executarAutomacaoExcluirEnderecos(id_cliente);
        executarAutomacaoExcluirOrcamentos(id_cliente);
        log.info("PROCESSO FINALIZADO !");
    }

    public void executarAutomacaoExcluirEnderecos(Integer id_cliente) throws Exception {
        var lista = enderecosRepository.findAllByCliente(id_cliente);
        try {
            if (lista.size() > 0) {
                lista.forEach(enderecos -> {
                    Optional<SetEnderecos> optional = enderecosRepository.findById(enderecos.getId_endereco());
                    SetEnderecos entity = optional.get();
                    entity.setAtivo("N");
                    entity.setData_exclusao(LocalDateTime.now());
                    entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                    enderecosRepository.save(entity);
                    log.info("excluido !");
                });
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void executarAutomacaoExcluirOrcamentos(Integer id_cliente) throws Exception {
        var lista = orcamentoRepository.listAllByClientId(id_cliente);
        try {
            if (lista.size() > 0) {
                lista.forEach(dto -> {
                    Optional<SetOrcamento> optional = orcamentoRepository.findById(dto.getId_orcamento());
                    SetOrcamento entity = optional.get();
                    entity.setAtivo("N");
                    entity.setData_exclusao(LocalDateTime.now());
                    entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                    orcamentoRepository.save(entity);
                    log.info("excluido !");
                    try {
                        executarAutomacaoExcluirCotratos(dto.getId_orcamento());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void executarAutomacaoExcluirCotratos(Integer id_orcamento) throws Exception {
        var lista = setContratoRepository.findByContratoId(id_orcamento);
        try {
            if (lista.size() > 0) {
                lista.forEach(dto -> {
                    Optional<SetContrato> optional = setContratoRepository.findById(dto.getId_contrato());
                    SetContrato entity = optional.get();
                    entity.setAtivo("N");
                    entity.setData_exclusao(LocalDateTime.now());
                    entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                    setContratoRepository.save(entity);
                    log.info("excluido !");
                });
            }
            executarAutomacaoExcluirOrdemsServico(id_orcamento);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void executarAutomacaoExcluirOrdemsServico(Integer id_orcamento) throws Exception {
        var lista = ordemServicoRepository.listAllByOrcamentoId(id_orcamento);
        try {
            if (lista.size() > 0) {
                lista.forEach(dto -> {
                    Optional<SetOrdemServico> optional = ordemServicoRepository.findById(dto.getId_ordemservico());
                    SetOrdemServico entity = optional.get();
                    entity.setAtivo("N");
                    entity.setData_exclusao(LocalDateTime.now());
                    entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
                    ordemServicoRepository.save(entity);
                    log.info("excluido !");
                });
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }


}
