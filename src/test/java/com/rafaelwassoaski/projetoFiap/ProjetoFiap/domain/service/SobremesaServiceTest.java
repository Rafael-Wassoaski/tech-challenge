package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Sobremesa;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Item;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceItemForTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SobremesaServiceTest {

    private SobremesaService SobremesaService;

    @Test
    void deveriaCriarUmItemValido() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        SobremesaService = new SobremesaService(mapPersistenceForTests);
        String nomeSobremesa = "Nome 1";
        double valor = 10;
        Sobremesa Sobremesa = new Sobremesa(nomeSobremesa, valor);

        SobremesaService.criar(Sobremesa);
        Sobremesa SobremesaCriado = (Sobremesa) SobremesaService.buscarPorNome(nomeSobremesa);

        Assertions.assertNotNull(SobremesaCriado);
        Assertions.assertInstanceOf(Sobremesa.class, SobremesaCriado);
    }


    @Test
    void deveriaRetornarTodosOsItensSalvos() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        SobremesaService = new SobremesaService(mapPersistenceForTests);
        String nomeSobremesa1= "Nome 1";
        String nomeSobremesa2 = "Nome 2";
        double valor = 10;
        Sobremesa Sobremesa1 = new Sobremesa(nomeSobremesa1, valor);
        Sobremesa Sobremesa2 = new Sobremesa(nomeSobremesa2, valor);

        SobremesaService.criar(Sobremesa1);
        SobremesaService.criar(Sobremesa2);
        List<Item> Sobremesas = SobremesaService.buscarTodosOsItens();

        Assertions.assertEquals(2, Sobremesas.size());
    }

    @Test
    void deveriaAtualizarOPrecoDeUmSobremesaSalvo() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        SobremesaService = new SobremesaService(mapPersistenceForTests);
        String nomeSobremesa= "Nome 1";
        double valor = 10;
        Sobremesa Sobremesa = new Sobremesa(nomeSobremesa, valor);

        SobremesaService.criar(Sobremesa);
        Sobremesa.setPreco(11);
        SobremesaService.atualizar(Sobremesa);

        Sobremesa SobremesaCriado = (Sobremesa) SobremesaService.buscarPorNome(nomeSobremesa);

        Assertions.assertNotNull(SobremesaCriado);
        Assertions.assertEquals(Sobremesa.getPreco(), SobremesaCriado.getPreco());
    }

    @Test
    void deveriaDeletarUmItemSalvo() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        SobremesaService = new SobremesaService(mapPersistenceForTests);
        String nomeSobremesa= "Nome 1";
        double valor = 10;
        Sobremesa Sobremesa = new Sobremesa(nomeSobremesa, valor);

        SobremesaService.criar(Sobremesa);
        SobremesaService.deletarPorNome(nomeSobremesa);

         Assertions.assertThrows(Exception.class, ()->{
             SobremesaService.buscarPorNome(nomeSobremesa);
         });
    }
}
