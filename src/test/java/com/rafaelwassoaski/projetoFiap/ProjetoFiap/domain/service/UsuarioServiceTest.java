package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceUsuarioForTests;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.Encriptador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioServiceTest {

    private UsuarioService usuarioService;
    private MapPersistenceUsuarioForTests mapPersistenceUsuarioForTests;
    private String salParaTestes = "salParaTestes";

    @Test
    void deveriaRetornarEmailComoInvalido() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Encriptador encriptador = new Encriptador(salParaTestes);
        usuarioService = new UsuarioService(encriptador);
        String email = "testeteste.com";
        String senha = "teste123456";
        Usuario usuario = new Usuario(email, senha);

        Assertions.assertThrows(Exception.class, () -> usuarioService.validarEmail(usuario));
    }

}
