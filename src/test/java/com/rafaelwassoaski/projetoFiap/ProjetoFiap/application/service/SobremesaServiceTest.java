package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Sobremesa;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceItemForTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SobremesaServiceTest {

    private SobremesaService sobremesaService;

    @Test
    void deveriaCriarUmItemValido() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        sobremesaService = new SobremesaService(mapPersistenceForTests);
        String nomeSobremesa = "Nome 1";
        double valor = 10;
        Sobremesa Sobremesa = new Sobremesa(nomeSobremesa, valor);

        sobremesaService.criar(Sobremesa);
        Sobremesa SobremesaCriado = (Sobremesa) sobremesaService.buscarPorNome(nomeSobremesa);

        Assertions.assertNotNull(SobremesaCriado);
        Assertions.assertInstanceOf(Sobremesa.class, SobremesaCriado);
    }

    @Test
    public void naoDeveriaCriarDoisLanchesComOMesmoNome() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        sobremesaService = new SobremesaService(mapPersistenceForTests);
        String nomeSobremesa = "Nome 1";
        double valor = 10;
        Sobremesa sobremesa = new Sobremesa(nomeSobremesa, valor);

        sobremesaService.criar(sobremesa);

        Assertions.assertThrows(Exception.class, () -> {
            sobremesaService.criar(sobremesa);
        });
    }


    @Test
    void deveriaRetornarTodosOsItensSalvos() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        sobremesaService = new SobremesaService(mapPersistenceForTests);
        String nomeSobremesa1= "Nome 1";
        String nomeSobremesa2 = "Nome 2";
        double valor = 10;
        Sobremesa Sobremesa1 = new Sobremesa(nomeSobremesa1, valor);
        Sobremesa Sobremesa2 = new Sobremesa(nomeSobremesa2, valor);

        sobremesaService.criar(Sobremesa1);
        sobremesaService.criar(Sobremesa2);
        List<Sobremesa> Sobremesas = sobremesaService.buscarTodosOsItens();

        Assertions.assertEquals(2, Sobremesas.size());
    }

    @Test
    void deveriaAtualizarOPrecoDeUmSobremesaSalvo() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        sobremesaService = new SobremesaService(mapPersistenceForTests);
        String nomeSobremesa= "Nome 1";
        double valor = 10;
        Sobremesa Sobremesa = new Sobremesa(nomeSobremesa, valor);

        sobremesaService.criar(Sobremesa);
        Sobremesa.setPreco(11);
        sobremesaService.atualizar(Sobremesa);

        Sobremesa SobremesaCriado = (Sobremesa) sobremesaService.buscarPorNome(nomeSobremesa);

        Assertions.assertNotNull(SobremesaCriado);
        Assertions.assertEquals(Sobremesa.getPreco(), SobremesaCriado.getPreco());
    }

    @Test
    void deveriaDeletarUmItemSalvo() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        sobremesaService = new SobremesaService(mapPersistenceForTests);
        String nomeSobremesa= "Nome 1";
        double valor = 10;
        Sobremesa Sobremesa = new Sobremesa(nomeSobremesa, valor);

        sobremesaService.criar(Sobremesa);
        sobremesaService.deletarPorNome(nomeSobremesa);

         Assertions.assertThrows(Exception.class, ()->{
             sobremesaService.buscarPorNome(nomeSobremesa);
         });
    }
}
