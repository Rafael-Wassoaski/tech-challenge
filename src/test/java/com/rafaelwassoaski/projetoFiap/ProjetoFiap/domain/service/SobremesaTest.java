package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Sobremesa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SobremesaTest {
    private SobremesaService sobremesaService;
    private String nomeSobremesa = "Sobremesa 1";
    private double preco = 10.0;

    @Test
    public void naoDeveriaCriarSobremesaComNomeNulo(){
        Assertions.assertThrows(Exception.class, () -> {
            Sobremesa sobremesa = new Sobremesa(null, preco);
            sobremesaService.validar(sobremesa);
        });
    }

    @Test
    public void naoDeveriaCriarSobremesaComNomeVazio(){
        Assertions.assertThrows(Exception.class, () -> {
            Sobremesa sobremesa = new Sobremesa("", preco);
            sobremesaService.validar(sobremesa);
        });
    }

    @Test
    public void naoDeveriaCriarSobremesaComValorZero(){
        Assertions.assertThrows(Exception.class, () -> {
            Sobremesa sobremesa = new Sobremesa(nomeSobremesa, 0);
            sobremesaService.validar(sobremesa);
        });
    }
}
