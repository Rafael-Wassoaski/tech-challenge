package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceUsuarioForTests;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.UsuarioDomainService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.Encriptador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class UsuarioServiceTest {

    private com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService usuarioService;
    private MapPersistenceUsuarioForTests mapPersistenceUsuarioForTests;
    private String salParaTestes = "salParaTestes";

    @Test
    void deveriaCriarUmUsuarioComSenhaComSenhaCriptgrafada() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Encriptador encriptador = new Encriptador(salParaTestes);
        usuarioService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService(mapPersistenceUsuarioForTests, encriptador);
        String email = "teste@teste.com";
        String senha = "teste123456";
        String cpf = "000.000.000-00";

        Usuario usuario = new Usuario(email, senha, cpf);
        Usuario usuarioCriado = usuarioService.criar(usuario);

        Assertions.assertTrue(encriptador.senhasBatem(senha, usuarioCriado.getSenha()));
        Assertions.assertNotEquals(senha, usuarioCriado.getSenha());
        Assertions.assertEquals(cpf, usuarioCriado.getCpf());
    }

    @Test
    void deveriaRetornarOUsuarioCadastradoComBaseNoEmail() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Encriptador encriptador = new Encriptador(salParaTestes);
        usuarioService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService(mapPersistenceUsuarioForTests, encriptador);
        String email = "teste@teste.com";
        String senha = "teste123456";
        String cpf = "000.000.000-00";
        Usuario usuario = new Usuario(email, senha, cpf);

        usuarioService.criar(usuario);
        UserDetails userDetails = usuarioService.buscarUserDetails(email);

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(cpf, userDetails.getUsername());
    }

    @Test
    void deveriaRealizarOLoginESenhaDeUmUsuarioCadastrado() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Encriptador encriptador = new Encriptador(salParaTestes);
        usuarioService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService(mapPersistenceUsuarioForTests, encriptador);
        String email = "teste@teste.com";
        String senha = "teste123456";
        String cpf = "000.000.000-00";
        Usuario usuario = new Usuario(email, senha, cpf);

        usuarioService.criar(usuario);

        Assertions.assertDoesNotThrow(() -> {
            UserDetails userDetails = usuarioService.logar(email, senha);
            Assertions.assertNotNull(userDetails);
            Assertions.assertEquals(cpf, userDetails.getUsername());
        });
    }

    @Test
    void naoDeveriaRealizarLoginComSenhaIncorreta() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Encriptador encriptador = new Encriptador(salParaTestes);
        usuarioService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService(mapPersistenceUsuarioForTests, encriptador);
        String email = "teste@teste.com";
        String senha = "teste123456";
        String senhaErrada = "teste654321";
        String cpf = "000.000.000-00";
        Usuario usuario = new Usuario(email, senha, cpf);

        usuarioService.criar(usuario);

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
        String cpf = "000.000.000-00";
        Usuario usuario = new Usuario(email, senha, cpf);

        usuarioService.criar(usuario);

        Assertions.assertThrows(Exception.class, () -> {
            usuarioService.logar(emailErrado, senha);
        });
    }

    @Test
    void deveriaValidarSeOUsuarioEhUmGerente() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Encriptador encriptador = new Encriptador(salParaTestes);
        usuarioService = new UsuarioService(mapPersistenceUsuarioForTests, encriptador);
        UsuarioDomainService usuarioDomainService = new UsuarioDomainService();
        String email = "teste@teste.com";
        String senha = "teste123456";
        String cpf = "000.000.000-00";
        Usuario usuario = new Usuario(email, senha, cpf);
        usuario.setPapel(Papel.GERENTE);

        mapPersistenceUsuarioForTests.salvar(usuario);

        Assertions.assertTrue(() ->
                usuarioDomainService.usuarioEhGerente(usuario)
        );
    }

    @Test
    void deveriaValidarSeOUsuarioClienteNaoEhUmGerente() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Encriptador encriptador = new Encriptador(salParaTestes);
        usuarioService = new UsuarioService(mapPersistenceUsuarioForTests, encriptador);
        UsuarioDomainService usuarioDomainService = new UsuarioDomainService();
        String email = "teste@teste.com";
        String senha = "teste123456";
        String cpf = "000.000.000-00";

        Usuario usuario = new Usuario(email, senha, cpf);
        mapPersistenceUsuarioForTests.salvar(usuario);

        Assertions.assertFalse(() -> usuarioDomainService.usuarioEhGerente(usuario));
    }
}
