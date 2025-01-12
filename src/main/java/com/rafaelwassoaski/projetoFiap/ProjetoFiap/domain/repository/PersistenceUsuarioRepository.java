package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface PersistenceUsuarioRepository{
    Usuario salvar(Usuario usuario);

    Optional<Usuario> buscarPorEmail(String email);

    Optional<Usuario> buscarPorCpf(String cpf);

    List<Usuario> buscarTodos();

    void deletarPorEmail(String nome);

    Usuario atualizar(Usuario usuario);
}
