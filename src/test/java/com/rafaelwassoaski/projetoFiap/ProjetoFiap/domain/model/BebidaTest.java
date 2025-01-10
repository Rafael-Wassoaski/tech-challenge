package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BebidaTest {
    String nomeBebida = "Bebida 1";
    String categoria = "categoria bebida";
    double preco = 10.0;

    @Test
    public void deveriaCriarBebidaComTodosOsAtributos() throws Exception {
        Bebida bebida = new Bebida(nomeBebida, preco, categoria);

        Assertions.assertEquals(nomeBebida, bebida.getNome());
        Assertions.assertEquals(preco, bebida.getPreco());
        Assertions.assertEquals(categoria, bebida.getCategoria());
    }
}
