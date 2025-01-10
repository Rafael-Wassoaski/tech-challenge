package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.BebidaService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Bebida;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/bebidas")
public class BebidaController {
    private PersistenceItemRepository<Bebida> bebidaPersistenceItemRepository;

    public BebidaController(PersistenceItemRepository<Bebida> bebidaPersistenceItemRepository) {
        this.bebidaPersistenceItemRepository = bebidaPersistenceItemRepository;
    }

    @GetMapping
    public List<Bebida> buscarTodasAsBebidas(){
        BebidaService bebidaService = new BebidaService(bebidaPersistenceItemRepository);

        return bebidaService.buscarTodosOsItens();
    }
}
