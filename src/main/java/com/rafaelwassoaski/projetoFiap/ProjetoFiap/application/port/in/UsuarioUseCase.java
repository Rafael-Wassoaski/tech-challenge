package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioUseCase {

    Usuario criar(String email, String senha) throws Exception;

    UserDetails logar(String email, String senha) throws Exception;

    UserDetails buscarUsuario(String email) throws Exception;

}
