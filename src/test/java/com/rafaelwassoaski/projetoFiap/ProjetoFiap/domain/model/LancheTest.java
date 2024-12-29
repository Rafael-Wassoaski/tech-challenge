package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LancheTest {
    String nomeLanche = "Lanche 1";
    double preco = 10.0;

    @Test
    public void deveriaCriarLancheComTodosOsAtributos() throws Exception {
        Lanche lanche = new Lanche(nomeLanche, preco);

        Assertions.assertEquals(nomeLanche, lanche.getNome());
        Assertions.assertEquals(preco, lanche.getPreco());
    }
}
