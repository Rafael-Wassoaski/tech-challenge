package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.google.gson.Gson;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceUsuarioRepository;
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
public class UsuarioControllerTest {

    @Autowired
    private PersistenceUsuarioRepository mapPersistenceUsuarioForTests;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    String emailUsuario = "email@email.com";
    String senhaUsuario = "senha";
    String cpf = "000.000.000-00";
    String nome = "teste";

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void after() {
        mapPersistenceUsuarioForTests.deletarPorEmail(emailUsuario);
    }

    @Test
    void deveriaCriarUmUsuarioComEmailESenha() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, nome, senhaUsuario, cpf);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/cadastro")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        Optional<Usuario> usuarioCadastrado = mapPersistenceUsuarioForTests.buscarPorEmail(emailUsuario);

        Assertions.assertTrue(usuarioCadastrado.isPresent());
        Assertions.assertEquals(emailUsuario, usuarioCadastrado.get().getEmail());
        Assertions.assertEquals(cpf, usuarioCadastrado.get().getCpf());
    }

    @Test
    void naoDeveriaCriarUmUsuarioGerentePelaApi() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, nome, senhaUsuario, cpf);
        usuario.setPapel(Papel.GERENTE);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/cadastro")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        Optional<Usuario> usuarioCadastrado = mapPersistenceUsuarioForTests.buscarPorEmail(emailUsuario);

        Assertions.assertNotEquals(Papel.GERENTE, usuarioCadastrado.get().getPapel());
        Assertions.assertEquals(Papel.CLIENTE, usuarioCadastrado.get().getPapel());
    }

    @Test
    void deveriaFazerLoginComUmUsuarioCadastrado() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, nome, senhaUsuario, cpf);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/cadastro")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deveriaFazerLoginApenasComCpfESenha() throws Exception {
        Usuario usuario = new Usuario(cpf, senhaUsuario);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/usuarios/cadastro")
                .content(new Gson().toJson(usuario))
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
