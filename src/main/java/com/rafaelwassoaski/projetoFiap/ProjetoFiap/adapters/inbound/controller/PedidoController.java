package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.*;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistencePedidoRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceUsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Log4j2
@RestController("/pedidos")
public class PedidoController {

    private PersistenceItemRepository<Lanche> lanchePersistenceItemRepository;
    private PersistenceItemRepository<Bebida> bebidaPersistenceItemRepository;
    private PersistenceItemRepository<Acompanhamento> acompanhamentoPersistenceItemRepository;
    private PersistenceItemRepository<Sobremesa> sobremesaPersistenceItemRepository;
    private PersistenceUsuarioRepository persistenceUsuarioRepository;
    private PersistencePedidoRepository persistencePedidoRepository;
    private static final Logger log = LogManager.getLogger(PedidoController.class);

    public PedidoController(PersistenceItemRepository<Lanche> lanchePersistenceItemRepository,
                            PersistenceItemRepository<Bebida> bebidaPersistenceItemRepository,
                            PersistenceItemRepository<Acompanhamento> acompanhamentoPersistenceItemRepository,
                            PersistenceItemRepository<Sobremesa> sobremesaPersistenceItemRepository,
                            PersistenceUsuarioRepository persistenceUsuarioRepository,
                            PersistencePedidoRepository persistencePedidoRepository) {
        this.lanchePersistenceItemRepository = lanchePersistenceItemRepository;
        this.bebidaPersistenceItemRepository = bebidaPersistenceItemRepository;
        this.acompanhamentoPersistenceItemRepository = acompanhamentoPersistenceItemRepository;
        this.sobremesaPersistenceItemRepository = sobremesaPersistenceItemRepository;
        this.persistenceUsuarioRepository = persistenceUsuarioRepository;
        this.persistencePedidoRepository = persistencePedidoRepository;
    }


    @PostMapping("/criar")
    public Pedido criarPedido(Usuario usuario) {
        try {
            PedidoService pedidoService = new PedidoService(lanchePersistenceItemRepository,
                    bebidaPersistenceItemRepository,
                    acompanhamentoPersistenceItemRepository,
                    sobremesaPersistenceItemRepository,
                    persistencePedidoRepository);
            Pedido pedido = pedidoService.criarPedido();
            return pedidoService.iniciarPedido(usuario, pedido.getId());
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/adicionar/lanche")
    public Pedido adicionarLanche(Pedido pedido, String nomeLanche) {
        try {
            PedidoService pedidoService = new PedidoService(lanchePersistenceItemRepository,
                    bebidaPersistenceItemRepository,
                    acompanhamentoPersistenceItemRepository,
                    sobremesaPersistenceItemRepository,
                    persistencePedidoRepository);
            return pedidoService.definirLanche(nomeLanche, pedido.getId());
        } catch (Exception e) {
            log.error("Ocorreu um erro ao adicionar o lanche ao pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/adicionar/bebida")
    public Pedido adicionarBebida(Pedido pedido, String nomeBebida) {
        try {
            PedidoService pedidoService = new PedidoService(lanchePersistenceItemRepository,
                    bebidaPersistenceItemRepository,
                    acompanhamentoPersistenceItemRepository,
                    sobremesaPersistenceItemRepository,
                    persistencePedidoRepository);
            return pedidoService.definirBebida(nomeBebida, pedido.getId());
        } catch (Exception e) {
            log.error("Ocorreu um erro ao adicionar a bebida ao pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/adicionar/acompanhamento")
    public Pedido adicionarAcompanhamento(Pedido pedido, String nomeAcompanhamento) {
        try {
            PedidoService pedidoService = new PedidoService(lanchePersistenceItemRepository,
                    bebidaPersistenceItemRepository,
                    acompanhamentoPersistenceItemRepository,
                    sobremesaPersistenceItemRepository,
                    persistencePedidoRepository);
            return pedidoService.definirAcompanhamento(nomeAcompanhamento, pedido.getId());
        } catch (Exception e) {
            log.error("Ocorreu um erro ao adicionar o acompanhamento ao pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/adicionar/sobremesa")
    public Pedido adicionarSobremesa(Pedido pedido, String nomeSobremesa) {
        try {
            PedidoService pedidoService = new PedidoService(lanchePersistenceItemRepository,
                    bebidaPersistenceItemRepository,
                    acompanhamentoPersistenceItemRepository,
                    sobremesaPersistenceItemRepository,
                    persistencePedidoRepository);
            return pedidoService.definirSobremesa(nomeSobremesa, pedido.getId());
        } catch (Exception e) {
            log.error("Ocorreu um erro ao adicionar o acompanhamento ao pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/preparar")
    public Pedido prepararPedido(Pedido pedido) {
        try {
            PedidoService pedidoService = new PedidoService(lanchePersistenceItemRepository,
                    bebidaPersistenceItemRepository,
                    acompanhamentoPersistenceItemRepository,
                    sobremesaPersistenceItemRepository,
                    persistencePedidoRepository);
            return pedidoService.prepararPedido(pedido.getId());
        } catch (Exception e) {
            log.error("Ocorreu um erro ao adicionar o acompanhamento ao pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
