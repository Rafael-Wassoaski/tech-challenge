package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in.ItemUseCase;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.StatusPedido;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.*;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistencePedidoRepository;

import java.util.List;
import java.util.Optional;

public class PedidoService {
    private PersistencePedidoRepository persistencePedidoRepository;
    private BebidaService bebidaService;
    private LancheService lancheService;
    private AcompanhamentoService acompanhamentoService;
    private SobremesaService sobremesaService;
    private com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.PedidoService pedidoService;


    public PedidoService(PersistenceItemRepository<Lanche> lanchePersistenceItemRepository,
                         PersistenceItemRepository<Bebida> bebidaPersistenceItemRepository,
                         PersistenceItemRepository<Acompanhamento> acompanhamentoPersistenceItemRepository,
                         PersistenceItemRepository<Sobremesa> sobremesaPersistenceItemRepository,
                         PersistencePedidoRepository persistencePedidoRepository) {
        this.persistencePedidoRepository = persistencePedidoRepository;
        this.bebidaService = new BebidaService(bebidaPersistenceItemRepository);
        this.lancheService = new LancheService(lanchePersistenceItemRepository);
        this.acompanhamentoService = new AcompanhamentoService(acompanhamentoPersistenceItemRepository);
        this.sobremesaService = new SobremesaService(sobremesaPersistenceItemRepository);
        this.pedidoService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.PedidoService();
    }

    public Pedido definirLanche(String nomeLanche, int id) throws Exception {
        Pedido pedido = buscarPedidoPorId(id);
        Optional<Lanche> optionalLanche = (Optional<Lanche>) buscarItemPorNome(nomeLanche, lancheService);
        pedido.setOptionalLanche(optionalLanche);

        return persistencePedidoRepository.atualizar(pedido);
    }

    public Pedido definirBebida(String nomeBebida, int id) throws Exception {
        Pedido pedido = buscarPedidoPorId(id);

        Optional<Bebida> optionalBebiba = (Optional<Bebida>) buscarItemPorNome(nomeBebida, bebidaService);
        pedido.setOptionalBebida(optionalBebiba);

        return persistencePedidoRepository.atualizar(pedido);
    }

    public Pedido definirAcompanhamento(String nomeAcompanhamento, int id) throws Exception {
        Pedido pedido = buscarPedidoPorId(id);
        Optional<Acompanhamento> optionalAcompanhamento = (Optional<Acompanhamento>) buscarItemPorNome(nomeAcompanhamento, acompanhamentoService);
        pedido.setOptionalAcompanhamento(optionalAcompanhamento);

        return persistencePedidoRepository.atualizar(pedido);
    }

    public Pedido definirSobremesa(String nomeSobremesa, int id) throws Exception {
        Pedido pedido = buscarPedidoPorId(id);
        Optional<Sobremesa> optionalSobremesa = (Optional<Sobremesa>) buscarItemPorNome(nomeSobremesa, sobremesaService);
        pedido.setOptionalSobremesa(optionalSobremesa);

        return persistencePedidoRepository.atualizar(pedido);
    }

    private Optional<? extends Item> buscarItemPorNome(String nomeItem, ItemUseCase itemUseCase) throws Exception {
        if (pedidoService.nomeDoItemEhVazio(nomeItem)) {
            return Optional.empty();
        }

        return Optional.ofNullable(itemUseCase.buscarPorNome(nomeItem));
    }

    public Pedido criarPedido(Cliente cliente) throws Exception {
        Pedido pedido = new Pedido();
        pedido.setCliente(Optional.of(cliente));
        return persistencePedidoRepository.salvar(pedido);
    }

    public Pedido criarPedido() throws Exception {
        return persistencePedidoRepository.salvar(new Pedido());
    }

    public Pedido prepararPedido(int id) throws Exception {
        Pedido pedido = buscarPedidoPorId(id);

        if (pedido.getStatusPedido() != StatusPedido.RECEBIDO) {
            throw new Exception("O status não pode ser alterado para EM PREPARAÇÃO");
        }

        pedido.setStatusPedido(StatusPedido.EM_PREPARACAO);

        return persistencePedidoRepository.atualizar(pedido);
    }

    public Pedido finalizarPreparacaoDoPedido(int id) throws Exception {
        Pedido pedido = buscarPedidoPorId(id);

        if (pedido.getStatusPedido() != StatusPedido.EM_PREPARACAO) {
            throw new Exception("O status não pode ser alterado para PRONTO");
        }

        pedido.setStatusPedido(StatusPedido.PRONTO);

        return persistencePedidoRepository.atualizar(pedido);
    }

    public Pedido retirarPedido(int id) throws Exception {
        Pedido pedido = buscarPedidoPorId(id);

        if (pedido.getStatusPedido() != StatusPedido.PRONTO) {
            throw new Exception("O status não pode ser alterado para RETIRADO");
        }

        pedido.setStatusPedido(StatusPedido.RETIRADO);

        return persistencePedidoRepository.atualizar(pedido);
    }

    private Pedido buscarPedidoPorId(int id) throws Exception {
        Optional<Pedido> optionalPedido = persistencePedidoRepository.buscarPorId(id);

        return optionalPedido.orElseThrow(() -> new Exception(String.format("Pedido com id %d não localizado", id)));
    }

    public List<Pedido> buscarTodosOsPedidos(Usuario usuario) throws Exception {

        if(!(Papel.GERENTE.equals(usuario.getPapel()) || Papel.FUNCIONARIO.equals(usuario.getPapel()))){
            throw new Exception("Usuário não tem permissão para visualizar estes dados");
        }

        return persistencePedidoRepository.buscarTodos();
    }

    public Pedido buscarPorId(Integer id, Cliente cliente) throws Exception {
        Pedido pedido = buscarPedidoPorId(id);

        if(!pedidoService.clientePodeVerPedido(pedido, cliente)){
            throw new Exception("Você não tem permissão para visualizar este pedido");
        }

        return pedido;
    }
}
