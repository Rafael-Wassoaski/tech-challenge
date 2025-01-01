package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.AcompanhamentoService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.BebidaService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.LancheService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.SobremesaService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.StatusPedido;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.*;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceItemForTests;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistencePedidoForTests;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceUsuarioForTests;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PedidoControllerTest {

    private MapPersistenceUsuarioForTests mapPersistenceUsuarioForTests;
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

    private com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService pedidoService;

    private PedidoController pedidoController;

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
    }

    @Test
    void deveriaIniciarUmPedidoComUmUsuarioCadastrado() {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Usuario usuario = new Usuario("email@email.com", "senha");
        mapPersistenceUsuarioForTests.salvar(usuario);

        pedidoController = new PedidoController(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistenceUsuarioForTests,
                mapPersistencePedidoForTests);

        Pedido pedido = pedidoController.criarPedido(usuario);

        Assertions.assertNotNull(pedido.getId());
        Assertions.assertEquals(StatusPedido.RECEBIDO, pedido.getStatusPedido());
        Assertions.assertTrue(pedido.getLanche().isEmpty());
        Assertions.assertTrue(pedido.getAcompanhamento().isEmpty());
        Assertions.assertTrue(pedido.getSobremesa().isEmpty());
        Assertions.assertTrue(pedido.getLanche().isEmpty());
        Assertions.assertTrue(pedido.getLanche().isEmpty());
        Assertions.assertEquals(usuario.getEmail(), pedido.getUsuario().getEmail());
    }

    @Test
    void deveriaAdicionarOLancheAoPedidoIniciado() {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Usuario usuario = new Usuario("email@email.com", "senha");
        mapPersistenceUsuarioForTests.salvar(usuario);

        pedidoController = new PedidoController(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistenceUsuarioForTests,
                mapPersistencePedidoForTests);

        Pedido pedido = pedidoController.criarPedido(usuario);

        pedido = pedidoController.adicionarLanche(pedido, nomeLanche);
        Assertions.assertTrue(pedido.getLanche().isPresent());
        Assertions.assertEquals(nomeLanche, pedido.getLanche().get().getNome());
    }

    @Test
    void deveriaAdicionarABebidaAoPedidoIniciado() {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Usuario usuario = new Usuario("email@email.com", "senha");
        mapPersistenceUsuarioForTests.salvar(usuario);

        pedidoController = new PedidoController(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistenceUsuarioForTests,
                mapPersistencePedidoForTests);

        Pedido pedido = pedidoController.criarPedido(usuario);

        pedido = pedidoController.adicionarBebida(pedido, nomeBebida);
        Assertions.assertTrue(pedido.getBebida().isPresent());
        Assertions.assertEquals(nomeBebida, pedido.getBebida().get().getNome());
    }

    @Test
    void deveriaAdicionarOAcompanhamentoAoPedidoIniciado() {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Usuario usuario = new Usuario("email@email.com", "senha");
        mapPersistenceUsuarioForTests.salvar(usuario);

        pedidoController = new PedidoController(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistenceUsuarioForTests,
                mapPersistencePedidoForTests);

        Pedido pedido = pedidoController.criarPedido(usuario);

        pedido = pedidoController.adicionarAcompanhamento(pedido, nomeAcompanhamento);
        Assertions.assertTrue(pedido.getAcompanhamento().isPresent());
        Assertions.assertEquals(nomeAcompanhamento, pedido.getAcompanhamento().get().getNome());
    }

    @Test
    void deveriaAdicionarASobremesaAoPedidoIniciado() {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Usuario usuario = new Usuario("email@email.com", "senha");
        mapPersistenceUsuarioForTests.salvar(usuario);

        pedidoController = new PedidoController(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistenceUsuarioForTests,
                mapPersistencePedidoForTests);

        Pedido pedido = pedidoController.criarPedido(usuario);

        pedido = pedidoController.adicionarSobremesa(pedido, nomeSobremesa);
        Assertions.assertTrue(pedido.getSobremesa().isPresent());
        Assertions.assertEquals(nomeSobremesa, pedido.getSobremesa().get().getNome());
    }

    @Test
    void deveriaIniciarUmPedidoComTodosOsItens() {
        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Usuario usuario = new Usuario("email@email.com", "senha");
        mapPersistenceUsuarioForTests.salvar(usuario);

        pedidoController = new PedidoController(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistenceUsuarioForTests,
                mapPersistencePedidoForTests);

        Pedido pedido = pedidoController.criarPedido(usuario);

        pedidoController.adicionarLanche(pedido, nomeLanche);
        pedidoController.adicionarBebida(pedido, nomeBebida);
        pedidoController.adicionarSobremesa(pedido, nomeSobremesa);
        pedidoController.adicionarAcompanhamento(pedido, nomeAcompanhamento);

        pedido = pedidoController.prepararPedido(pedido);

        Assertions.assertTrue(pedido.getLanche().isPresent());
        Assertions.assertEquals(nomeLanche, pedido.getLanche().get().getNome());

        Assertions.assertTrue(pedido.getBebida().isPresent());
        Assertions.assertEquals(nomeBebida, pedido.getBebida().get().getNome());

        Assertions.assertTrue(pedido.getAcompanhamento().isPresent());
        Assertions.assertEquals(nomeAcompanhamento, pedido.getAcompanhamento().get().getNome());

        Assertions.assertTrue(pedido.getSobremesa().isPresent());
        Assertions.assertEquals(nomeSobremesa, pedido.getSobremesa().get().getNome());

        Assertions.assertEquals(StatusPedido.EM_PREPARACAO , pedido.getStatusPedido());
    }
}
