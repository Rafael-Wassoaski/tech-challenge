package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.AcompanhamentoService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.SobremesaService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Acompanhamento;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Sobremesa;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/sobremesas")
public class SobremesaController {
    private PersistenceItemRepository<Sobremesa> sobremesaPersistenceItemRepository;

    public SobremesaController(PersistenceItemRepository<Sobremesa> sobremesaPersistenceItemRepository) {
        this.sobremesaPersistenceItemRepository = sobremesaPersistenceItemRepository;
    }

    @GetMapping
    public List<Sobremesa> buscarTodasAsSobremesas(){
        SobremesaService sobremesaService = new SobremesaService(sobremesaPersistenceItemRepository);

        return sobremesaService.buscarTodosOsItens();
    }
}
