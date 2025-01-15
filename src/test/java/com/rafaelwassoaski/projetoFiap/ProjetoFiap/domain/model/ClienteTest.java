package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClienteTest {
    @Test
    void deveriaCriarUmClienteComEmailENome() throws Exception {
        String email = "teste@teste.com";
        String nome = "teste";
        Cliente cliente = new Cliente(email, nome);

        Assertions.assertEquals(email, cliente.getEmail());
        Assertions.assertEquals(nome, cliente.getNome());
    }

    @Test
    void deveriaCriarUmClienteComCPF() throws Exception {
        String cpf = "000.000.000-00";
        Cliente cliente = new Cliente(cpf);

        Assertions.assertEquals(cpf, cliente.getCpf());
    }

    @Test
    void deveriaCriarUmClienteComCpf() throws Exception {
        String cpf = "000.000.000-00";
        Cliente cliente = new Cliente(cpf);

        Assertions.assertNull( cliente.getEmail());
        Assertions.assertEquals(cpf, cliente.getCpf());
        Assertions.assertNull(cliente.getNome());
    }

    @Test
    void deveriaAtualizarOEmailDeUmCliente() throws Exception {
        String email = "teste@teste.com";
        String email2 = "teste2@teste2.com";
        String nome = "teste";
        Cliente cliente = new Cliente(email, nome);

        Assertions.assertEquals(email, cliente.getEmail());

        cliente.setEmail(email2);
        Assertions.assertEquals(email2, cliente.getEmail());
    }

    @Test
    void deveriaAtualizarONomeDeUmCliente() throws Exception {
        String email = "teste@teste.com";
        String nome = "teste";
        String nome2 = "teste2";

        Cliente cliente = new Cliente(email, nome);

        Assertions.assertEquals(nome, cliente.getNome());

        cliente.setNome(nome2);
        Assertions.assertEquals(nome2, cliente.getNome());
    }
}
