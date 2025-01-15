package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.google.gson.Gson;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.dto.TokenDTO;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.*;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Acompanhamento;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceUsuarioRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.Encriptador;
import jakarta.servlet.http.Cookie;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class AcompanhamentoControllerTest {

    @Autowired
    private PersistenceItemRepository<Acompanhamento> acompanhamentoPersistenceItemRepository;
    @Autowired
    private PersistenceUsuarioRepository persistenceUsuarioRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private String emailUsuario = "email@email.com";
    private String senhaUsuario = "senha";
    @Value("${custom.sal}")
    private String sal;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void afterEach() {
        List<Acompanhamento> acompanhamentoList = acompanhamentoPersistenceItemRepository.buscarTodos();

        acompanhamentoList.forEach(acompanhamento -> acompanhamentoPersistenceItemRepository.deletarPorNome(acompanhamento.getNome()));
        persistenceUsuarioRepository.deletarPorEmail(emailUsuario);
    }

    @Test
    void deveriaRetornarTodosOsAcompanhamentos() throws Exception {
        Acompanhamento bebiba1 = new Acompanhamento("acompanhamento1", 10);
        Acompanhamento bebiba2 = new Acompanhamento("acompanhamento2", 5);
        Acompanhamento bebiba3 = new Acompanhamento("acompanhamento3", 15);

        acompanhamentoPersistenceItemRepository.salvar(bebiba1);
        acompanhamentoPersistenceItemRepository.salvar(bebiba2);
        acompanhamentoPersistenceItemRepository.salvar(bebiba3);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/acompanhamentos")
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void deveriaCriarUmAcompanhamento() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);
        Acompanhamento acompanhamento = new Acompanhamento("acompanhamento1", 10, "categoria");

        Optional<Usuario> usuarioSalvoOptional = persistenceUsuarioRepository.buscarPorEmail(usuario.getEmail());
        Usuario usuarioSalvo = usuarioSalvoOptional.get();
        usuarioSalvo.setPapel(Papel.GERENTE);
        persistenceUsuarioRepository.atualizar(usuarioSalvo);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/acompanhamentos/criar")
                        .content(new Gson().toJson(acompanhamento))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        Optional<Acompanhamento> optionalAcompanhamento = acompanhamentoPersistenceItemRepository.buscarPorNome(acompanhamento.getNome());

        Assertions.assertTrue(optionalAcompanhamento.isPresent());
        Assertions.assertEquals(acompanhamento.getNome(), optionalAcompanhamento.get().getNome());
        Assertions.assertEquals(acompanhamento.getPreco(), optionalAcompanhamento.get().getPreco());
        Assertions.assertEquals(acompanhamento.getCategoria(), optionalAcompanhamento.get().getCategoria());
    }

    @Test
    void deveriaRetornarUmErroQuandoUmClienteTentarCriarUmAcompanhamento() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);
        Acompanhamento acompanhamento = new Acompanhamento("acompanhamento1", 10, "categoria");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/acompanhamentos/criar")
                        .content(new Gson().toJson(acompanhamento))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        Optional<Acompanhamento> optionalAcompanhamento = acompanhamentoPersistenceItemRepository.buscarPorNome(acompanhamento.getNome());

        Assertions.assertTrue(optionalAcompanhamento.isEmpty());
    }

    @Test
    void deveriaAtualizarUmAcompanhamento() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);
        double precoOriginal = 10;
        Acompanhamento acompanhamento = new Acompanhamento("acompanhamento1", precoOriginal, "categoria");

        Optional<Usuario> usuarioSalvoOptional = persistenceUsuarioRepository.buscarPorEmail(usuario.getEmail());
        Usuario usuarioSalvo = usuarioSalvoOptional.get();
        usuarioSalvo.setPapel(Papel.GERENTE);
        persistenceUsuarioRepository.atualizar(usuarioSalvo);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/acompanhamentos/criar")
                .content(new Gson().toJson(acompanhamento))
                .contentType("application/json")
                .cookie(new Cookie("token", tokenDTO.getToken()))
                .accept(MediaType.APPLICATION_JSON));

        acompanhamento.setPreco(50.0);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/acompanhamentos/atualizar")
                        .content(new Gson().toJson(acompanhamento))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Acompanhamento> optionalAcompanhamento = acompanhamentoPersistenceItemRepository.buscarPorNome(acompanhamento.getNome());

        Assertions.assertTrue(optionalAcompanhamento.isPresent());
        Assertions.assertEquals(acompanhamento.getNome(), optionalAcompanhamento.get().getNome());
        Assertions.assertEquals(acompanhamento.getPreco(), optionalAcompanhamento.get().getPreco());
        Assertions.assertNotEquals(precoOriginal, optionalAcompanhamento.get().getPreco());
        Assertions.assertEquals(acompanhamento.getCategoria(), optionalAcompanhamento.get().getCategoria());
    }

    @Test
    void deveriaRetornarUmErroQuandoUmClienteTentarAtualizarUmAcompanhamento() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);
        double precoOriginal = 10;
        Acompanhamento acompanhamento = new Acompanhamento("acompanhamento1", precoOriginal, "categoria");
        acompanhamentoPersistenceItemRepository.salvar(acompanhamento);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);
        acompanhamento.setPreco(50.0);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/acompanhamentos/atualizar")
                        .content(new Gson().toJson(acompanhamento))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        Optional<Acompanhamento> optionalAcompanhamento = acompanhamentoPersistenceItemRepository.buscarPorNome(acompanhamento.getNome());

        Assertions.assertTrue(optionalAcompanhamento.isPresent());
        Assertions.assertEquals(precoOriginal, optionalAcompanhamento.get().getPreco());
    }

    @Test
    void deveriaDeletarUmAcompanhamento() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);
        Acompanhamento acompanhamento = new Acompanhamento("acompanhamento1", 10, "categoria");

        Optional<Usuario> usuarioSalvoOptional = persistenceUsuarioRepository.buscarPorEmail(usuario.getEmail());
        Usuario usuarioSalvo = usuarioSalvoOptional.get();
        usuarioSalvo.setPapel(Papel.GERENTE);
        persistenceUsuarioRepository.atualizar(usuarioSalvo);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/acompanhamentos/criar")
                        .content(new Gson().toJson(acompanhamento))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/acompanhamentos/deletar/" + acompanhamento.getNome())
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        Optional<Acompanhamento> optionalAcompanhamento = acompanhamentoPersistenceItemRepository.buscarPorNome(acompanhamento.getNome());

        Assertions.assertTrue(optionalAcompanhamento.isEmpty());
    }

    @Test
    void deveriaRetornarUmErroQuandoUmClienteTentarDeletarUmAcompanhamento() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);
        Acompanhamento acompanhamento = new Acompanhamento("acompanhamento1", 10, "categoria");
        acompanhamentoPersistenceItemRepository.salvar(acompanhamento);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/acompanhamentos/criar")
                        .content(new Gson().toJson(acompanhamento))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/acompanhamentos/deletar/" + acompanhamento.getNome())
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        Optional<Acompanhamento> optionalAcompanhamento = acompanhamentoPersistenceItemRepository.buscarPorNome(acompanhamento.getNome());

        Assertions.assertTrue(optionalAcompanhamento.isPresent());
    }
}
