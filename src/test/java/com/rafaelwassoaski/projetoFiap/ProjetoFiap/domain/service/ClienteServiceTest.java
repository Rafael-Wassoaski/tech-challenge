package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Cliente;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceUsuarioForTests;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.Encriptador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClienteServiceTest {

    private ClienteDomainService clienteDomainService;
    private MapPersistenceUsuarioForTests mapPersistenceUsuarioForTests;

    @Test
    void deveriaValidarUmClienteComEmail() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        clienteDomainService = new ClienteDomainService();
        String email = "teste@teste.com";
        String nome = "teste";
        Cliente cliente = new Cliente(email, nome);

        Assertions.assertTrue(() -> {
            try {
                return clienteDomainService.clienteEhValido(cliente);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void deveriaValidarUmClienteComCpf() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        clienteDomainService = new ClienteDomainService();
        String cpf = "000.000.000-00";
        Cliente cliente = new Cliente(cpf);

        Assertions.assertTrue(() -> {
            try {
                return clienteDomainService.clienteEhValido(cliente);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void deveriaRetornarEmailComoInvalido() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        clienteDomainService = new ClienteDomainService();
        String email = "testeteste.com";
        String nome = "teste";
        Cliente cliente = new Cliente(email, nome);

        Assertions.assertThrows(Exception.class, () -> clienteDomainService.clienteEhValido(cliente));
    }

    @Test
    void deveriaRetornarErroAoTentarCriarUsuarioComCPFNulo() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        clienteDomainService = new ClienteDomainService();
        Cliente cliente = new Cliente(null);

        Assertions.assertThrows(Exception.class, () -> clienteDomainService.clienteEhValido(cliente));
    }

    @Test
    void deveriaRetornarErroAoTentarCriarUsuarioComCPFVazio() throws Exception {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        clienteDomainService = new ClienteDomainService();
        Cliente cliente = new Cliente("");

        Assertions.assertThrows(Exception.class, () -> clienteDomainService.clienteEhValido(cliente));
    }

}
