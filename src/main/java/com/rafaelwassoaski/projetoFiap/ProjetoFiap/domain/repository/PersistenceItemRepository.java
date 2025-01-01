package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Item;

import java.util.List;
import java.util.Optional;

public interface PersistenceItemRepository <T extends Item> {
    T salvar(T item);

    Optional<T> buscarPorNome(String nome);

    List<T> buscarTodos();

    void deletarPorNome(String nome);

    T atualizar(T itemSalvo);
}
