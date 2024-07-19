package br.com.onetec.application.service.securityservice;

import br.com.onetec.domain.usecase.security.IUseCaseSecurity;
import br.com.onetec.domain.usecase.security.UseCaseSecurityImp;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImp  implements  ISecurityService{

    IUseCaseSecurity useCase;

    @Override
    public InMemoryUserDetailsManager configSecuriry() {
        this.useCase = new UseCaseSecurityImp();
        InMemoryUserDetailsManager memory = new InMemoryUserDetailsManager();
        for (User user : useCase.getAllUsers()) {
            memory.createUser(user);
        }
        return memory;
    }

}
