package com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceUsuarioRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private PersistenceUsuarioRepository persistenceUsuarioRepository;
    private Encriptador encriptador;

    public CustomAuthenticationProvider(PersistenceUsuarioRepository persistenceUsuarioRepository, Encriptador encriptador) {
        this.persistenceUsuarioRepository = persistenceUsuarioRepository;
        this.encriptador = encriptador;
    }

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials()
                .toString();
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, encriptador);

        if ("externaluser".equals(username) && "pass".equals(password)) {
            return new UsernamePasswordAuthenticationToken
                    (username, password, Collections.emptyList());
        } else {
            throw new
                    BadCredentialsException("External system authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}