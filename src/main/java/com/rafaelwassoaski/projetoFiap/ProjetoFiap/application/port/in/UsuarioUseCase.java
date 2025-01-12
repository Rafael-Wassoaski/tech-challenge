package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioUseCase {

    Usuario criar(Usuario usuario) throws Exception;

    UserDetails logar(String email, String senha) throws Exception;

    UserDetails buscarUserDetails(String email) throws Exception;

    Usuario buscarUsuario(String email) throws Exception;

}
