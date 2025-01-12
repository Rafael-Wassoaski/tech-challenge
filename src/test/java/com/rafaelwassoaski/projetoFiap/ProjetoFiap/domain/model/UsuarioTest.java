package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UsuarioTest {

    @Test
    void deveriaCriarUmUsuarioComEmailECPFESenha() throws Exception {
        String email = "teste@teste.com";
        String senha = "teste123456";
        String cpf = "000.000.000-00";
        Usuario usuario = new Usuario(email, senha, cpf);

        Assertions.assertEquals(email, usuario.getEmail());
        Assertions.assertEquals(senha, usuario.getSenha());
    }

    @Test
    void deveriaCriarUmUsuarioComEmailECpfESemSenha() throws Exception {
        String email = "teste@teste.com";
        String cpf = "000.000.000-00";
        Usuario usuario = new Usuario(email, cpf);

        Assertions.assertEquals(email, usuario.getEmail());
        Assertions.assertEquals(cpf, usuario.getCpf());
        Assertions.assertNull(usuario.getSenha());
    }

    @Test
    void deveriaAtualizarOEmailDeUmUsuario() throws Exception {
        String email = "teste@teste.com";
        String email2 = "teste2@teste2.com";
        String senha = "teste123456";
        String cpf = "000.000.000-00";
        Usuario usuario = new Usuario(email, senha, cpf);

        Assertions.assertEquals(email, usuario.getEmail());

        usuario.setEmail(email2);
        Assertions.assertEquals(email2, usuario.getEmail());
    }

    @Test
    void deveriaAtualizarASenhaDeUmUsuario() throws Exception {
        String email = "teste@teste.com";
        String senha = "teste123456";
        String senha2 = "teste654321";
        String cpf = "000.000.000-00";

        Usuario usuario = new Usuario(email, senha, cpf);

        Assertions.assertEquals(senha, usuario.getSenha());

        usuario.setSenha(senha2);
        Assertions.assertEquals(senha2, usuario.getSenha());
    }

    @Test
    void deveriaCriarUmUsuarioComOPapelDeCliente() throws Exception {
        String email = "teste@teste.com";
        String senha = "teste123456";
        String cpf = "000.000.000.00";
        Usuario usuario = new Usuario(email, senha, cpf);

        Assertions.assertEquals(Papel.CLIENTE, usuario.getPapel());
    }
}
