package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in.ClienteUseCase;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.ClienteService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Cliente;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceClienteRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private static final Logger log = LogManager.getLogger(ClienteController.class);
    private PersistenceClienteRepository persistenceClienteRepository;

    public ClienteController(PersistenceClienteRepository persistenceClienteRepository) {
        this.persistenceClienteRepository = persistenceClienteRepository;
    }

    @PostMapping("/cadastro")
    @ResponseStatus( HttpStatus.CREATED)
    public void cadastrar(@RequestBody Cliente cliente) {
        try {
            ClienteUseCase clienteService = new ClienteService(persistenceClienteRepository);
            clienteService.criar(cliente);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao cadastrar o cliente", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
