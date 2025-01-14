package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.google.gson.Gson;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.dto.TokenDTO;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.*;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Sobremesa;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceUsuarioRepository;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SobremesaControllerTest {

    @Autowired
    private PersistenceItemRepository<Sobremesa> sobremesaPersistenceItemRepository;
    @Autowired
    private PersistenceUsuarioRepository persistenceUsuarioRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private String emailUsuario = "email@email.com";
    private String senhaUsuario = "senha";
    private String cpf = "000.000.000-00";
    private String nome = "teste";

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void afterEach(){
        List<Sobremesa> sobremesaList = sobremesaPersistenceItemRepository.buscarTodos();

        sobremesaList.forEach(sobremesa -> sobremesaPersistenceItemRepository.deletarPorNome(sobremesa.getNome()));
        persistenceUsuarioRepository.deletarPorEmail(emailUsuario);
    }

    @Test
    void deveriaRetornarTodasAsSobremesas() throws Exception {
        Sobremesa sobremesa1 = new Sobremesa("sobremesa1", 10);
        Sobremesa sobremesa2 = new Sobremesa("sobremesa2", 5);
        Sobremesa sobremesa3 = new Sobremesa("sobremesa3", 15);

        sobremesaPersistenceItemRepository.salvar(sobremesa1);
        sobremesaPersistenceItemRepository.salvar(sobremesa2);
        sobremesaPersistenceItemRepository.salvar(sobremesa3);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/sobremesas")
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void deveriaCriarUmSobremesa() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, nome, senhaUsuario, cpf);
        Sobremesa sobremesa = new Sobremesa("sobremesa1", 10, "categoria");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/cadastro")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

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
                        .post("/sobremesas/criar")
                        .content(new Gson().toJson(sobremesa))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        Optional<Sobremesa> optionalSobremesa = sobremesaPersistenceItemRepository.buscarPorNome(sobremesa.getNome());

        Assertions.assertTrue(optionalSobremesa.isPresent());
        Assertions.assertEquals(sobremesa.getNome(), optionalSobremesa.get().getNome());
        Assertions.assertEquals(sobremesa.getPreco(), optionalSobremesa.get().getPreco());
        Assertions.assertEquals(sobremesa.getCategoria(), optionalSobremesa.get().getCategoria());
    }

    @Test
    void deveriaRetornarUmErroQuandoUmClienteTentarCriarUmSobremesa() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, nome, senhaUsuario, cpf);
        Sobremesa sobremesa = new Sobremesa("sobremesa1", 10, "categoria");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/cadastro")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/sobremesas/criar")
                        .content(new Gson().toJson(sobremesa))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        Optional<Sobremesa> optionalSobremesa = sobremesaPersistenceItemRepository.buscarPorNome(sobremesa.getNome());

        Assertions.assertTrue(optionalSobremesa.isEmpty());
    }

    @Test
    void deveriaAtualizarUmSobremesa() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, nome, senhaUsuario, cpf);
        double precoOriginal = 10;
        Sobremesa sobremesa = new Sobremesa("sobremesa1", precoOriginal, "categoria");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/cadastro")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

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
                .post("/sobremesas/criar")
                .content(new Gson().toJson(sobremesa))
                .contentType("application/json")
                .cookie(new Cookie("token", tokenDTO.getToken()))
                .accept(MediaType.APPLICATION_JSON));

        sobremesa.setPreco(50.0);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/sobremesas/atualizar")
                        .content(new Gson().toJson(sobremesa))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Sobremesa> optionalSobremesa = sobremesaPersistenceItemRepository.buscarPorNome(sobremesa.getNome());

        Assertions.assertTrue(optionalSobremesa.isPresent());
        Assertions.assertEquals(sobremesa.getNome(), optionalSobremesa.get().getNome());
        Assertions.assertEquals(sobremesa.getPreco(), optionalSobremesa.get().getPreco());
        Assertions.assertNotEquals(precoOriginal, optionalSobremesa.get().getPreco());
        Assertions.assertEquals(sobremesa.getCategoria(), optionalSobremesa.get().getCategoria());
    }

    @Test
    void deveriaRetornarUmErroQuandoUmClienteTentarAtualizarUmSobremesa() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, nome, senhaUsuario, cpf);
        double precoOriginal = 10;
        Sobremesa sobremesa = new Sobremesa("sobremesa1", precoOriginal, "categoria");
        sobremesaPersistenceItemRepository.salvar(sobremesa);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/cadastro")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);
        sobremesa.setPreco(50.0);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/sobremesas/atualizar")
                        .content(new Gson().toJson(sobremesa))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        Optional<Sobremesa> optionalSobremesa = sobremesaPersistenceItemRepository.buscarPorNome(sobremesa.getNome());

        Assertions.assertTrue(optionalSobremesa.isPresent());
        Assertions.assertEquals(precoOriginal, optionalSobremesa.get().getPreco());
    }

    @Test
    void deveriaDeletarUmSobremesa() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, nome, senhaUsuario, cpf);
        Sobremesa sobremesa = new Sobremesa("sobremesa1", 10, "categoria");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/cadastro")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

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
                        .post("/sobremesas/criar")
                        .content(new Gson().toJson(sobremesa))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/sobremesas/deletar/" + sobremesa.getNome())
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        Optional<Sobremesa> optionalSobremesa = sobremesaPersistenceItemRepository.buscarPorNome(sobremesa.getNome());

        Assertions.assertTrue(optionalSobremesa.isEmpty());
    }

    @Test
    void deveriaRetornarUmErroQuandoUmClienteTentarDeletarUmSobremesa() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, nome, senhaUsuario, cpf);
        Sobremesa sobremesa = new Sobremesa("sobremesa1", 10, "categoria");
        sobremesaPersistenceItemRepository.salvar(sobremesa);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/cadastro")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/sobremesas/criar")
                        .content(new Gson().toJson(sobremesa))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/sobremesas/deletar/" + sobremesa.getNome())
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        Optional<Sobremesa> optionalSobremesa = sobremesaPersistenceItemRepository.buscarPorNome(sobremesa.getNome());

        Assertions.assertTrue(optionalSobremesa.isPresent());
    }
}
