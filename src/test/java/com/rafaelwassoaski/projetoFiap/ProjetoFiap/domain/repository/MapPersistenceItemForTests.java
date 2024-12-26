package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.PersistenceItemAdapter;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Item;

import java.util.*;

public class MapPersistenceItemForTests implements PersistenceItemAdapter {

    private Map<String, Item> itens;

    public MapPersistenceItemForTests() {
        this.itens = new HashMap<>();
    }

    @Override
    public Item salvar(Item item) {
        this.itens.put(item.getNome(), item);
        return item;
    }

    @Override
    public Optional<Item> buscarPorNome(String nome) {
        Item item = this.itens.get(nome);
        return Optional.of(item);
    }

    @Override
    public List<Item> buscarTodos() {
        return new ArrayList<Item>(itens.values());
    }

    @Override
    public void deletarPorNome(String nome) {
        this.itens.remove(nome);
    }

    @Override
    public Item atualizar(Item itemSalvo) {
        this.itens.put(itemSalvo.getNome(), itemSalvo);
        return this.itens.get(itemSalvo.getNome());
    }
}
