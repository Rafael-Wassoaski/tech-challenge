package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PersistencePedidoRepository {
    Pedido salvar(Pedido pedido);

    Optional<Pedido> buscarPorId(int id);

    List<Pedido> buscarTodos();

    Pedido atualizar(Pedido pedido);
}
