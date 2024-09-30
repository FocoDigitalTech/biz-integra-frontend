package br.com.onetec.application.service.tipopagamentoservice;

import br.com.onetec.infra.db.model.SetTipoEventoFinanceiro;
import br.com.onetec.infra.db.repository.ISetTipoEventoFinanceiroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TipoEventoFinanceiroService {


    private ISetTipoEventoFinanceiroRepository repository;

    @Autowired
    public void initServices (ISetTipoEventoFinanceiroRepository repository1){
        this.repository = repository1;
    }

    public Page<SetTipoEventoFinanceiro> list(Pageable pageable, Specification<SetTipoEventoFinanceiro> filter) {
        log.info("Pageable: {}", pageable);
        Page<SetTipoEventoFinanceiro> page = repository.findAll(filter, pageable);
        return repository.findAll(filter, pageable);
    }

    public void save(SetTipoEventoFinanceiro dto) throws Exception {
        try {
            repository.save(dto);
        }catch (Exception e){
            throw new Exception();
        }
    }

    public void delete(SetTipoEventoFinanceiro item) throws Exception {
        try {
            repository.delete(item);
        } catch (Exception e){
            throw new Exception();
        }
    }

}
