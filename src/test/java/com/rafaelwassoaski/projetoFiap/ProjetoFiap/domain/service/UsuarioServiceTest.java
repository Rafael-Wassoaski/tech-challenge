package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceUsuarioForTests;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.Encriptador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UsuarioServiceTest {

    private UsuarioDomainService usuarioService;
    private MapPersistenceUsuarioForTests mapPersistenceUsuarioForTests;
    private String salParaTestes = "salParaTestes";

    @Test
    void deveriaRetornarEmailComoInvalido() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Encriptador encriptador = new Encriptador(salParaTestes);
        usuarioService = new UsuarioDomainService(encriptador);
        String email = "testeteste.com";
        String senha = "teste123456";
        String cpf = "000.000.000.36";
        Usuario usuario = new Usuario(email, senha, cpf);

        Assertions.assertThrows(Exception.class, () -> usuarioService.validarEmail(usuario));
    }

}
