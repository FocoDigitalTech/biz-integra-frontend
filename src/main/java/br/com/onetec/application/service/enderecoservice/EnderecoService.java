package br.com.onetec.application.service.enderecoservice;

import br.com.onetec.application.model.Endereco;
import br.com.onetec.infra.db.model.SetEnderecos;
import br.com.onetec.infra.db.repository.IEnderecosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class EnderecoService {

    @Autowired
    IEnderecosRepository repository;

    public void save(List<Endereco> enderecos,Integer idCliente, Integer idUsuario) {
        enderecos.forEach(e -> {
            SetEnderecos et = new SetEnderecos();
            et.setAtivo("S");
            et.setArea_imovel(e.getFieldEnderecosArea());
            et.setBairro_imovel(e.getFieldEnderecosBairro());
            et.setCep_imovel(e.getFieldEnderecosCEP());
            et.setEndereco_imovel(e.getFieldEnderecosEndereço());
            et.setCidade_imovel(e.getFieldEnderecosCidade());
            et.setComplemento_imovel(e.getFieldEnderecosComplemento());
            et.setData_inclusao(LocalDateTime.now());
            et.setId_cliente(idCliente);
            //et.setId_estado(e.getComboEnderecosUF());
            et.setId_usuario(idUsuario);
            et.setNome_responsavel(e.getFieldEnderecosReponsavel());
            et.setNumero_imovel(e.getFieldEnderecosNumero());
            et.setPonto_referencia(e.getFieldEnderecosPontodeReferencia());
            et.setPagina_guia(e.getFieldEnderecosPagGuia());
            et.setTelefone_local(e.getFieldEnderecosTelefone());
            log.info("Salvando novo endereço :" + et.toString());
            repository.save(et);
        });
    }
}
