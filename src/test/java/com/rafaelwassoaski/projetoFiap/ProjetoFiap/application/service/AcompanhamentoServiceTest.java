package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Acompanhamento;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Bebida;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceItemForTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AcompanhamentoServiceTest {

    private AcompanhamentoService acompanhamentoService;

    @Test
    void deveriaCriarUmItemValido() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        acompanhamentoService = new AcompanhamentoService(mapPersistenceForTests);
        String nomeAcompanhamento = "Nome 1";
        double valor = 10;
        Acompanhamento Acompanhamento = new Acompanhamento(nomeAcompanhamento, valor);

        acompanhamentoService.criar(Acompanhamento);
        Acompanhamento AcompanhamentoCriado = (Acompanhamento) acompanhamentoService.buscarPorNome(nomeAcompanhamento);

        Assertions.assertNotNull(AcompanhamentoCriado);
        Assertions.assertInstanceOf(Acompanhamento.class, AcompanhamentoCriado);
    }

    @Test
    public void naoDeveriaCriarDuasBebidasComOMesmoNome() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        acompanhamentoService = new AcompanhamentoService(mapPersistenceForTests);
        String nomeAcompanhamento = "Nome 1";
        double valor = 10;
        Acompanhamento acompanhamento = new Acompanhamento(nomeAcompanhamento, valor);

        acompanhamentoService.criar(acompanhamento);

        Assertions.assertThrows(Exception.class, () -> {
            acompanhamentoService.criar(acompanhamento);
        });
    }


    @Test
    void deveriaRetornarTodosOsItensSalvos() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        acompanhamentoService = new AcompanhamentoService(mapPersistenceForTests);
        String nomeAcompanhamento1= "Nome 1";
        String nomeAcompanhamento2 = "Nome 2";
        double valor = 10;
        Acompanhamento Acompanhamento1 = new Acompanhamento(nomeAcompanhamento1, valor);
        Acompanhamento Acompanhamento2 = new Acompanhamento(nomeAcompanhamento2, valor);

        acompanhamentoService.criar(Acompanhamento1);
        acompanhamentoService.criar(Acompanhamento2);
        List<Acompanhamento> Acompanhamentos = acompanhamentoService.buscarTodosOsItens();

        Assertions.assertEquals(2, Acompanhamentos.size());
    }

    @Test
    void deveriaAtualizarOPrecoDeUmAcompanhamentoSalvo() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        acompanhamentoService = new AcompanhamentoService(mapPersistenceForTests);
        String nomeAcompanhamento= "Nome 1";
        double valor = 10;
        Acompanhamento Acompanhamento = new Acompanhamento(nomeAcompanhamento, valor);

        acompanhamentoService.criar(Acompanhamento);
        Acompanhamento.setPreco(11);
        acompanhamentoService.atualizar(Acompanhamento);

        Acompanhamento AcompanhamentoCriado = (Acompanhamento) acompanhamentoService.buscarPorNome(nomeAcompanhamento);

        Assertions.assertNotNull(AcompanhamentoCriado);
        Assertions.assertEquals(Acompanhamento.getPreco(), AcompanhamentoCriado.getPreco());
    }

    @Test
    void deveriaDeletarUmItemSalvo() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        acompanhamentoService = new AcompanhamentoService(mapPersistenceForTests);
        String nomeAcompanhamento= "Nome 1";
        double valor = 10;
        Acompanhamento Acompanhamento = new Acompanhamento(nomeAcompanhamento, valor);

        acompanhamentoService.criar(Acompanhamento);
        acompanhamentoService.deletarPorNome(nomeAcompanhamento);

         Assertions.assertThrows(Exception.class, ()->{
             acompanhamentoService.buscarPorNome(nomeAcompanhamento);
         });
    }
}
