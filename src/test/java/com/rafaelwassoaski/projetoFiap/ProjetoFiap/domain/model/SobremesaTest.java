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


}
