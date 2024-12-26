package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BebidaTest {
    String nomeBebida = "Bebida 1";
    double preco = 10.0;

    @Test
    public void deveriaCriarBebidaComTodosOsAtributos() throws Exception {
        Bebida Bebida = new Bebida(nomeBebida, preco);

        Assertions.assertEquals(nomeBebida, Bebida.getNome());
        Assertions.assertEquals(preco, Bebida.getPreco());
    }

    @Test
    public void naoDeveriaCriarBebidaComNomeNulo(){
        Assertions.assertThrows(Exception.class, () -> {
            new Bebida(null, preco);
        });
    }

    @Test
    public void naoDeveriaCriarBebidaComNomeVazio(){
        Assertions.assertThrows(Exception.class, () -> {
            new Bebida("", preco);
        });
    }

    @Test
    public void naoDeveriaCriarBebidaComValorZero(){
        Assertions.assertThrows(Exception.class, () -> {
            new Bebida(nomeBebida, 0);
        });
    }
}
