package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.google.gson.Gson;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.dto.TokenDTO;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Lanche;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Pedido;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
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
public class LancheControllerTest {

    @Autowired
    private PersistenceItemRepository<Lanche> lanchePersistenceItemRepository;
    @Autowired
    private PersistenceUsuarioRepository persistenceUsuarioRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private String emailUsuario = "email@email.com";
    private String senhaUsuario = "senha";
    private String cpf = "000.000.000-00";
    private String nome = "teste";
    @Value("${custom.sal}")
    private String sal;


    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void afterEach(){
        List<Lanche> lanches = lanchePersistenceItemRepository.buscarTodos();

        lanches.forEach(lanche -> lanchePersistenceItemRepository.deletarPorNome(lanche.getNome()));
        persistenceUsuarioRepository.deletarPorEmail(emailUsuario);
    }

    @Test
    void deveriaRetornarTodosOsLanches() throws Exception {
        Lanche lanche1 = new Lanche("lanche1", 10);
        Lanche lanche2 = new Lanche("lanche2", 5);
        Lanche lanche3 = new Lanche("lanche3", 15);

        lanchePersistenceItemRepository.salvar(lanche1);
        lanchePersistenceItemRepository.salvar(lanche2);
        lanchePersistenceItemRepository.salvar(lanche3);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/lanches")
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void deveriaCriarUmLanche() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);
        Lanche lanche = new Lanche("lanche1", 10, "categoria");

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
                        .post("/lanches/criar")
                        .content(new Gson().toJson(lanche))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

       Optional<Lanche> optionalLanche = lanchePersistenceItemRepository.buscarPorNome(lanche.getNome());

        Assertions.assertTrue(optionalLanche.isPresent());
        Assertions.assertEquals(lanche.getNome(), optionalLanche.get().getNome());
        Assertions.assertEquals(lanche.getPreco(), optionalLanche.get().getPreco());
        Assertions.assertEquals(lanche.getCategoria(), optionalLanche.get().getCategoria());
    }

    @Test
    void deveriaRetornarUmErroQuandoUmClienteTentarCriarUmLanche() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);
        Lanche lanche = new Lanche("lanche1", 10, "categoria");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/lanches/criar")
                        .content(new Gson().toJson(lanche))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        Optional<Lanche> optionalLanche = lanchePersistenceItemRepository.buscarPorNome(lanche.getNome());

        Assertions.assertTrue(optionalLanche.isEmpty());
    }

    @Test
    void deveriaAtualizarUmLanche() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);
        double precoOriginal = 10;
        Lanche lanche = new Lanche("lanche1", precoOriginal, "categoria");

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
                        .post("/lanches/criar")
                        .content(new Gson().toJson(lanche))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON));

        lanche.setPreco(50.0);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/lanches/atualizar")
                        .content(new Gson().toJson(lanche))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Lanche> optionalLanche = lanchePersistenceItemRepository.buscarPorNome(lanche.getNome());

        Assertions.assertTrue(optionalLanche.isPresent());
        Assertions.assertEquals(lanche.getNome(), optionalLanche.get().getNome());
        Assertions.assertEquals(lanche.getPreco(), optionalLanche.get().getPreco());
        Assertions.assertNotEquals(precoOriginal, optionalLanche.get().getPreco());
        Assertions.assertEquals(lanche.getCategoria(), optionalLanche.get().getCategoria());
    }

    @Test
    void deveriaRetornarUmErroQuandoUmClienteTentarAtualizarUmLanche() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);
        double precoOriginal = 10;
        Lanche lanche = new Lanche("lanche1", precoOriginal, "categoria");
        lanchePersistenceItemRepository.salvar(lanche);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);
        lanche.setPreco(50.0);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/lanches/atualizar")
                        .content(new Gson().toJson(lanche))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        Optional<Lanche> optionalLanche = lanchePersistenceItemRepository.buscarPorNome(lanche.getNome());

        Assertions.assertTrue(optionalLanche.isPresent());
        Assertions.assertEquals(precoOriginal, optionalLanche.get().getPreco());
    }

    @Test
    void deveriaDeletarUmLanche() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);
        Lanche lanche = new Lanche("lanche1", 10, "categoria");

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
                        .post("/lanches/criar")
                        .content(new Gson().toJson(lanche))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/lanches/deletar/" + lanche.getNome())
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        Optional<Lanche> optionalLanche = lanchePersistenceItemRepository.buscarPorNome(lanche.getNome());

        Assertions.assertTrue(optionalLanche.isEmpty());
    }

    @Test
    void deveriaRetornarUmErroQuandoUmClienteTentarDeletarUmLanche() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);
        Lanche lanche = new Lanche("lanche1", 10, "categoria");
        lanchePersistenceItemRepository.salvar(lanche);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/lanches/criar")
                        .content(new Gson().toJson(lanche))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/lanches/deletar/" + lanche.getNome())
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        Optional<Lanche> optionalLanche = lanchePersistenceItemRepository.buscarPorNome(lanche.getNome());

        Assertions.assertTrue(optionalLanche.isPresent());
    }
}
