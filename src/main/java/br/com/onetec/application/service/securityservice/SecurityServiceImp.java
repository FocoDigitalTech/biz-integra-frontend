package br.com.onetec.application.service.securityservice;

import br.com.onetec.domain.usecase.securityusecase.imp.UseCaseSecurityImp;
import br.com.onetec.infra.db.model.SetUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImp  {


    private UseCaseSecurityImp useCase;

    @Autowired
    public void initServices(UseCaseSecurityImp useCase1) {
        this.useCase = useCase1;
    }

    public InMemoryUserDetailsManager configSecuriry() {
        InMemoryUserDetailsManager memory = new InMemoryUserDetailsManager();
        for (User user : useCase.getAllUsers()) {
            memory.createUser(user);
        }
        return memory;
    }

}
