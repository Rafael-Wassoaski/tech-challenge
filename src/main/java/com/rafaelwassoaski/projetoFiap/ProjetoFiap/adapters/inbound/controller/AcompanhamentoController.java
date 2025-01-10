package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.AcompanhamentoService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.BebidaService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Acompanhamento;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Bebida;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/acompanhamentos")
public class AcompanhamentoController {
    private PersistenceItemRepository<Acompanhamento> acompanhamentoPersistenceItemRepository;

    public AcompanhamentoController(PersistenceItemRepository<Acompanhamento> acompanhamentoPersistenceItemRepository) {
        this.acompanhamentoPersistenceItemRepository = acompanhamentoPersistenceItemRepository;
    }

    @GetMapping
    public List<Acompanhamento> buscarTodosOsAcompanhamentos(){
        AcompanhamentoService acompanhamentoService = new AcompanhamentoService(acompanhamentoPersistenceItemRepository);

        return acompanhamentoService.buscarTodosOsItens();
    }
}
