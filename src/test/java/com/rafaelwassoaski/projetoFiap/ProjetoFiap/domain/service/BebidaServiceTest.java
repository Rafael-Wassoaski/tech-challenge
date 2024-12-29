package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Bebida;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Lanche;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BebidaServiceTest {
    private BebidaService  bebidaService;
    String nomeBebida = "Bebida 1";
    double preco = 10.0;

    @Test
    public void naoDeveriaCriarBebidaComNomeNulo(){
        Assertions.assertThrows(Exception.class, () -> {
            Bebida bebida = new Bebida(null, preco);
            bebidaService.validar(bebida);
        });
    }

    @Test
    public void naoDeveriaCriarBebidaComNomeVazio(){
        Assertions.assertThrows(Exception.class, () -> {
            Bebida bebida = new Bebida("", preco);
            bebidaService.validar(bebida);
        });
    }

    @Test
    public void naoDeveriaCriarBebidaComValorZero(){
        Assertions.assertThrows(Exception.class, () -> {
            Bebida bebida = new Bebida(nomeBebida, 0);
            bebidaService.validar(bebida);
        });
    }
}
