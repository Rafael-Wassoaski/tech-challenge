package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AcompanhementoTest {
    String nomeAcompanhamento = "Acompanhamento 1";
    double preco = 10.0;

    @Test
    public void deveriaCriarAcompanhamentoComTodosOsAtributos() throws Exception {
        Acompanhamento Acompanhamento = new Acompanhamento(nomeAcompanhamento, preco);

        Assertions.assertEquals(nomeAcompanhamento, Acompanhamento.getNome());
        Assertions.assertEquals(preco, Acompanhamento.getPreco());
    }
}
