package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.dto.AcompanhamentoDTO;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.dto.BebidaDTO;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.dto.LancheDTO;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.dto.SobremesaDTO;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in.ClienteUseCase;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.ClienteService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.*;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceClienteRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistencePedidoRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceUsuarioRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.ClienteDomainService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.JWTService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.utils.CookiesUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private PersistenceItemRepository<Lanche> lanchePersistenceItemRepository;
    private PersistenceItemRepository<Bebida> bebidaPersistenceItemRepository;
    private PersistenceItemRepository<Acompanhamento> acompanhamentoPersistenceItemRepository;
    private PersistenceItemRepository<Sobremesa> sobremesaPersistenceItemRepository;
    private PersistenceClienteRepository persistenceClienteRepository;
    private PersistenceUsuarioRepository persistenceUsuarioRepository;
    private PersistencePedidoRepository persistencePedidoRepository;
    private static final Logger log = LogManager.getLogger(PedidoController.class);
    private JWTService jwtService;

    public PedidoController(PersistenceItemRepository<Lanche> lanchePersistenceItemRepository,
                            PersistenceItemRepository<Bebida> bebidaPersistenceItemRepository,
                            PersistenceItemRepository<Acompanhamento> acompanhamentoPersistenceItemRepository,
                            PersistenceItemRepository<Sobremesa> sobremesaPersistenceItemRepository,
                            PersistenceClienteRepository persistenceClienteRepository,
                            PersistencePedidoRepository persistencePedidoRepository,
                            PersistenceUsuarioRepository persistenceUsuarioRepository,
                            JWTService jwtService) {
        this.lanchePersistenceItemRepository = lanchePersistenceItemRepository;
        this.bebidaPersistenceItemRepository = bebidaPersistenceItemRepository;
        this.acompanhamentoPersistenceItemRepository = acompanhamentoPersistenceItemRepository;
        this.sobremesaPersistenceItemRepository = sobremesaPersistenceItemRepository;
        this.persistenceClienteRepository = persistenceClienteRepository;
        this.persistenceUsuarioRepository = persistenceUsuarioRepository;
        this.persistencePedidoRepository = persistencePedidoRepository;
        this.jwtService = jwtService;
    }


    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido criarPedido(@RequestBody Cliente cliente) {
        try {
            ClienteUseCase clienteUseCase = new ClienteService(persistenceClienteRepository);
            ClienteDomainService clienteDomainService = new ClienteDomainService();
            String identificador = clienteDomainService.pegarIdentificador(cliente);
            Cliente clienteSalvo = clienteUseCase.buscarCliente(identificador);

            PedidoService pedidoService = new PedidoService(lanchePersistenceItemRepository,
                    bebidaPersistenceItemRepository,
                    acompanhamentoPersistenceItemRepository,
                    sobremesaPersistenceItemRepository,
                    persistencePedidoRepository);

            return pedidoService.criarPedido(clienteSalvo);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/criar/{cpf}")
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido criarPedido(@PathVariable String cpf) {
        try {
            ClienteUseCase clienteUseCase = new ClienteService(persistenceClienteRepository);
            Cliente cliente = clienteUseCase.buscarCliente(cpf);
            PedidoService pedidoService = new PedidoService(lanchePersistenceItemRepository,
                    bebidaPersistenceItemRepository,
                    acompanhamentoPersistenceItemRepository,
                    sobremesaPersistenceItemRepository,
                    persistencePedidoRepository);

            return pedidoService.criarPedido(cliente);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/criar/anonimo")
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido criarPedido() {
        try {
            PedidoService pedidoService = new PedidoService(lanchePersistenceItemRepository,
                    bebidaPersistenceItemRepository,
                    acompanhamentoPersistenceItemRepository,
                    sobremesaPersistenceItemRepository,
                    persistencePedidoRepository);

            return pedidoService.criarPedido();
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/buscar/todos")
    @ResponseStatus(HttpStatus.OK)
    public List<Pedido> buscarTodosOsPedidos(HttpServletRequest request) {
        try {
            String token = CookiesUtils.extractTokenCookie(request).get();
            String email = jwtService.getUsername(token);
            UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository);
            Usuario usuario = usuarioService.buscarUsuario(email);

            PedidoService pedidoService = new PedidoService(lanchePersistenceItemRepository,
                    bebidaPersistenceItemRepository,
                    acompanhamentoPersistenceItemRepository,
                    sobremesaPersistenceItemRepository,
                    persistencePedidoRepository);

            return pedidoService.buscarTodosOsPedidos(usuario);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/buscar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Pedido buscarPedidoPorId(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            ClienteUseCase clienteUseCase = new ClienteService(persistenceClienteRepository);
            ClienteDomainService clienteDomainService = new ClienteDomainService();
            String identificador = clienteDomainService.pegarIdentificador(cliente);
            Cliente clienteSalvo = clienteUseCase.buscarCliente(identificador);

            PedidoService pedidoService = new PedidoService(lanchePersistenceItemRepository,
                    bebidaPersistenceItemRepository,
                    acompanhamentoPersistenceItemRepository,
                    sobremesaPersistenceItemRepository,
                    persistencePedidoRepository);

            return pedidoService.buscarPorId(id, clienteSalvo);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/adicionar/lanche/{id}")
    public Pedido adicionarLanche(@PathVariable Integer id, @RequestBody LancheDTO lancheDTO) {
        try {
            PedidoService pedidoService = new PedidoService(lanchePersistenceItemRepository,
                    bebidaPersistenceItemRepository,
                    acompanhamentoPersistenceItemRepository,
                    sobremesaPersistenceItemRepository,
                    persistencePedidoRepository);
            return pedidoService.definirLanche(lancheDTO.getNomeDoLanche(), id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao adicionar o lanche ao pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/adicionar/bebida/{id}")
    public Pedido adicionarBebida(@PathVariable Integer id, @RequestBody BebidaDTO bebidaDTO) {
        try {
            PedidoService pedidoService = new PedidoService(lanchePersistenceItemRepository,
                    bebidaPersistenceItemRepository,
                    acompanhamentoPersistenceItemRepository,
                    sobremesaPersistenceItemRepository,
                    persistencePedidoRepository);
            return pedidoService.definirBebida(bebidaDTO.getNomeDaBebida(), id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao adicionar a bebida ao pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/adicionar/acompanhamento/{id}")
    public Pedido adicionarAcompanhamento(@PathVariable Integer id, @RequestBody AcompanhamentoDTO acompanhamentoDTO) {
        try {
            PedidoService pedidoService = new PedidoService(lanchePersistenceItemRepository,
                    bebidaPersistenceItemRepository,
                    acompanhamentoPersistenceItemRepository,
                    sobremesaPersistenceItemRepository,
                    persistencePedidoRepository);
            return pedidoService.definirAcompanhamento(acompanhamentoDTO.getNomeDoAcompanhamento(), id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao adicionar o acompanhamento ao pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/adicionar/sobremesa/{id}")
    public Pedido adicionarSobremesa(@PathVariable Integer id, @RequestBody SobremesaDTO sobremesaDTO) {
        try {
            PedidoService pedidoService = new PedidoService(lanchePersistenceItemRepository,
                    bebidaPersistenceItemRepository,
                    acompanhamentoPersistenceItemRepository,
                    sobremesaPersistenceItemRepository,
                    persistencePedidoRepository);
            return pedidoService.definirSobremesa(sobremesaDTO.getNomeDaSobremesa(), id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao adicionar o acompanhamento ao pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/preparar/{id}")
    public Pedido prepararPedido(@PathVariable int id) {
        try {
            PedidoService pedidoService = new PedidoService(lanchePersistenceItemRepository,
                    bebidaPersistenceItemRepository,
                    acompanhamentoPersistenceItemRepository,
                    sobremesaPersistenceItemRepository,
                    persistencePedidoRepository);
            return pedidoService.prepararPedido(id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao adicionar o acompanhamento ao pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
