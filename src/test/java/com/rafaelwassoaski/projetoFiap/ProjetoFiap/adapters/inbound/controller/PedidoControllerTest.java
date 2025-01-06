package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.google.gson.Gson;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.dto.TokenDTO;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.AcompanhamentoService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.BebidaService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.LancheService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.SobremesaService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.StatusPedido;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.*;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.*;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.JWTService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PedidoControllerTest {

    private MapPersistenceUsuarioForTests mapPersistenceUsuarioForTests;
    @Autowired
    private PersistenceUsuarioRepository persistenceUsuarioRepository;
    private String nomeLanche = "Lanche 1";
    private String nomeBebida = "Bebida 1";
    private String nomeAcompanhamento = "Acompanhamento 1";
    private String nomeSobremesa = "Sobremesa 1";

    private com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.BebidaService BebidaService;
    private LancheService lancheService;
    private AcompanhamentoService acompanhamentoService;
    private SobremesaService sobremesaService;

    private PersistenceItemRepository<Lanche> lanchePersistenceItemRepository;
    private PersistenceItemRepository<Bebida> bebidaPersistenceItemRepository;
    private PersistenceItemRepository<Acompanhamento> acompanhamentoPersistenceItemRepository;
    private PersistenceItemRepository<Sobremesa> sobremesaPersistenceItemRepository;
    private MapPersistencePedidoForTests mapPersistencePedidoForTests;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService pedidoService;

    private PedidoController pedidoController;
    String emailUsuario = "email@email.com";
    String senhaUsuario = "senha";

    @BeforeEach
    void setup() throws Exception {
        Lanche lanche = new Lanche(nomeLanche, 10);
        Bebida bebida = new Bebida(nomeBebida, 10);
        Acompanhamento acompanhamento = new Acompanhamento(nomeAcompanhamento, 10);
        Sobremesa sobremesa = new Sobremesa(nomeSobremesa, 10);

        lanchePersistenceItemRepository = new MapPersistenceItemForTests();
        bebidaPersistenceItemRepository = new MapPersistenceItemForTests();
        acompanhamentoPersistenceItemRepository = new MapPersistenceItemForTests();
        sobremesaPersistenceItemRepository = new MapPersistenceItemForTests();
        mapPersistencePedidoForTests = new MapPersistencePedidoForTests();

        BebidaService = new BebidaService(bebidaPersistenceItemRepository);
        lancheService = new LancheService(lanchePersistenceItemRepository);
        acompanhamentoService = new AcompanhamentoService(acompanhamentoPersistenceItemRepository);
        sobremesaService = new SobremesaService(sobremesaPersistenceItemRepository);

        BebidaService.criar(bebida);
        lancheService.criar(lanche);
        acompanhamentoService.criar(acompanhamento);
        sobremesaService.criar(sobremesa);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }


    @AfterEach
    public void after() {
        persistenceUsuarioRepository.deletarPorEmail(emailUsuario);
    }

    @Test
    void deveriaRetornarTodosOsLanches() throws Exception {
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

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos/criar")
                        .content(new Gson().toJson(usuario))
                        .contentType("application/json")
                        .cookie(new Cookie("token", tokenDTO.getToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());


    }

//    @Test
//    void deveriaIniciarUmPedidoComTodosOsItens() {
//        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
//        Usuario usuario = new Usuario("email@email.com", "senha");
//        mapPersistenceUsuarioForTests.salvar(usuario);
//
//        pedidoController = new PedidoController(lanchePersistenceItemRepository,
//                bebidaPersistenceItemRepository,
//                acompanhamentoPersistenceItemRepository,
//                sobremesaPersistenceItemRepository,
//                mapPersistenceUsuarioForTests,
//                mapPersistencePedidoForTests,
//                new JWTService());
//
//        Pedido pedido = pedidoController.criarPedido(usuario);
//
//        pedidoController.adicionarLanche(pedido, nomeLanche);
//        pedidoController.adicionarBebida(pedido, nomeBebida);
//        pedidoController.adicionarSobremesa(pedido, nomeSobremesa);
//        pedidoController.adicionarAcompanhamento(pedido, nomeAcompanhamento);
//
//        pedido = pedidoController.prepararPedido(pedido);
//
//        Assertions.assertTrue(pedido.getLanche().isPresent());
//        Assertions.assertEquals(nomeLanche, pedido.getLanche().get().getNome());
//
//        Assertions.assertTrue(pedido.getBebida().isPresent());
//        Assertions.assertEquals(nomeBebida, pedido.getBebida().get().getNome());
//
//        Assertions.assertTrue(pedido.getAcompanhamento().isPresent());
//        Assertions.assertEquals(nomeAcompanhamento, pedido.getAcompanhamento().get().getNome());
//
//        Assertions.assertTrue(pedido.getSobremesa().isPresent());
//        Assertions.assertEquals(nomeSobremesa, pedido.getSobremesa().get().getNome());
//
//        Assertions.assertEquals(StatusPedido.EM_PREPARACAO , pedido.getStatusPedido());
//    }
}
