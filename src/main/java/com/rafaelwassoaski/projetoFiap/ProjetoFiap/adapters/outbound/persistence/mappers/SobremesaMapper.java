package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.mappers;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.AcompanhamentoEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.SobremesaEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Acompanhamento;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Sobremesa;

public class SobremesaMapper {

    public static Sobremesa converterParaSobremesa(SobremesaEntity sobremesa){
        return new Sobremesa(sobremesa.getId(), sobremesa.getNome(), sobremesa.getPreco(), sobremesa.getCategoria());
    }

    public static SobremesaEntity converterParaSobremesaEntity(Sobremesa sobremesa){
        return new SobremesaEntity(sobremesa.getId(), sobremesa.getNome(), sobremesa.getPreco(), sobremesa.getCategoria());
    }
}
