package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

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

        Pedido Pedido = new Pedido(Optional.of(lanche), Optional.of(bebida), Optional.of(acompanhamento), Optional.of(sobremesa));

        Assertions.assertEquals(lanche.getNome(), Pedido.getLanche().get().getNome());
        Assertions.assertEquals(bebida.getNome(), Pedido.getBebida().get().getNome());
        Assertions.assertEquals(acompanhamento.getNome(), Pedido.getAcompanhamento().get().getNome());
        Assertions.assertEquals(sobremesa.getNome(), Pedido.getSobremesa().get().getNome());
    }

    @Test
    void deveriaCriarUmPedidoSemNada() throws Exception {
        Pedido Pedido = new Pedido(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

        Assertions.assertTrue(Pedido.getLanche().isEmpty());
        Assertions.assertTrue(Pedido.getAcompanhamento().isEmpty());
        Assertions.assertTrue(Pedido.getBebida().isEmpty());
        Assertions.assertTrue(Pedido.getAcompanhamento().isEmpty());
    }

    @Test
    void deveriaCriarUmPedidoComApenasABebida() throws Exception {
        Bebida bebida = new Bebida("Bebida 1", 10);

        Pedido Pedido = new Pedido(Optional.empty(), Optional.of(bebida), Optional.empty(), Optional.empty());

        Assertions.assertEquals(bebida.getNome(), Pedido.getBebida().get().getNome());
        Assertions.assertTrue(Pedido.getAcompanhamento().isEmpty());
        Assertions.assertTrue(Pedido.getLanche().isEmpty());
        Assertions.assertTrue(Pedido.getAcompanhamento().isEmpty());
    }

    @Test
    void deveriaCriarUmPedidoComApenasOLanche() throws Exception {
        Lanche lanche = new Lanche("Lanche 1", 10);

        Pedido Pedido = new Pedido(Optional.of(lanche), Optional.empty(), Optional.empty(), Optional.empty());
        Assertions.assertEquals(lanche.getNome(), Pedido.getLanche().get().getNome());
        Assertions.assertTrue(Pedido.getAcompanhamento().isEmpty());
        Assertions.assertTrue(Pedido.getBebida().isEmpty());
        Assertions.assertTrue(Pedido.getAcompanhamento().isEmpty());
    }

    @Test
    void deveriaCriarUmPedidoComApenasOAcompanhamento() throws Exception {
        Acompanhamento acompanhamento = new Acompanhamento("Acompanhamento 1", 10);

        Pedido Pedido = new Pedido(Optional.empty(), Optional.empty(), Optional.of(acompanhamento), Optional.empty());

        Assertions.assertEquals(acompanhamento.getNome(), Pedido.getAcompanhamento().get().getNome());
        Assertions.assertTrue(Pedido.getLanche().isEmpty());
        Assertions.assertTrue(Pedido.getBebida().isEmpty());
        Assertions.assertTrue(Pedido.getSobremesa().isEmpty());
    }

    @Test
    void deveriaCriarUmPedidoComApenasASobremesa() throws Exception {
        Sobremesa sobremesa = new Sobremesa("Sobremesa 1", 10);

        Pedido Pedido = new Pedido(Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(sobremesa));

        Assertions.assertEquals(sobremesa.getNome(), Pedido.getSobremesa().get().getNome());
        Assertions.assertTrue(Pedido.getLanche().isEmpty());
        Assertions.assertTrue(Pedido.getBebida().isEmpty());
        Assertions.assertTrue(Pedido.getAcompanhamento().isEmpty());
    }

    @Test
    void deveriaRetornarOTotalDoPedidoComBaseNosItens() throws Exception {
        Lanche lanche = new Lanche("Lanche 1", 10);
        Bebida bebida = new Bebida("Bebida 1", 10);
        Acompanhamento acompanhamento = new Acompanhamento("Acompanhamento 1", 10);
        Sobremesa sobremesa = new Sobremesa("Sobremesa 1", 10);

        Pedido Pedido = new Pedido(Optional.of(lanche), Optional.of(bebida), Optional.of(acompanhamento), Optional.of(sobremesa));

        Assertions.assertEquals(40, Pedido.getTotal());
    }
}
