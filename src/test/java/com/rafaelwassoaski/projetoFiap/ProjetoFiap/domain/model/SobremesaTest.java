package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SobremesaTest {
    String nomeSobremesa = "Sobremesa 1";
    double preco = 10.0;

    @Test
    public void deveriaCriarSobremesaComTodosOsAtributos() throws Exception {
        Sobremesa Sobremesa = new Sobremesa(nomeSobremesa, preco);

        Assertions.assertEquals(nomeSobremesa, Sobremesa.getNome());
        Assertions.assertEquals(preco, Sobremesa.getPreco());
    }

    @Test
    public void naoDeveriaCriarSobremesaComNomeNulo(){
        Assertions.assertThrows(Exception.class, () -> {
            new Sobremesa(null, preco);
        });
    }

    @Test
    public void naoDeveriaCriarSobremesaComNomeVazio(){
        Assertions.assertThrows(Exception.class, () -> {
            new Sobremesa("", preco);
        });
    }

    @Test
    public void naoDeveriaCriarSobremesaComValorZero(){
        Assertions.assertThrows(Exception.class, () -> {
            new Sobremesa(nomeSobremesa, 0);
        });
    }
}
