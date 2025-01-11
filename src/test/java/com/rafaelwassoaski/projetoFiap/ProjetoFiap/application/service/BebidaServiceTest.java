package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Bebida;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Item;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceItemForTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BebidaServiceTest {

    private BebidaService bebidaService;

    @Test
    void deveriaCriarUmItemValido() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        bebidaService = new BebidaService(mapPersistenceForTests);
        String nomeBebida = "Nome 1";
        double valor = 10;
        Bebida bebida = new Bebida(nomeBebida, valor);

        bebidaService.criar(bebida);
        Bebida bebidaCriado = (Bebida) bebidaService.buscarPorNome(nomeBebida);

        Assertions.assertNotNull(bebidaCriado);
        Assertions.assertInstanceOf(Bebida.class, bebidaCriado);
    }


    @Test
    void deveriaRetornarTodosOsItensSalvos() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        bebidaService = new BebidaService(mapPersistenceForTests);
        String nomeBebida1= "Nome 1";
        String nomeBebida2 = "Nome 2";
        double valor = 10;
        Bebida bebida1 = new Bebida(nomeBebida1, valor);
        Bebida bebida2 = new Bebida(nomeBebida2, valor);

        bebidaService.criar(bebida1);
        bebidaService.criar(bebida2);
        List<Bebida> Bebidas = bebidaService.buscarTodosOsItens();

        Assertions.assertEquals(2, Bebidas.size());
    }

    @Test
    void deveriaAtualizarOPrecoDeUmaBebidaSalva() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        bebidaService = new BebidaService(mapPersistenceForTests);
        String nomeBebida= "Nome 1";
        double valor = 10;
        Bebida Bebida = new Bebida(nomeBebida, valor);

        bebidaService.criar(Bebida);
        Bebida.setPreco(11);
        bebidaService.atualizar(Bebida);

        Bebida bebidaCriado = (Bebida) bebidaService.buscarPorNome(nomeBebida);

        Assertions.assertNotNull(bebidaCriado);
        Assertions.assertEquals(Bebida.getPreco(), bebidaCriado.getPreco());
    }

    @Test
    void deveriaDeletarUmItemSalvo() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        bebidaService = new BebidaService(mapPersistenceForTests);
        String nomeBebida= "Nome 1";
        double valor = 10;
        Bebida bebida = new Bebida(nomeBebida, valor);

        bebidaService.criar(bebida);
        bebidaService.deletarPorNome(nomeBebida);

         Assertions.assertThrows(Exception.class, ()->{
             bebidaService.buscarPorNome(nomeBebida);
         });
    }
}
