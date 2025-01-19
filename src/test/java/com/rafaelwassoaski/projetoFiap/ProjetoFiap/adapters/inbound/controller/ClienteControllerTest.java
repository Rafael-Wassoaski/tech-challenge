package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.google.gson.Gson;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Cliente;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceClienteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class ClienteControllerTest {

    @Autowired
    private PersistenceClienteRepository persistenceClienteRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    String emailCliente = "email@email.com";
    String cpf = "304.091.730-70";
    String nome = "teste";

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void after() {
        persistenceClienteRepository.deletarPorEmail(emailCliente);
    }

    @Test
    void deveriaCriarUmClienteComEmailENome() throws Exception {
        Cliente cliente = new Cliente(emailCliente, nome);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/clientes/cadastro")
                        .content(new Gson().toJson(cliente))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        Optional<Cliente> clienteCadastrado = persistenceClienteRepository.buscarPorEmail(emailCliente);

        Assertions.assertTrue(clienteCadastrado.isPresent());
        Assertions.assertEquals(emailCliente, clienteCadastrado.get().getEmail());
        Assertions.assertEquals(nome, clienteCadastrado.get().getNome());
    }

    @Test
    void deveriaCriarUmClienteComCpf() throws Exception {
        Cliente cliente = new Cliente(cpf);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/clientes/cadastro")
                        .content(new Gson().toJson(cliente))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        Optional<Cliente> clienteCadastrado = persistenceClienteRepository.buscarPorCpf(cpf);

        Assertions.assertTrue(clienteCadastrado.isPresent());
        Assertions.assertEquals(cpf, clienteCadastrado.get().getCpf());
    }
}
