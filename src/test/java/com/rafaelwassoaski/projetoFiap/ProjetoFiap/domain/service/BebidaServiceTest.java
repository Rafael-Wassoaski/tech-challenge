package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Item;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Bebida;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceItemForTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BebidaServiceTest {

    private BebidaService BebidaService;

    @Test
    void deveriaCriarUmItemValido() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        BebidaService = new BebidaService(mapPersistenceForTests);
        String nomeBebida = "Nome 1";
        double valor = 10;
        Bebida Bebida = new Bebida(nomeBebida, valor);

        BebidaService.criar(Bebida);
        Bebida BebidaCriado = (Bebida) BebidaService.buscarPorNome(nomeBebida);

        Assertions.assertNotNull(BebidaCriado);
        Assertions.assertInstanceOf(Bebida.class, BebidaCriado);
    }


    @Test
    void deveriaRetornarTodosOsItensSalvos() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        BebidaService = new BebidaService(mapPersistenceForTests);
        String nomeBebida1= "Nome 1";
        String nomeBebida2 = "Nome 2";
        double valor = 10;
        Bebida Bebida1 = new Bebida(nomeBebida1, valor);
        Bebida Bebida2 = new Bebida(nomeBebida2, valor);

        BebidaService.criar(Bebida1);
        BebidaService.criar(Bebida2);
        List<Item> Bebidas = BebidaService.buscarTodosOsItens();

        Assertions.assertEquals(2, Bebidas.size());
    }

    @Test
    void deveriaAtualizarOPrecoDeUmBebidaSalvo() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        BebidaService = new BebidaService(mapPersistenceForTests);
        String nomeBebida= "Nome 1";
        double valor = 10;
        Bebida Bebida = new Bebida(nomeBebida, valor);

        BebidaService.criar(Bebida);
        Bebida.setPreco(11);
        BebidaService.atualizar(Bebida);

        Bebida BebidaCriado = (Bebida) BebidaService.buscarPorNome(nomeBebida);

        Assertions.assertNotNull(BebidaCriado);
        Assertions.assertEquals(Bebida.getPreco(), BebidaCriado.getPreco());
    }

    @Test
    void deveriaDeletarUmItemSalvo() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        BebidaService = new BebidaService(mapPersistenceForTests);
        String nomeBebida= "Nome 1";
        double valor = 10;
        Bebida Bebida = new Bebida(nomeBebida, valor);

        BebidaService.criar(Bebida);
        BebidaService.deletarPorNome(nomeBebida);

         Assertions.assertThrows(Exception.class, ()->{
             BebidaService.buscarPorNome(nomeBebida);
         });
    }
}
