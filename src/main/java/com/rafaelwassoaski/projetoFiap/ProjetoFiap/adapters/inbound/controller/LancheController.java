package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.LancheService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Lanche;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/lanches")
public class LancheController {
    private PersistenceItemRepository<Lanche> lanchePersistenceItemRepository;

    public LancheController(PersistenceItemRepository<Lanche> lanchePersistenceItemRepository) {
        this.lanchePersistenceItemRepository = lanchePersistenceItemRepository;
    }

    @GetMapping
    public List<Lanche> buscarTodosOsLanches(){
        LancheService lancheService = new LancheService(lanchePersistenceItemRepository);

        return lancheService.buscarTodosOsItens();

    }
}
