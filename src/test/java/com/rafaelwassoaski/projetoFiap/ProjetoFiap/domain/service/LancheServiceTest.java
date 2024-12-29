package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Lanche;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LancheServiceTest {

    private LancheService  lancheService;
    String nomeLanche = "Lanche 1";
    double preco = 10.0;

    @Test
    public void naoDeveriaCriarLancheComNomeNulo(){
        Assertions.assertThrows(Exception.class, () -> {
            Lanche lanche = new Lanche(null, preco);
            lancheService.validar(lanche);
        });
    }

    @Test
    public void naoDeveriaCriarLancheComNomeVazio(){
        Assertions.assertThrows(Exception.class, () -> {
            Lanche lanche = new Lanche("", preco);
            lancheService.validar(lanche);
        });
    }

    @Test
    public void naoDeveriaCriarLancheComValorZero(){
        Assertions.assertThrows(Exception.class, () -> {
            Lanche lanche = new Lanche(nomeLanche, 0);
            lancheService.validar(lanche);
        });
    }
}
