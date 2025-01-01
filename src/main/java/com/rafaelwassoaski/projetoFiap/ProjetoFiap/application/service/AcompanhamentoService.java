package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in.ItemUseCase;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Acompanhamento;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;

public class AcompanhamentoService extends ItemUseCase {
    public AcompanhamentoService(PersistenceItemRepository<Acompanhamento> persistenceItemRepository) {
        super(persistenceItemRepository);
    }
}
