package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in.ItemUseCase;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Bebida;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;

public class BebidaService extends ItemUseCase {
    public BebidaService(PersistenceItemRepository<Bebida> persistenceItemRepository) {
        super(persistenceItemRepository);
    }
}
