package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.PersistenceItemAdapter;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Item;

import java.util.List;
import java.util.Optional;

public abstract class ItemService {

    private PersistenceItemAdapter persistenceItemAdapter;

    public ItemService(PersistenceItemAdapter persistenceItemAdapter) {
        this.persistenceItemAdapter = persistenceItemAdapter;
    }

    public Item criar(Item item){
        return persistenceItemAdapter.salvar(item);
    }

    public Item buscarPorNome(String nome) throws Exception {
        Optional<Item> optionalItem = persistenceItemAdapter.buscarPorNome(nome);

        if(optionalItem.isEmpty()){
            throw new Exception(String.format("Item %s não encontrado", nome));
        }

        return optionalItem.get();
    }

    public List<Item> buscarTodosOsItens() {
        return persistenceItemAdapter.buscarTodos();
    }

    public Item atualizar(Item item) throws Exception {
        Item itemSalvo = this.buscarPorNome(item.getNome());
        itemSalvo.setPreco(item.getPreco());

        return this.persistenceItemAdapter.atualizar(itemSalvo);
    }

    public void deletarPorNome(String nomeLanche) throws Exception {
        Item itemSalvo = this.buscarPorNome(nomeLanche);

        this.persistenceItemAdapter.deletarPorNome(itemSalvo.getNome());
        return;
    }
}
