package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.mappers;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.BebidaEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Bebida;

public class BebidaMapper {

    public static Bebida converterParaBebida(BebidaEntity bebida){
        return new Bebida(bebida.getId(), bebida.getNome(), bebida.getPreco(), bebida.getCategoria());
    }

    public static BebidaEntity converterParaBebidaEntity(Bebida bebida){
        return new BebidaEntity(bebida.getId(), bebida.getNome(), bebida.getPreco(), bebida.getCategoria());
    }
}
