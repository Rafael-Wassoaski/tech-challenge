package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.google.gson.Gson;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceUsuarioRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.Encriptador;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${custom.sal}")
    private String sal;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void after() {
        mapPersistenceUsuarioForTests.deletarPorEmail(emailUsuario);
    }

    @Test
    void deveriaFazerLoginComUmUsuarioCadastrado() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        UsuarioService usuarioService = new UsuarioService(mapPersistenceUsuarioForTests, new Encriptador(sal));
        usuarioService.criar(usuario);

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
