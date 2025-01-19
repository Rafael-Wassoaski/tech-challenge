package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.google.gson.Gson;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.dto.TokenDTO;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Bebida;
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
public class BebidaControllerTest {

    @Autowired
    private PersistenceItemRepository<Bebida> bebidaPersistenceItemRepository;
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
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void afterEach() {
        List<Bebida> bebidaList = bebidaPersistenceItemRepository.buscarTodos();

        bebidaList.forEach(bebida -> bebidaPersistenceItemRepository.deletarPorNome(bebida.getNome()));
        persistenceUsuarioRepository.deletarPorEmail(emailUsuario);
    }

    @Test
    void deveriaRetornarTodasAsBebidas() throws Exception {
        Bebida bebiba1 = new Bebida("bebida1", 10);
        Bebida bebiba2 = new Bebida("bebida2", 5);
        Bebida bebiba3 = new Bebida("bebida3", 15);

        bebidaPersistenceItemRepository.salvar(bebiba1);
        bebidaPersistenceItemRepository.salvar(bebiba2);
        bebidaPersistenceItemRepository.salvar(bebiba3);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/bebidas")
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }


    @Test
    void deveriaCriarUmaBebida() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        Bebida bebida = new Bebida("bebida1", 10, "categoria");
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);

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
                        .post("/bebidas/criar")
                        .content(new Gson().toJson(bebida))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        Optional<Bebida> optionalBebida = bebidaPersistenceItemRepository.buscarPorNome(bebida.getNome());

        Assertions.assertTrue(optionalBebida.isPresent());
        Assertions.assertEquals(bebida.getNome(), optionalBebida.get().getNome());
        Assertions.assertEquals(bebida.getPreco(), optionalBebida.get().getPreco());
        Assertions.assertEquals(bebida.getCategoria(), optionalBebida.get().getCategoria());
    }

    @Test
    void deveriaRetornarUmErroQuandoUmClienteTentarCriarUmaBebida() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        Bebida bebida = new Bebida("bebida1", 10, "categoria");
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/bebidas/criar")
                        .content(new Gson().toJson(bebida))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        Optional<Bebida> optionalBebida = bebidaPersistenceItemRepository.buscarPorNome(bebida.getNome());

        Assertions.assertTrue(optionalBebida.isEmpty());
    }

    @Test
    void deveriaAtualizarUmaBebida() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        double precoOriginal = 10;
        Bebida bebida = new Bebida("bebida1", precoOriginal, "categoria");
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);

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
                .post("/bebidas/criar")
                .content(new Gson().toJson(bebida))
                .contentType("application/json")
                .cookie(new Cookie("token", tokenDTO.getToken()))
                .accept(MediaType.APPLICATION_JSON));

        bebida.setPreco(50.0);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/bebidas/atualizar")
                        .content(new Gson().toJson(bebida))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Bebida> optionalBebida = bebidaPersistenceItemRepository.buscarPorNome(bebida.getNome());

        Assertions.assertTrue(optionalBebida.isPresent());
        Assertions.assertEquals(bebida.getNome(), optionalBebida.get().getNome());
        Assertions.assertEquals(bebida.getPreco(), optionalBebida.get().getPreco());
        Assertions.assertNotEquals(precoOriginal, optionalBebida.get().getPreco());
        Assertions.assertEquals(bebida.getCategoria(), optionalBebida.get().getCategoria());
    }

    @Test
    void deveriaRetornarUmErroQuandoUmClienteTentarAtualizarUmaBebida() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);
        double precoOriginal = 10;
        Bebida bebida = new Bebida("bebida1", precoOriginal, "categoria");
        bebidaPersistenceItemRepository.salvar(bebida);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);
        bebida.setPreco(50.0);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/bebidas/atualizar")
                        .content(new Gson().toJson(bebida))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        Optional<Bebida> optionalBebida = bebidaPersistenceItemRepository.buscarPorNome(bebida.getNome());

        Assertions.assertTrue(optionalBebida.isPresent());
        Assertions.assertEquals(precoOriginal, optionalBebida.get().getPreco());
    }

    @Test
    void deveriaDeletarUmaBebida() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        Bebida bebida = new Bebida("bebida1", 10, "categoria");
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);

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
                        .post("/bebidas/criar")
                        .content(new Gson().toJson(bebida))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/bebidas/deletar/" + bebida.getNome())
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        Optional<Bebida> optionalBebida = bebidaPersistenceItemRepository.buscarPorNome(bebida.getNome());

        Assertions.assertTrue(optionalBebida.isEmpty());
    }

    @Test
    void deveriaRetornarUmErroQuandoUmClienteTentarDeletarUmaBebida() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        Bebida bebida = new Bebida("bebida1", 10, "categoria");
        bebidaPersistenceItemRepository.salvar(bebida);
        UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, new Encriptador(sal));
        usuarioService.criar(usuario);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/bebidas/criar")
                        .content(new Gson().toJson(bebida))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/bebidas/deletar/" + bebida.getNome())
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        Optional<Bebida> optionalBebida = bebidaPersistenceItemRepository.buscarPorNome(bebida.getNome());

        Assertions.assertTrue(optionalBebida.isPresent());
    }
}
