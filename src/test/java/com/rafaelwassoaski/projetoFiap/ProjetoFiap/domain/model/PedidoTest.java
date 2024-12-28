package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.StatusPedido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class PedidoTest {

    @Test
    void deveriaCriarUmPedidoComLancheBebidaAcompanhamentoESobremesa() throws Exception {
        Lanche lanche = new Lanche("Lanche 1", 10);
        Bebida bebida = new Bebida("Bebida 1", 10);
        Acompanhamento acompanhamento = new Acompanhamento("Acompanhamento 1", 10);
        Sobremesa sobremesa = new Sobremesa("Sobremesa 1", 10);

        Pedido pedido = new Pedido(1, Optional.of(lanche), Optional.of(bebida), Optional.of(acompanhamento), Optional.of(sobremesa));

        Assertions.assertEquals(lanche.getNome(), pedido.getLanche().get().getNome());
        Assertions.assertEquals(bebida.getNome(), pedido.getBebida().get().getNome());
        Assertions.assertEquals(acompanhamento.getNome(), pedido.getAcompanhamento().get().getNome());
        Assertions.assertEquals(sobremesa.getNome(), pedido.getSobremesa().get().getNome());
    }

    @Test
    void deveriaCriarUmPedidoSemNada() throws Exception {
        Pedido pedido = new Pedido(1, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

        Assertions.assertTrue(pedido.getLanche().isEmpty());
        Assertions.assertTrue(pedido.getAcompanhamento().isEmpty());
        Assertions.assertTrue(pedido.getBebida().isEmpty());
        Assertions.assertTrue(pedido.getAcompanhamento().isEmpty());
    }

    @Test
    void deveriaCriarUmPedidoComApenasABebida() throws Exception {
        Bebida bebida = new Bebida("Bebida 1", 10);

        Pedido pedido = new Pedido(1, Optional.empty(), Optional.of(bebida), Optional.empty(), Optional.empty());

        Assertions.assertEquals(bebida.getNome(), pedido.getBebida().get().getNome());
        Assertions.assertTrue(pedido.getAcompanhamento().isEmpty());
        Assertions.assertTrue(pedido.getLanche().isEmpty());
        Assertions.assertTrue(pedido.getAcompanhamento().isEmpty());
    }

    @Test
    void deveriaCriarUmPedidoComApenasOLanche() throws Exception {
        Lanche lanche = new Lanche("Lanche 1", 10);

        Pedido pedido = new Pedido(1, Optional.of(lanche), Optional.empty(), Optional.empty(), Optional.empty());
        Assertions.assertEquals(lanche.getNome(), pedido.getLanche().get().getNome());
        Assertions.assertTrue(pedido.getAcompanhamento().isEmpty());
        Assertions.assertTrue(pedido.getBebida().isEmpty());
        Assertions.assertTrue(pedido.getAcompanhamento().isEmpty());
    }

    @Test
    void deveriaCriarUmPedidoComApenasOAcompanhamento() throws Exception {
        Acompanhamento acompanhamento = new Acompanhamento("Acompanhamento 1", 10);

        Pedido pedido = new Pedido(1, Optional.empty(), Optional.empty(), Optional.of(acompanhamento), Optional.empty());

        Assertions.assertEquals(acompanhamento.getNome(), pedido.getAcompanhamento().get().getNome());
        Assertions.assertTrue(pedido.getLanche().isEmpty());
        Assertions.assertTrue(pedido.getBebida().isEmpty());
        Assertions.assertTrue(pedido.getSobremesa().isEmpty());
    }

    @Test
    void deveriaCriarUmPedidoComApenasASobremesa() throws Exception {
        Sobremesa sobremesa = new Sobremesa("Sobremesa 1", 10);

        Pedido pedido = new Pedido(1, Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(sobremesa));

        Assertions.assertEquals(sobremesa.getNome(), pedido.getSobremesa().get().getNome());
        Assertions.assertTrue(pedido.getLanche().isEmpty());
        Assertions.assertTrue(pedido.getBebida().isEmpty());
        Assertions.assertTrue(pedido.getAcompanhamento().isEmpty());
    }

    @Test
    void deveriaRetornarOTotalDoPedidoComBaseNosItens() throws Exception {
        Lanche lanche = new Lanche("Lanche 1", 10);
        Bebida bebida = new Bebida("Bebida 1", 10);
        Acompanhamento acompanhamento = new Acompanhamento("Acompanhamento 1", 10);
        Sobremesa sobremesa = new Sobremesa("Sobremesa 1", 10);

        Pedido pedido = new Pedido(1, Optional.of(lanche), Optional.of(bebida), Optional.of(acompanhamento), Optional.of(sobremesa));

        Assertions.assertEquals(40, pedido.getTotal());
    }

    @Test
    void deveriaCriarUmPedidoComOStatusDeRecebido() throws Exception {
        Lanche lanche = new Lanche("Lanche 1", 10);
        Bebida bebida = new Bebida("Bebida 1", 10);
        Acompanhamento acompanhamento = new Acompanhamento("Acompanhamento 1", 10);
        Sobremesa sobremesa = new Sobremesa("Sobremesa 1", 10);

        Pedido pedido = new Pedido(1, Optional.of(lanche), Optional.of(bebida), Optional.of(acompanhamento), Optional.of(sobremesa));

        Assertions.assertEquals(StatusPedido.RECEBIDO, pedido.getStatusPedido());
    }

    @Test
    void deveriaAtualizarOStatusDeUmPedido() throws Exception {
        Lanche lanche = new Lanche("Lanche 1", 10);
        Bebida bebida = new Bebida("Bebida 1", 10);
        Acompanhamento acompanhamento = new Acompanhamento("Acompanhamento 1", 10);
        Sobremesa sobremesa = new Sobremesa("Sobremesa 1", 10);

        Pedido pedido = new Pedido(1, Optional.of(lanche), Optional.of(bebida), Optional.of(acompanhamento), Optional.of(sobremesa));
        pedido.setStatusPedido(StatusPedido.EM_PREPARACAO);

        Assertions.assertEquals(StatusPedido.EM_PREPARACAO, pedido.getStatusPedido());
    }

    @Test
    void deveriaCriarUmPedidoComIdPreenchido() throws Exception {
        Lanche lanche = new Lanche("Lanche 1", 10);
        Bebida bebida = new Bebida("Bebida 1", 10);
        Acompanhamento acompanhamento = new Acompanhamento("Acompanhamento 1", 10);
        Sobremesa sobremesa = new Sobremesa("Sobremesa 1", 10);

        Pedido pedido = new Pedido(1, Optional.of(lanche), Optional.of(bebida), Optional.of(acompanhamento), Optional.of(sobremesa));

        Assertions.assertEquals(1, pedido.getId());
    }
}
