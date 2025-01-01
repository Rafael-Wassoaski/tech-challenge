package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceUsuarioForTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

public class UsuarioControllerTest {

    private UsuarioController usuarioController;
    private MapPersistenceUsuarioForTests mapPersistenceUsuarioForTests;

    @Test
    void deveriaCriarUmUsuarioComEmailESenha(){
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        usuarioController = new UsuarioController(mapPersistenceUsuarioForTests, "salParaTestes");
        String emailUsuario = "email@email.com";
        String senhaUsuario = "senha";
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);

        usuarioController.cadastrar(usuario);

        Optional<Usuario> usuarioCadastrado = mapPersistenceUsuarioForTests.buscarPorEmail(emailUsuario);

        Assertions.assertTrue(usuarioCadastrado.isPresent());
        Assertions.assertEquals(emailUsuario, usuarioCadastrado.get().getEmail());
    }


}
