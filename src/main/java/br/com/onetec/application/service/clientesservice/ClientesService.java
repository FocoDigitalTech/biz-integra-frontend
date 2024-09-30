package br.com.onetec.application.service.clientesservice;

import br.com.onetec.application.configuration.UsuarioAutenticadoConfig;
import br.com.onetec.application.model.Cliente;
import br.com.onetec.infra.db.model.SetCliente;
import br.com.onetec.infra.db.repository.IClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClientesService {

    @Autowired
    IClienteRepository repository;

    public Page<SetCliente> list(Pageable pageable, Specification<SetCliente> filter) {
        return repository.findAll(filter, pageable);
    }


    public SetCliente save(Cliente dto) {
        SetCliente entity = new SetCliente();
        entity.setAtivo("S");
        entity.setNome_cliente(dto.getNomeField());
        entity.setNome_fantasia_cliente(dto.getNomeField());
        entity.setTelefone_cliente(dto.getTelefoneField());
        entity.setEmail_cliente(dto.getInternetEmailField());
        entity.setNome_contato_cliente(dto.getContatoField());
        entity.setCargo_contato_cliente("");
        entity.setId_anuncio(1);
        entity.setId_indicacao(1);
        entity.setCpf_cgc_cliente(dto.getCGCCPFField());
        entity.setIest_cliente("AG");
        entity.setTipo_naturezajuridica(dto.getFJField());
        entity.setNumero_naturezajuridica(dto.getCnpjField());
        entity.setObservacoes_cliente(dto.getObservacaoField());
        entity.setMarca_cliente("Marca Ltda");
        entity.setAdministradora_cliente("Concecionaria");
        entity.setCelular_cliente(dto.getCelularField());
        entity.setHora_ligacao_cliente(dto.getHoraField());
        entity.setId_tipo_imovel(1);
        entity.setEndereco_cliente("Rua 1");
        entity.setNumero_res_cliente("655");
        entity.setComplemento_cliente("Complemento");
        entity.setBairro_cliente("Teste");
        entity.setCep_cliente("000000");
        entity.setCidade_cliente("Teste");
        entity.setId_estado(1);
        entity.setResponsavel_cliente("Carlos");
        entity.setPonto_referencia_cliente("Teste");
        entity.setData_inclusao(LocalDateTime.now());
        entity.setId_usuario(UsuarioAutenticadoConfig.getUser().getId_usuario());
        log.info("Salvando novo cliente :" + entity.toString());
        repository.save(entity);
        return entity;
    }

    public SetCliente findById(Integer idCliente) {
        Optional<SetCliente> optional = repository.findById(idCliente);
        return optional.orElse(null);
    }
}
