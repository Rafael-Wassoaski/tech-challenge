package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.*;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceItemForTests;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceUsuarioForTests;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.Encriptador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PedidoServiceTest {

    private String nomeLanche = "Lanche 1";
    private String nomeBebida = "Bebida 1";
    private String nomeAcompanhamento = "Acompanhamento 1";
    private String nomeSobremesa = "Sobremesa 1";

    private BebidaService bebidaService;
    private LancheService lancheService;
    private AcompanhamentoService acompanhamentoService;
    private SobremesaService sobremesaService;

    private MapPersistenceItemForTests mapPersistenceForTests;

    private PedidoService pedidoService;

    @BeforeEach
    void setup() throws Exception {
        Lanche lanche = new Lanche(nomeLanche, 10);
        Bebida bebida = new Bebida(nomeBebida, 10);
        Acompanhamento acompanhamento = new Acompanhamento(nomeAcompanhamento, 10);
        Sobremesa sobremesa = new Sobremesa(nomeSobremesa, 10);

        mapPersistenceForTests = new MapPersistenceItemForTests();

        bebidaService = new BebidaService(mapPersistenceForTests);
        lancheService = new LancheService(mapPersistenceForTests);
        acompanhamentoService = new AcompanhamentoService(mapPersistenceForTests);
        sobremesaService = new SobremesaService(mapPersistenceForTests);

        bebidaService.criar(bebida);
        lancheService.criar(lanche);
        acompanhamentoService.criar(acompanhamento);
        sobremesaService.criar(sobremesa);
    }

    @Test
    void deveriaCriarUmPedidoComONomeDosItens() throws Exception {
        pedidoService = new PedidoService(mapPersistenceForTests);

        pedidoService.definirLanche(nomeLanche);
        pedidoService.definirBebida(nomeBebida);
        pedidoService.definirAcompanhamento(nomeAcompanhamento);
        pedidoService.definirSobremesa(nomeSobremesa);

        Pedido Pedido = pedidoService.criarPedido();

        Assertions.assertEquals(nomeLanche, Pedido.getLanche().get().getNome());
        Assertions.assertEquals(nomeBebida, Pedido.getBebida().get().getNome());
        Assertions.assertEquals(nomeAcompanhamento, Pedido.getAcompanhamento().get().getNome());
        Assertions.assertEquals(nomeSobremesa, Pedido.getSobremesa().get().getNome());
    }

    @Test
    void deveriaCriarUmPedidoApenasComOLanche() throws Exception {
        pedidoService = new PedidoService(mapPersistenceForTests);

        pedidoService.definirLanche(nomeLanche);
        pedidoService.definirBebida(null);
        pedidoService.definirAcompanhamento(null);
        pedidoService.definirSobremesa(null);

        Pedido Pedido = pedidoService.criarPedido();

        Assertions.assertEquals(nomeLanche, Pedido.getLanche().get().getNome());
        Assertions.assertTrue(Pedido.getBebida().isEmpty());
        Assertions.assertTrue(Pedido.getAcompanhamento().isEmpty());
        Assertions.assertTrue(Pedido.getSobremesa().isEmpty());
    }

    @Test
    void deveriaCriarUmPedidoApenasComABebida() throws Exception {
        pedidoService = new PedidoService(mapPersistenceForTests);

        pedidoService.definirLanche(null);
        pedidoService.definirBebida(nomeBebida);
        pedidoService.definirAcompanhamento(null);
        pedidoService.definirSobremesa(null);

        Pedido Pedido = pedidoService.criarPedido();

        Assertions.assertEquals(nomeBebida, Pedido.getBebida().get().getNome());
        Assertions.assertTrue(Pedido.getLanche().isEmpty());
        Assertions.assertTrue(Pedido.getAcompanhamento().isEmpty());
        Assertions.assertTrue(Pedido.getSobremesa().isEmpty());
    }

    @Test
    void deveriaCriarUmPedidoApenasComOAcompanhamento() throws Exception {
        pedidoService = new PedidoService(mapPersistenceForTests);

        pedidoService.definirLanche(null);
        pedidoService.definirBebida(null);
        pedidoService.definirAcompanhamento(nomeAcompanhamento);
        pedidoService.definirSobremesa(null);

        Pedido Pedido = pedidoService.criarPedido();

        Assertions.assertEquals(nomeAcompanhamento, Pedido.getAcompanhamento().get().getNome());
        Assertions.assertTrue(Pedido.getLanche().isEmpty());
        Assertions.assertTrue(Pedido.getBebida().isEmpty());
        Assertions.assertTrue(Pedido.getSobremesa().isEmpty());
    }

    @Test
    void deveriaCriarUmPedidoApenasComASobremesa() throws Exception {
        pedidoService = new PedidoService(mapPersistenceForTests);

        pedidoService.definirLanche(null);
        pedidoService.definirBebida(null);
        pedidoService.definirAcompanhamento(null);
        pedidoService.definirSobremesa(nomeSobremesa);

        Pedido Pedido = pedidoService.criarPedido();

        Assertions.assertEquals(nomeSobremesa, Pedido.getSobremesa().get().getNome());
        Assertions.assertTrue(Pedido.getLanche().isEmpty());
        Assertions.assertTrue(Pedido.getBebida().isEmpty());
        Assertions.assertTrue(Pedido.getAcompanhamento().isEmpty());
    }

    @Test
    void deveriaCriarUmPedidoAtreladoAUmUsuario() throws Exception {
        pedidoService = new PedidoService(mapPersistenceForTests);

        pedidoService.definirLanche(nomeLanche);
        pedidoService.definirBebida(nomeBebida);
        pedidoService.definirAcompanhamento(nomeAcompanhamento);
        pedidoService.definirSobremesa(nomeSobremesa);

        UsuarioService usuarioService;
        MapPersistenceUsuarioForTests mapPersistenceUsuarioForTests;
        String salParaTestes = "salParaTestes";

        mapPersistenceUsuarioForTests = new MapPersistenceUsuarioForTests();
        Encriptador encriptador = new Encriptador(salParaTestes);
        usuarioService = new UsuarioService(mapPersistenceUsuarioForTests, encriptador);
        String email = "teste@teste.com";
        String senha = "teste123456";

        Usuario usuario = usuarioService.criar(email, senha);

        Pedido Pedido = pedidoService.criarPedido(usuario);

        Assertions.assertEquals(nomeLanche, Pedido.getLanche().get().getNome());
        Assertions.assertEquals(nomeBebida, Pedido.getBebida().get().getNome());
        Assertions.assertEquals(nomeAcompanhamento, Pedido.getAcompanhamento().get().getNome());
        Assertions.assertEquals(nomeSobremesa, Pedido.getSobremesa().get().getNome());
        Assertions.assertEquals(usuario.getEmail(), Pedido.getUsuario().getEmail());
    }
}
