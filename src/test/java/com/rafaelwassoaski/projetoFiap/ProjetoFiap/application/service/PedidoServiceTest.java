package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.StatusPedido;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.*;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceItemForTests;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistencePedidoForTests;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceUsuarioForTests;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.Encriptador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class PedidoServiceTest {

    private String nomeLanche = "Lanche 1";
    private String nomeBebida = "Bebida 1";
    private String nomeAcompanhamento = "Acompanhamento 1";
    private String nomeSobremesa = "Sobremesa 1";

    private BebidaService BebidaService;
    private LancheService lancheService;
    private AcompanhamentoService acompanhamentoService;
    private SobremesaService sobremesaService;

    private PersistenceItemRepository<Lanche> lanchePersistenceItemRepository;
    private PersistenceItemRepository<Bebida> bebidaPersistenceItemRepository;
    private PersistenceItemRepository<Acompanhamento> acompanhamentoPersistenceItemRepository;
    private PersistenceItemRepository<Sobremesa> sobremesaPersistenceItemRepository;
    private MapPersistencePedidoForTests mapPersistencePedidoForTests;

    private com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService pedidoService;

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
    void deveriaCriarUmPedidoComONomeDosItens() throws Exception {
        pedidoService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistencePedidoForTests);
        Usuario usuario = new Usuario("teste@test.com", "1234567", "000.000.000.00");
        Pedido pedido = pedidoService.criarPedido(usuario);
        int pedidoId = pedido.getId();

        pedidoService.definirLanche(nomeLanche, pedidoId);
        pedidoService.definirBebida(nomeBebida, pedidoId);
        pedidoService.definirAcompanhamento(nomeAcompanhamento, pedidoId);
        pedidoService.definirSobremesa(nomeSobremesa, pedidoId);

        Assertions.assertEquals(nomeLanche, pedido.getLanche().get().getNome());
        Assertions.assertEquals(nomeBebida, pedido.getBebida().get().getNome());
        Assertions.assertEquals(nomeAcompanhamento, pedido.getAcompanhamento().get().getNome());
        Assertions.assertEquals(nomeSobremesa, pedido.getSobremesa().get().getNome());
    }

    @Test
    void deveriaCriarUmPedidoApenasComOLanche() throws Exception {
        pedidoService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistencePedidoForTests);
        Usuario usuario = new Usuario("teste@test.com", "1234567", "000.000.000.00");
        Pedido pedido = pedidoService.criarPedido(usuario);
        int pedidoId = pedido.getId();

        pedidoService.definirLanche(nomeLanche, pedidoId);
        pedidoService.definirBebida(null, pedidoId);
        pedidoService.definirAcompanhamento(null, pedidoId);
        pedidoService.definirSobremesa(null, pedidoId);

        Optional<Pedido> optionalPedido = mapPersistencePedidoForTests.buscarPorId(pedidoId);
        pedido = optionalPedido.get();

        Assertions.assertEquals(nomeLanche, pedido.getLanche().get().getNome());
        Assertions.assertTrue(pedido.getBebida().isEmpty());
        Assertions.assertTrue(pedido.getAcompanhamento().isEmpty());
        Assertions.assertTrue(pedido.getSobremesa().isEmpty());
    }

    @Test
    void deveriaCriarUmPedidoApenasComABebida() throws Exception {
        pedidoService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistencePedidoForTests);
        Usuario usuario = new Usuario("teste@test.com", "1234567", "000.000.000.00");
        Pedido pedido = pedidoService.criarPedido(usuario);
        int pedidoId = pedido.getId();

        pedidoService.definirLanche(null, pedidoId);
        pedidoService.definirBebida(nomeBebida, pedidoId);
        pedidoService.definirAcompanhamento(null, pedidoId);
        pedidoService.definirSobremesa(null, pedidoId);

        Optional<Pedido> optionalPedido = mapPersistencePedidoForTests.buscarPorId(pedidoId);
        pedido = optionalPedido.get();

        Assertions.assertEquals(nomeBebida, pedido.getBebida().get().getNome());
        Assertions.assertTrue(pedido.getLanche().isEmpty());
        Assertions.assertTrue(pedido.getAcompanhamento().isEmpty());
        Assertions.assertTrue(pedido.getSobremesa().isEmpty());
    }

    @Test
    void deveriaCriarUmPedidoApenasComOAcompanhamento() throws Exception {
        pedidoService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistencePedidoForTests);
        Usuario usuario = new Usuario("teste@test.com", "1234567", "000.000.000.00");
        Pedido pedido = pedidoService.criarPedido(usuario);
        int pedidoId = pedido.getId();

        pedidoService.definirLanche(null, pedidoId);
        pedidoService.definirBebida(null, pedidoId);
        pedidoService.definirAcompanhamento(nomeAcompanhamento, pedidoId);
        pedidoService.definirSobremesa(null, pedidoId);

        Optional<Pedido> optionalPedido = mapPersistencePedidoForTests.buscarPorId(pedidoId);
        pedido = optionalPedido.get();

        Assertions.assertEquals(nomeAcompanhamento, pedido.getAcompanhamento().get().getNome());
        Assertions.assertTrue(pedido.getLanche().isEmpty());
        Assertions.assertTrue(pedido.getBebida().isEmpty());
        Assertions.assertTrue(pedido.getSobremesa().isEmpty());
    }

    @Test
    void deveriaCriarUmPedidoApenasComASobremesa() throws Exception {
        pedidoService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistencePedidoForTests);
        Usuario usuario = new Usuario("teste@test.com", "1234567", "000.000.000.00");
        Pedido pedido = pedidoService.criarPedido(usuario);
        int pedidoId = pedido.getId();

        pedidoService.definirLanche(null, pedidoId);
        pedidoService.definirBebida(null, pedidoId);
        pedidoService.definirAcompanhamento(null, pedidoId);
        pedidoService.definirSobremesa(nomeSobremesa, pedidoId);

        Optional<Pedido> optionalPedido = mapPersistencePedidoForTests.buscarPorId(pedidoId);
        pedido = optionalPedido.get();

        Assertions.assertEquals(nomeSobremesa, pedido.getSobremesa().get().getNome());
        Assertions.assertTrue(pedido.getLanche().isEmpty());
        Assertions.assertTrue(pedido.getBebida().isEmpty());
        Assertions.assertTrue(pedido.getAcompanhamento().isEmpty());
    }

    @Test
    void deveriaCriarUmPedidoAtreladoAUmUsuario() throws Exception {
        pedidoService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistencePedidoForTests);
        Usuario usuario = new Usuario("teste@test.com", "1234567", "000.000.000.00");
        Pedido pedido = pedidoService.criarPedido(usuario);
        int pedidoId = pedido.getId();

        pedidoService.definirLanche(nomeLanche, pedidoId);
        pedidoService.definirBebida(nomeBebida, pedidoId);
        pedidoService.definirAcompanhamento(nomeAcompanhamento, pedidoId);
        pedidoService.definirSobremesa(nomeSobremesa, pedidoId);

        Optional<Pedido> optionalPedido = mapPersistencePedidoForTests.buscarPorId(pedidoId);

        Assertions.assertTrue(optionalPedido.isPresent());
        pedido = optionalPedido.get();
        Assertions.assertEquals(nomeLanche, pedido.getLanche().get().getNome());
        Assertions.assertEquals(nomeBebida, pedido.getBebida().get().getNome());
        Assertions.assertEquals(nomeAcompanhamento, pedido.getAcompanhamento().get().getNome());
        Assertions.assertEquals(nomeSobremesa, pedido.getSobremesa().get().getNome());
        Assertions.assertEquals(usuario.getEmail(), pedido.getUsuario().getEmail());
    }

    @Test
    void deveriaAtualizarOStatusDeUmPedidoparaEmPreparacao() throws Exception {
        pedidoService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistencePedidoForTests);
        Usuario usuario = new Usuario("teste@test.com", "1234567", "000.000.000.00");
        Pedido pedido = pedidoService.criarPedido(usuario);
        int pedidoId = pedido.getId();

        pedidoService.definirLanche(nomeLanche, pedidoId);
        pedidoService.definirBebida(nomeBebida, pedidoId);
        pedidoService.definirAcompanhamento(nomeAcompanhamento, pedidoId);
        pedidoService.definirSobremesa(nomeSobremesa, pedidoId);

        pedido = pedidoService.criarPedido(usuario);
        Pedido pedidoAtualizado = pedidoService.prepararPedido(pedido.getId());

        Assertions.assertEquals(StatusPedido.EM_PREPARACAO, pedidoAtualizado.getStatusPedido());
    }

    @Test
    void deveriaAtualizarOStatusDeUmPedidoParaPronto() throws Exception {
        pedidoService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistencePedidoForTests);
        Usuario usuario = new Usuario("teste@test.com", "1234567", "000.000.000.00");
        Pedido pedido = pedidoService.criarPedido(usuario);
        int pedidoId = pedido.getId();

        pedidoService.definirLanche(nomeLanche, pedidoId);
        pedidoService.definirBebida(nomeBebida, pedidoId);
        pedidoService.definirAcompanhamento(nomeAcompanhamento, pedidoId);
        pedidoService.definirSobremesa(nomeSobremesa, pedidoId);

        pedido = pedidoService.criarPedido(usuario);
        pedidoService.prepararPedido(pedido.getId());
        Pedido pedidoAtualizado = pedidoService.finalizarPreparacaoDoPedido(pedido.getId());

        Assertions.assertEquals(StatusPedido.PRONTO, pedidoAtualizado.getStatusPedido());
    }

    @Test
    void deveriaAtualizarOStatusDeUmPedidoParaRetirado() throws Exception {
        pedidoService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistencePedidoForTests);
        Usuario usuario = new Usuario("teste@test.com", "1234567", "000.000.000.00");
        Pedido pedido = pedidoService.criarPedido(usuario);
        int pedidoId = pedido.getId();

        pedidoService.definirLanche(nomeLanche, pedidoId);
        pedidoService.definirBebida(nomeBebida, pedidoId);
        pedidoService.definirAcompanhamento(nomeAcompanhamento, pedidoId);
        pedidoService.definirSobremesa(nomeSobremesa, pedidoId);

        pedido = pedidoService.criarPedido(usuario);
        pedidoService.prepararPedido(pedido.getId());
        pedidoService.finalizarPreparacaoDoPedido(pedido.getId());
        Pedido pedidoAtualizado = pedidoService.retirarPedido(pedido.getId());

        Assertions.assertEquals(StatusPedido.RETIRADO, pedidoAtualizado.getStatusPedido());
    }

    @Test
    void deveriaBuscarTodosOsPedidosCasoSejaGerente() throws Exception {
        pedidoService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistencePedidoForTests);
        Usuario usuario = new Usuario("teste@test.com", "1234567", "000.000.000.00");
        usuario.setPapel(Papel.GERENTE);

        pedidoService.criarPedido(usuario);
        pedidoService.criarPedido(usuario);
        pedidoService.criarPedido(usuario);
        pedidoService.criarPedido(usuario);

        List<Pedido> pedidosList = pedidoService.buscarTodosOsPedidos(usuario);

        Assertions.assertEquals(4, pedidosList.size());
    }

    @Test
    void deveriaBuscarTodosOsPedidosCasoSejaFuncionario() throws Exception {
        pedidoService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistencePedidoForTests);
        Usuario usuario = new Usuario("teste@test.com", "1234567", "000.000.000.00");
        usuario.setPapel(Papel.FUNCIONARIO);

        pedidoService.criarPedido(usuario);
        pedidoService.criarPedido(usuario);
        pedidoService.criarPedido(usuario);
        pedidoService.criarPedido(usuario);

        List<Pedido> pedidosList = pedidoService.buscarTodosOsPedidos(usuario);

        Assertions.assertEquals(4, pedidosList.size());
    }

    @Test
    void deveriaRetornarErroQuandoOClienteTentarBuscarTodosOsPedidos() throws Exception {
        pedidoService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService(lanchePersistenceItemRepository,
                bebidaPersistenceItemRepository,
                acompanhamentoPersistenceItemRepository,
                sobremesaPersistenceItemRepository,
                mapPersistencePedidoForTests);
        Usuario usuario = new Usuario("teste@test.com", "1234567", "000.000.000.00");

        pedidoService.criarPedido(usuario);
        pedidoService.criarPedido(usuario);
        pedidoService.criarPedido(usuario);
        pedidoService.criarPedido(usuario);

        Assertions.assertThrows(Exception.class, ()-> pedidoService.buscarTodosOsPedidos(usuario));
    }
}
