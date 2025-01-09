package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.mappers;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.AcompanhamentoEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.BebidaEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Acompanhamento;

public class AcompanhamentoMapper {

    public static Acompanhamento converterParaAcompanhamento(AcompanhamentoEntity acompanhamento){
        return new Acompanhamento(acompanhamento.getId(), acompanhamento.getNome(), acompanhamento.getPreco());
    }

    public static AcompanhamentoEntity converterParaAcompanhamentoEntity(Acompanhamento acompanhamento){
        return new AcompanhamentoEntity(acompanhamento.getId(), acompanhamento.getNome(), acompanhamento.getPreco());
    }
}
