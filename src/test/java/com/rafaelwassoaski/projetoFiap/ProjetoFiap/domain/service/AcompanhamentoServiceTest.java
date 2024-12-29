package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Acompanhamento;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AcompanhamentoServiceTest {
    private AcompanhamentoService acompanhamentoService;
    private String nomeAcompanhamento = "Acompanhamento 1";
    private double preco = 10.0;


    @Test
    public void naoDeveriaCriarAcompanhamentoComNomeNulo(){
        Assertions.assertThrows(Exception.class, () -> {
            Acompanhamento acompanhamento = new Acompanhamento(null, preco);
            acompanhamentoService.validar(acompanhamento);
        });
    }

    @Test
    public void naoDeveriaCriarAcompanhamentoComNomeVazio(){
        Assertions.assertThrows(Exception.class, () -> {
            Acompanhamento acompanhamento = new Acompanhamento("", preco);
            acompanhamentoService.validar(acompanhamento);
        });
    }

    @Test
    public void naoDeveriaCriarAcompanhamentoComValorZero(){
        Assertions.assertThrows(Exception.class, () -> {
            Acompanhamento acompanhamento = new Acompanhamento(nomeAcompanhamento, 0);
            acompanhamentoService.validar(acompanhamento);
        });
    }
}
