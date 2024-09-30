package br.com.onetec.infra.rest.provider;

import br.com.onetec.domain.gateway.IApiEnderecoGateway;
import br.com.onetec.infra.rest.dto.Address;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiEnderecoRest implements IApiEnderecoGateway {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ApiEnderecoRest() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Address getAddressByCep(String cep) {

        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        String response = restTemplate.getForObject(url, String.class);

        if (response != null && !response.contains("erro")) {
            try {
                JsonNode jsonNode = objectMapper.readTree(response);
                String logradouro = jsonNode.get("logradouro").asText();
                String bairro = jsonNode.get("bairro").asText();
                String localidade = jsonNode.get("localidade").asText();
                String uf = jsonNode.get("uf").asText();

                return Address.builder()
                        .logradouro(logradouro)
                        .bairro(bairro)
                        .uf(uf)
                        .localidade(localidade)
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

