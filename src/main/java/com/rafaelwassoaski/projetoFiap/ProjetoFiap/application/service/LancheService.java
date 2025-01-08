package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in.ItemUseCase;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Lanche;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;

public class LancheService extends ItemUseCase<Lanche> {
    public LancheService(PersistenceItemRepository<Lanche> persistenceItemRepository) {
        super(persistenceItemRepository);
    }
}
