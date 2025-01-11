package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.google.gson.Gson;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.PedidoEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.UsuarioEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.repository.JpaPedidoRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.dto.*;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.StatusPedido;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.*;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.*;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PedidoControllerTest {

    @Autowired
    private PersistenceUsuarioRepository persistenceUsuarioRepository;
    @Autowired
    private JpaPedidoRepository persistencePedidoRepository;
    @Autowired
    private PersistenceItemRepository<Lanche> lanchePersistenceItemRepository;
    @Autowired
    private PersistenceItemRepository<Bebida> bebidaPersistenceItemRepository;
    @Autowired
    private PersistenceItemRepository<Acompanhamento> acompanhamentoPersistenceItemRepository;
    @Autowired
    private PersistenceItemRepository<Sobremesa> sobremesaPersistenceItemRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    String emailUsuario = "email@email.com";
    String senhaUsuario = "senha";

    @BeforeEach
    void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @AfterEach
    public void after() {
        persistencePedidoRepository.deleteAll();
        persistenceUsuarioRepository.deletarPorEmail(emailUsuario);
        lanchePersistenceItemRepository.deletarPorNome("lanche 1");
        bebidaPersistenceItemRepository.deletarPorNome("bebida 1");
        acompanhamentoPersistenceItemRepository.deletarPorNome("acompanhamento 1");
        sobremesaPersistenceItemRepository.deletarPorNome("sobremesa 1");
    }

    @Test
    void deveriaCriarUmPedido() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);

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

        result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos/criar")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("statusPedido").exists())
                .andExpect(jsonPath("statusPedido").value(StatusPedido.RECEBIDO.toString()))
                .andExpect(jsonPath("bebida").isEmpty())
                .andExpect(jsonPath("lanche").isEmpty())
                .andExpect(jsonPath("acompanhamento").isEmpty())
                .andExpect(jsonPath("sobremesa").isEmpty())
                .andReturn();

        PedidoEntity pedido = new Gson().fromJson(result.getResponse().getContentAsString(), PedidoEntity.class);
        Optional<Pedido> optionalPedido = persistencePedidoRepository.buscarPorId(pedido.getId());

        Assertions.assertTrue(optionalPedido.isPresent());
    }
    
    

    @Test
    void deveriaRetornarTodoOsPedidos() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);

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
                        .post("/pedidos/criar")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON));
             

         mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos/criar")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/pedidos/buscar/todos")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deveriaRetornarUmPedidoPorId() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);

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
                .post("/pedidos/criar")
                .content(new Gson().toJson(usuario))
                .contentType("application/json")
                .cookie(new Cookie("token", tokenDTO.getToken()))
                .accept(MediaType.APPLICATION_JSON));


        result = mockMvc.perform(MockMvcRequestBuilders
                .post("/pedidos/criar")
                .content(new Gson().toJson(usuario))
                .contentType("application/json")
                .cookie(new Cookie("token", tokenDTO.getToken()))
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        PedidoEntity pedido = new Gson().fromJson(result.getResponse().getContentAsString(), PedidoEntity.class);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/pedidos/buscar/" + pedido.getId())
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(pedido.getId()))
                .andExpect(jsonPath("usuario.email").value(pedido.getUsuario().getEmail()));
    }

    @Test
    void deveriaCriarUmPedidoComOUsuario() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/usuarios/cadastro")
                .content(new Gson().toJson(usuario))
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);

        result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos/criar")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("usuario.email").exists())
                .andExpect(jsonPath("usuario.email").value(usuario.getEmail()))
                .andReturn();

        PedidoEntity pedido = new Gson().fromJson(result.getResponse().getContentAsString(), PedidoEntity.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/pedidos/buscar/" + pedido.getId())
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        Optional<Pedido> optionalPedido = persistencePedidoRepository.buscarPorId(pedido.getId());

        Assertions.assertTrue(optionalPedido.isPresent());
        Assertions.assertEquals(usuario.getEmail(), optionalPedido.get().getUsuario().getEmail());
    }

    @Test
    void deveriaAdicionarUmLancheAoPedido() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        Lanche lanche = new Lanche("lanche 1", 10);
        lanchePersistenceItemRepository.salvar(lanche);
        LancheDTO lancheDTO = new LancheDTO(lanche.getNome());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/usuarios/cadastro")
                .content(new Gson().toJson(usuario))
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);

        result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos/criar")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("usuario.email").exists())
                .andExpect(jsonPath("usuario.email").value(usuario.getEmail()))
                .andReturn();

        PedidoEntity pedido = new Gson().fromJson(result.getResponse().getContentAsString(), PedidoEntity.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos/adicionar/lanche/" + pedido.getId())
                        .content(new Gson().toJson(lancheDTO))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("lanche").exists())
                .andExpect(jsonPath("lanche.nome").value(lanche.getNome()));

    }

    @Test
    void deveriaAdicionarUmaBebidaeAoPedido() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        Bebida bebida = new Bebida("bebida 1", 10);
        bebidaPersistenceItemRepository.salvar(bebida);
        BebidaDTO bebidaDTO = new BebidaDTO(bebida.getNome());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/usuarios/cadastro")
                .content(new Gson().toJson(usuario))
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);

        result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos/criar")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("usuario.email").exists())
                .andExpect(jsonPath("usuario.email").value(usuario.getEmail()))
                .andReturn();

        PedidoEntity pedido = new Gson().fromJson(result.getResponse().getContentAsString(), PedidoEntity.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos/adicionar/bebida/" + pedido.getId())
                        .content(new Gson().toJson(bebidaDTO))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("bebida").exists())
                .andExpect(jsonPath("bebida.nome").value(bebida.getNome()));

    }

    @Test
    void deveriaAdicionarUmAcompanhamentoAoPedido() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        Acompanhamento acompanhamento = new Acompanhamento("acompanhamento 1", 10);
        acompanhamentoPersistenceItemRepository.salvar(acompanhamento);
        AcompanhamentoDTO acompanhamentoDTO = new AcompanhamentoDTO(acompanhamento.getNome());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/usuarios/cadastro")
                .content(new Gson().toJson(usuario))
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);

        result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos/criar")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("usuario.email").exists())
                .andExpect(jsonPath("usuario.email").value(usuario.getEmail()))
                .andReturn();

        PedidoEntity pedido = new Gson().fromJson(result.getResponse().getContentAsString(), PedidoEntity.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos/adicionar/acompanhamento/" + pedido.getId())
                        .content(new Gson().toJson(acompanhamentoDTO))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("acompanhamento").exists())
                .andExpect(jsonPath("acompanhamento.nome").value(acompanhamento.getNome()));
    }

    @Test
    void deveriaAdicionarUmaSobremesaAoPedido() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        Sobremesa sobremesa = new Sobremesa("sobremesa 1", 10);
        sobremesaPersistenceItemRepository.salvar(sobremesa);
        SobremesaDTO sobremesaDTO = new SobremesaDTO(sobremesa.getNome());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/usuarios/cadastro")
                .content(new Gson().toJson(usuario))
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);

        result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos/criar")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("usuario.email").exists())
                .andExpect(jsonPath("usuario.email").value(usuario.getEmail()))
                .andReturn();

        PedidoEntity pedido = new Gson().fromJson(result.getResponse().getContentAsString(), PedidoEntity.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos/adicionar/sobremesa/" + pedido.getId())
                        .content(new Gson().toJson(sobremesaDTO))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("sobremesa").exists())
                .andExpect(jsonPath("sobremesa.nome").value(sobremesa.getNome()));
    }

    @Test
    void deveriaAdicionarTodosOsItensAoPedido() throws Exception {
        Usuario usuario = new Usuario(emailUsuario, senhaUsuario);
        MockHttpSession session = new MockHttpSession();

        Lanche lanche = new Lanche("lanche 1", 10);
        lanchePersistenceItemRepository.salvar(lanche);
        LancheDTO lancheDTO = new LancheDTO(lanche.getNome());

        Bebida bebida = new Bebida("bebida 1", 10);
        bebidaPersistenceItemRepository.salvar(bebida);
        BebidaDTO bebidaDTO = new BebidaDTO(bebida.getNome());

        Acompanhamento acompanhamento = new Acompanhamento("acompanhamento 1", 10);
        acompanhamentoPersistenceItemRepository.salvar(acompanhamento);
        AcompanhamentoDTO acompanhamentoDTO = new AcompanhamentoDTO(acompanhamento.getNome());

        Sobremesa sobremesa = new Sobremesa("sobremesa 1", 10);
        sobremesaPersistenceItemRepository.salvar(sobremesa);
        SobremesaDTO sobremesaDTO = new SobremesaDTO(sobremesa.getNome());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/usuarios/cadastro")
                .content(new Gson().toJson(usuario))
                .session(session)
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios/login")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .session(session)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        TokenDTO tokenDTO = new Gson().fromJson(result.getResponse().getContentAsString(), TokenDTO.class);

        result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos/criar")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .session(session)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("usuario.email").exists())
                .andExpect(jsonPath("usuario.email").value(usuario.getEmail()))
                .andReturn();

        PedidoEntity pedidoEntity = new Gson().fromJson(result.getResponse().getContentAsString(), PedidoEntity.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos/adicionar/lanche/" + pedidoEntity.getId())
                        .content(new Gson().toJson(lancheDTO))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .session(session)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos/adicionar/bebida/" + pedidoEntity.getId())
                        .content(new Gson().toJson(bebidaDTO))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .session(session)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos/adicionar/acompanhamento/" + pedidoEntity.getId())
                        .content(new Gson().toJson(acompanhamentoDTO))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .session(session)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos/adicionar/sobremesa/" + pedidoEntity.getId())
                        .content(new Gson().toJson(sobremesaDTO))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .session(session)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("lanche").exists())
                .andExpect(jsonPath("lanche.nome").value(lanche.getNome()))
                .andExpect(jsonPath("bebida").exists())
                .andExpect(jsonPath("bebida.nome").value(bebida.getNome()))
                .andExpect(jsonPath("acompanhamento").exists())
                .andExpect(jsonPath("acompanhamento.nome").value(acompanhamento.getNome()))
                .andExpect(jsonPath("sobremesa").exists())
                .andExpect(jsonPath("sobremesa.nome").value(sobremesa.getNome()));

        Optional<Pedido> optionalPedido = persistencePedidoRepository.buscarPorId(pedidoEntity.getId());

        Assertions.assertTrue(optionalPedido.isPresent());
        Pedido pedido = optionalPedido.get();
        Assertions.assertNotNull(pedido.getLanche());
        Assertions.assertEquals(lanche.getNome(), pedido.getLanche().get().getNome());
        Assertions.assertNotNull(pedido.getBebida());
        Assertions.assertEquals(bebida.getNome(), pedido.getBebida().get().getNome());
        Assertions.assertNotNull(pedido.getAcompanhamento());
        Assertions.assertEquals(acompanhamento.getNome(), pedido.getAcompanhamento().get().getNome());
        Assertions.assertNotNull(pedido.getSobremesa());
        Assertions.assertEquals(sobremesa.getNome(), pedido.getSobremesa().get().getNome());
    }

}
