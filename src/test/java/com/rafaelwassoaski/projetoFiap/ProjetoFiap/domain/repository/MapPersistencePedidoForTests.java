package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Pedido;

import java.util.*;

public class MapPersistencePedidoForTests implements PersistencePedidoRepository {

    private Map<Integer, Pedido> pedidos;

    public MapPersistencePedidoForTests() {
        this.pedidos = new HashMap<>();
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        int id = pedidos.size() + 1;

        pedido.setId(id);
        this.pedidos.put(id, pedido);

        return pedido;
    }

    @Override
    public Optional<Pedido> buscarPorId(int id) {
        Pedido pedido = pedidos.get(id);

        if(pedido == null){
            return Optional.empty();
        }
        return Optional.of(pedido);
    }

    @Override
    public List<Pedido> buscarTodos() {
        return new ArrayList<Pedido>(pedidos.values());
    }

    @Override
    public Pedido atualizar(Pedido pedido) {
        this.pedidos.put(pedido.getId(), pedido);
        return this.pedidos.get(pedido.getId());
    }
}
