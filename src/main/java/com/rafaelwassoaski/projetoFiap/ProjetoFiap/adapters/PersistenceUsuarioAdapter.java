package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Item;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface PersistenceUsuarioAdapter {
    Usuario salvar(Usuario usuario);

    Optional<Usuario> buscarPorEmail(String email);

    List<Usuario> buscarTodos();

    void deletarPorEmail(String nome);

    Usuario atualizar(Usuario usuario);
}
