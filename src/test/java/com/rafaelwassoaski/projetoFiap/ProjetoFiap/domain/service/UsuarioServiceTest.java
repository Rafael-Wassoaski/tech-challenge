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
    void deveriaCriarUmUsuarioComSenhaComSenhaCriptgrafada() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Encriptador encriptador = new Encriptador(salParaTestes);
        usuarioService = new UsuarioService(mapPersistenceUsuarioForTests, encriptador);
        String email = "teste@teste.com";
        String senha = "teste123456";

        Usuario usuario = usuarioService.criar(email, senha);

        Assertions.assertTrue(encriptador.senhasBatem(senha, usuario.getSenha()));
        Assertions.assertNotEquals(senha, usuario.getSenha());
    }

    @Test
    void deveriaRetornarOUsuarioCadastradoComBaseNoEmail() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Encriptador encriptador = new Encriptador(salParaTestes);
        usuarioService = new UsuarioService(mapPersistenceUsuarioForTests, encriptador);
        String email = "teste@teste.com";
        String senha = "teste123456";

        usuarioService.criar(email, senha);
        UserDetails userDetails = usuarioService.buscarUsuario(email);

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(email, userDetails.getUsername());
    }

    @Test
    void deveriaRealizarOLoginESenhaDeUmUsuarioCadastrado() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Encriptador encriptador = new Encriptador(salParaTestes);
        usuarioService = new UsuarioService(mapPersistenceUsuarioForTests, encriptador);
        String email = "teste@teste.com";
        String senha = "teste123456";

        usuarioService.criar(email, senha);

        Assertions.assertDoesNotThrow(() -> {
            UserDetails userDetails = usuarioService.logar(email, senha);
            Assertions.assertNotNull(userDetails);
            Assertions.assertEquals(email, userDetails.getUsername());
        });
    }

    @Test
    void naoDeveriaRealizarLoginComSenhaIncorreta() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Encriptador encriptador = new Encriptador(salParaTestes);
        usuarioService = new UsuarioService(mapPersistenceUsuarioForTests, encriptador);
        String email = "teste@teste.com";
        String senha = "teste123456";
        String senhaErrada = "teste654321";

        usuarioService.criar(email, senha);

        Assertions.assertThrows(Exception.class, () -> {
            usuarioService.logar(email, senhaErrada);
        });
    }

    @Test
    void naoDeveriaRealizarLoginComEmailIncorreto() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Encriptador encriptador = new Encriptador(salParaTestes);
        usuarioService = new UsuarioService(mapPersistenceUsuarioForTests, encriptador);
        String email = "teste@teste.com";
        String emailErrado = "testeErrado@teste.com";
        String senha = "teste123456";

        usuarioService.criar(email, senha);

        Assertions.assertThrows(Exception.class, () -> {
            usuarioService.logar(emailErrado, senha);
        });
    }
}
