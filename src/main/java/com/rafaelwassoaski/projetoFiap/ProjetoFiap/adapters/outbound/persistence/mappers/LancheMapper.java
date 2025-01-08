package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.mappers;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.LancheEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Lanche;

public class LancheMapper {

    public static Lanche converterParaLanche(LancheEntity lancheEntity){
        return new Lanche(lancheEntity.getId(), lancheEntity.getNome(), lancheEntity.getPreco());
    }

    public static LancheEntity converterParaLancheEntity(Lanche lanche){
        return new LancheEntity(lanche.getId(), lanche.getNome(), lanche.getPreco());
    }
}
