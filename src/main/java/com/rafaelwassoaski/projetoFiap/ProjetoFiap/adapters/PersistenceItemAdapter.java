package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Item;

import java.util.List;
import java.util.Optional;

public interface PersistenceItemAdapter {
    Item salvar(Item item);

    Optional<Item> buscarPorNome(String nome);

    List<Item> buscarTodos();

    void deletarPorNome(String nome);

    Item atualizar(Item itemSalvo);
}
