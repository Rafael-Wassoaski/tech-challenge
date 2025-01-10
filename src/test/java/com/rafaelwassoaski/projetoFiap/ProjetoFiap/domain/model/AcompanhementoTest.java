package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AcompanhementoTest {
    String nomeAcompanhamento = "Acompanhamento 1";
    String categoria = "categoria acompanhamento";
    double preco = 10.0;

    @Test
    public void deveriaCriarAcompanhamentoComTodosOsAtributos() throws Exception {
        Acompanhamento acompanhamento = new Acompanhamento(nomeAcompanhamento, preco, categoria);

        Assertions.assertEquals(nomeAcompanhamento, acompanhamento.getNome());
        Assertions.assertEquals(preco, acompanhamento.getPreco());
        Assertions.assertEquals(categoria, acompanhamento.getCategoria());
    }
}
