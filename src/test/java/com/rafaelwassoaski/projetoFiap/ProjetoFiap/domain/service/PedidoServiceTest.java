package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class PedidoServiceTest {

    private String nomeLanche = "Lanche 1";
    private String nomeBebida = "Bebida 1";
    private String nomeAcompanhamento = "Acompanhamento 1";
    private String nomeSobremesa = "Sobremesa 1";

    private com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.PedidoService pedidoService;


    @Test
    void deveriaCalcularOValorDeUmPedido() throws Exception {
        pedidoService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.PedidoService();

        Lanche lanche = new Lanche(nomeLanche, 10);
        Bebida bebida = new Bebida(nomeBebida, 10);
        Acompanhamento acompanhamento = new Acompanhamento(nomeAcompanhamento, 10);
        Sobremesa sobremesa = new Sobremesa(nomeSobremesa, 10);

        Pedido pedido = new Pedido(Optional.of(lanche), Optional.of(bebida), Optional.of(acompanhamento), Optional.of(sobremesa));

        Assertions.assertEquals(40, pedidoService.getTotal(pedido));
    }

    @Test
    void deveriaValidarSeUmNomeDeItemEhVazio() throws Exception {
        pedidoService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.PedidoService();

        Assertions.assertEquals(true, pedidoService.nomeDoItemEhVazio(""));
    }

}
