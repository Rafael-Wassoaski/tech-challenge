package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Cliente;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceClienteForTests;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.ClienteDomainService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClienteServiceTest {

    private ClienteService clienteService;
    private MapPersistenceClienteForTests mapPersistenceClienteForTests;

    @Test
    void deveriaCriarUmUsuarioApenasComCPF() throws Exception {
        mapPersistenceClienteForTests = new MapPersistenceClienteForTests();
        clienteService = new ClienteService(mapPersistenceClienteForTests);
        String cpf = "000.000.000-00";

        Cliente cliente = new Cliente(cpf);
        Cliente clienteSalvo = clienteService.criar(cliente);

        Assertions.assertEquals(cpf, clienteSalvo.getCpf());
    }

    @Test
    void naoDeveriaCriarDoisUsuariosComOMesmoCPF() throws Exception {
        mapPersistenceClienteForTests = new MapPersistenceClienteForTests();
        clienteService = new ClienteService(mapPersistenceClienteForTests);
        String cpf = "000.000.000-00";

        Cliente cliente = new Cliente(cpf);
        clienteService.criar(cliente);

        Assertions.assertThrows(Exception.class, () -> {
            clienteService.criar(cliente);
        });
    }

    @Test
    void deveriaCriarUmUsuarioComEmailENomeMasSemCPF() throws Exception {
        mapPersistenceClienteForTests = new MapPersistenceClienteForTests();
        clienteService = new ClienteService(mapPersistenceClienteForTests);
        String email = "teste@teste.com";
        String nome = "teste";

        Cliente cliente = new Cliente(email, nome);
        Cliente clienteSalvo = clienteService.criar(cliente);

        Assertions.assertNull(clienteSalvo.getCpf());
        Assertions.assertEquals(email, clienteSalvo.getEmail());
        Assertions.assertEquals(nome, clienteSalvo.getNome());
    }

    @Test
    void naoDeveriaCriarDoisUsuariosComOMesmoEmail() throws Exception {
        mapPersistenceClienteForTests = new MapPersistenceClienteForTests();
        clienteService = new ClienteService(mapPersistenceClienteForTests);
        String email = "teste@teste.com";
        String nome = "teste";

        Cliente cliente = new Cliente(email, nome);
        clienteService.criar(cliente);

        Assertions.assertThrows(Exception.class, () -> {
            clienteService.criar(cliente);
        });
    }


    @Test
    void deveriaRetornarOCpfComoIndetificador() throws Exception {
        mapPersistenceClienteForTests = new MapPersistenceClienteForTests();
        clienteService = new ClienteService(mapPersistenceClienteForTests);
        String cpf = "000.000.000-00";

        Cliente cliente = new Cliente(cpf);
        ClienteDomainService clienteDomainService = new ClienteDomainService();
        String identificador = clienteDomainService.pegarIdentificador(cliente);

        Assertions.assertEquals(identificador, cpf);
    }

    @Test
    void deveriaRetornarEmailComoIndetificador() throws Exception {
        mapPersistenceClienteForTests = new MapPersistenceClienteForTests();
        clienteService = new ClienteService(mapPersistenceClienteForTests);
        String email = "teste@teste.com";
        String nome = "teste";

        Cliente cliente = new Cliente(email, nome);
        ClienteDomainService clienteDomainService = new ClienteDomainService();
        String identificador = clienteDomainService.pegarIdentificador(cliente);

        Assertions.assertEquals(identificador, email);
    }


    @Test
    void deveriaBuscarUmUsuarioPorCPF() throws Exception {
        mapPersistenceClienteForTests = new MapPersistenceClienteForTests();
        clienteService = new ClienteService(mapPersistenceClienteForTests);
        String cpf = "000.000.000-00";

        Cliente cliente = new Cliente(cpf);
        clienteService.criar(cliente);
        Cliente clienteSalvo = clienteService.buscarCliente(cpf);

        Assertions.assertEquals(cpf, clienteSalvo.getCpf());
        Assertions.assertNull(clienteSalvo.getEmail());
        Assertions.assertNull(clienteSalvo.getNome());
    }
}
