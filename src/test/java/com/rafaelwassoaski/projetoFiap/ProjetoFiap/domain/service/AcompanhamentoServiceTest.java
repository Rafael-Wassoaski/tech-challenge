package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Acompanhamento;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Item;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceItemForTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AcompanhamentoServiceTest {

    private AcompanhamentoService AcompanhamentoService;

    @Test
    void deveriaCriarUmItemValido() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        AcompanhamentoService = new AcompanhamentoService(mapPersistenceForTests);
        String nomeAcompanhamento = "Nome 1";
        double valor = 10;
        Acompanhamento Acompanhamento = new Acompanhamento(nomeAcompanhamento, valor);

        AcompanhamentoService.criar(Acompanhamento);
        Acompanhamento AcompanhamentoCriado = (Acompanhamento) AcompanhamentoService.buscarPorNome(nomeAcompanhamento);

        Assertions.assertNotNull(AcompanhamentoCriado);
        Assertions.assertInstanceOf(Acompanhamento.class, AcompanhamentoCriado);
    }


    @Test
    void deveriaRetornarTodosOsItensSalvos() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        AcompanhamentoService = new AcompanhamentoService(mapPersistenceForTests);
        String nomeAcompanhamento1= "Nome 1";
        String nomeAcompanhamento2 = "Nome 2";
        double valor = 10;
        Acompanhamento Acompanhamento1 = new Acompanhamento(nomeAcompanhamento1, valor);
        Acompanhamento Acompanhamento2 = new Acompanhamento(nomeAcompanhamento2, valor);

        AcompanhamentoService.criar(Acompanhamento1);
        AcompanhamentoService.criar(Acompanhamento2);
        List<Item> Acompanhamentos = AcompanhamentoService.buscarTodosOsItens();

        Assertions.assertEquals(2, Acompanhamentos.size());
    }

    @Test
    void deveriaAtualizarOPrecoDeUmAcompanhamentoSalvo() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        AcompanhamentoService = new AcompanhamentoService(mapPersistenceForTests);
        String nomeAcompanhamento= "Nome 1";
        double valor = 10;
        Acompanhamento Acompanhamento = new Acompanhamento(nomeAcompanhamento, valor);

        AcompanhamentoService.criar(Acompanhamento);
        Acompanhamento.setPreco(11);
        AcompanhamentoService.atualizar(Acompanhamento);

        Acompanhamento AcompanhamentoCriado = (Acompanhamento) AcompanhamentoService.buscarPorNome(nomeAcompanhamento);

        Assertions.assertNotNull(AcompanhamentoCriado);
        Assertions.assertEquals(Acompanhamento.getPreco(), AcompanhamentoCriado.getPreco());
    }

    @Test
    void deveriaDeletarUmItemSalvo() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        AcompanhamentoService = new AcompanhamentoService(mapPersistenceForTests);
        String nomeAcompanhamento= "Nome 1";
        double valor = 10;
        Acompanhamento Acompanhamento = new Acompanhamento(nomeAcompanhamento, valor);

        AcompanhamentoService.criar(Acompanhamento);
        AcompanhamentoService.deletarPorNome(nomeAcompanhamento);

         Assertions.assertThrows(Exception.class, ()->{
             AcompanhamentoService.buscarPorNome(nomeAcompanhamento);
         });
    }
}
