package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.mappers;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.*;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.*;

import java.util.Optional;

public class PedidoMapper {

    public static Pedido converterParaPedido(PedidoEntity pedidoEntity){
        Optional<Lanche> optionalLanche = pegarLancheOpcional(pedidoEntity.getLanche());
        Optional<Bebida> optionalBebida = pegarBebidaOpcional(pedidoEntity.getBebida());
        Optional<Acompanhamento> optionalAcompanhamento = pegarAcompanhamentoOpcional(pedidoEntity.getAcompanhamento());
        Optional<Sobremesa> optionalSobremesa = pegarSobremesaOpcional(pedidoEntity.getSobremesa());
        Optional<Cliente> clienteOptional = pegarCliente(pedidoEntity.getCliente());

        return new Pedido(pedidoEntity.getId(),
                optionalLanche,
                optionalBebida,
                optionalAcompanhamento,
                optionalSobremesa,
                clienteOptional,
                pedidoEntity.getStatusPedido()
        );
    }

    private static Optional<Lanche> pegarLancheOpcional(LancheEntity lancheEntity){
        if (lancheEntity == null){
            return Optional.empty();
        }

        return Optional.of(LancheMapper.converterParaLanche(lancheEntity));
    }

    private static Optional<Bebida> pegarBebidaOpcional(BebidaEntity bebidaEntity){
        if (bebidaEntity == null){
            return Optional.empty();
        }

        return Optional.of(BebidaMapper.converterParaBebida(bebidaEntity));
    }

    private static Optional<Acompanhamento> pegarAcompanhamentoOpcional(AcompanhamentoEntity acompanhamentoEntity){
        if (acompanhamentoEntity == null){
            return Optional.empty();
        }

        return Optional.of(AcompanhamentoMapper.converterParaAcompanhamento(acompanhamentoEntity));
    }

    private static Optional<Sobremesa> pegarSobremesaOpcional(SobremesaEntity sobremesaEntity){
        if (sobremesaEntity == null){
            return Optional.empty();
        }

        return Optional.of(SobremesaMapper.converterParaSobremesa(sobremesaEntity));
    }

    private static Optional<Cliente> pegarCliente(ClienteEntity clienteEntity){
        if (clienteEntity == null){
            return Optional.empty();
        }

        Cliente cliente = ClienteMapper.converterParaCliente(clienteEntity);
        return Optional.of(cliente);
    }

    public static PedidoEntity converterParaPedidoEntity(Pedido pedido) {
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setStatusPedido(pedido.getStatusPedido()); ;

        if(pedido.getId() != null){
            pedidoEntity.setId(pedido.getId());
        }

        if(pedido.getCliente().isPresent()){
            pedidoEntity.setCliente(ClienteMapper.converterParaClienteEntity(pedido.getCliente().get()));
        }
        if(pedido.getLanche().isPresent()){
            pedidoEntity.setLanche(LancheMapper.converterParaLancheEntity(pedido.getLanche().get())) ;
        }

        if(pedido.getBebida().isPresent()){
            pedidoEntity.setBebida(BebidaMapper.converterParaBebidaEntity(pedido.getBebida().get()));
        }

        if(pedido.getAcompanhamento().isPresent()){
            pedidoEntity.setAcompanhamento(AcompanhamentoMapper.converterParaAcompanhamentoEntity(pedido.getAcompanhamento().get()));
        }
        if(pedido.getSobremesa().isPresent()){
            pedidoEntity.setSobremesa(SobremesaMapper.converterParaSobremesaEntity(pedido.getSobremesa().get()));
        }

        return pedidoEntity;
    }
}
