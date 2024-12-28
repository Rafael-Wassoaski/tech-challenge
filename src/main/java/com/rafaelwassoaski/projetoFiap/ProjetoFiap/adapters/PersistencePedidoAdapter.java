package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Pedido;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface PersistencePedidoAdapter {
    Pedido salvar(Pedido pedido);

    Optional<Pedido> buscarPorId(int id);

    List<Pedido> buscarTodos();

    Pedido atualizar(Pedido pedido);
}
