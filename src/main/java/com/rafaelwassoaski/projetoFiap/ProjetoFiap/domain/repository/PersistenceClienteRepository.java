package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Cliente;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface PersistenceClienteRepository {
    Cliente salvar(Cliente cliente);

    Optional<Cliente> buscarPorEmail(String email);

    Optional<Cliente> buscarPorCpf(String cpf);

    List<Cliente> buscarTodos();

    void deletarPorEmail(String nome);

    Cliente atualizar(Cliente cliente);
}
