package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.repository;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.PedidoEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.mappers.PedidoMapper;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Pedido;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistencePedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface JpaPedidoRepository extends JpaRepository<PedidoEntity, Integer>, PersistencePedidoRepository {
    @Override
    default Pedido atualizar(Pedido pedido) {
        return salvar(pedido);
    }

    @Override
    default List<Pedido> buscarTodos() {
        List<PedidoEntity> pedidoEntities = findAll();

        return pedidoEntities.stream().map(PedidoMapper::converterParaPedido).collect(Collectors.toList());    }

    @Override
    default Optional<Pedido> buscarPorId(int id) {
        Optional<PedidoEntity> pedidoEntity = findById(id);

        if(pedidoEntity.isEmpty()){
            return Optional.empty();
        }

        PedidoEntity pedido = pedidoEntity.get();

        return Optional.of(PedidoMapper.converterParaPedido(pedido));
    }

    @Transactional
    @Override
    default Pedido salvar(Pedido pedido) {
        PedidoEntity pedidoEntity = PedidoMapper.converterParaPedidoEntity(pedido);
        PedidoEntity pedidoEntitySalvo = saveAndFlush(pedidoEntity);
        return PedidoMapper.converterParaPedido(pedidoEntitySalvo);
    }
}
