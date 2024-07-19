package br.com.onetec.application.service.securityservice;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public interface ISecurityService {

    InMemoryUserDetailsManager configSecuriry ();
}
