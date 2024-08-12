package br.com.onetec.domain.usecase.securityusecase;

import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface IUseCaseSecurity {

    List<User> getAllUsers ();

}
