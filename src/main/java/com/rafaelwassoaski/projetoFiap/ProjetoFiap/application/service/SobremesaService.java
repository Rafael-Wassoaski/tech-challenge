package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in.ItemUseCase;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Sobremesa;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;

public class SobremesaService extends ItemUseCase {
    public SobremesaService(PersistenceItemRepository<Sobremesa> persistenceItemRepository) {
        super(persistenceItemRepository);
    }
}
