package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.PersistenceItemAdapter;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.PersistencePedidoAdapter;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.StatusPedido;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.*;

import java.util.Optional;

public class PedidoService {
    private PersistenceItemAdapter persistenceItemAdapter;
    private PersistencePedidoAdapter persistencePedidoAdapter;
    private BebidaService bebidaService;
    private LancheService lancheService;
    private AcompanhamentoService acompanhamentoService;
    private SobremesaService sobremesaService;
    private com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.PedidoService pedidoService;

    private Optional<Lanche> optionalLanche;
    private Optional<Bebida> optionalBebiba;
    private Optional<Acompanhamento> optionalAcompanhamento;
    private Optional<Sobremesa> optionalSobremesa;


    public PedidoService(PersistenceItemAdapter persistenceItemAdapter, PersistencePedidoAdapter persistencePedidoAdapter) {
        this.persistenceItemAdapter = persistenceItemAdapter;
        this.persistencePedidoAdapter = persistencePedidoAdapter;
        this.bebidaService = new BebidaService(persistenceItemAdapter);
        this.lancheService = new LancheService(persistenceItemAdapter);
        this.acompanhamentoService = new AcompanhamentoService(persistenceItemAdapter);
        this.sobremesaService = new SobremesaService(persistenceItemAdapter);
        this.pedidoService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.PedidoService();
    }

    public void definirLanche(String nomeLanche) throws Exception {
        if (pedidoService.nomeDoItemEhVazio(nomeLanche)) {
            optionalLanche = Optional.empty();
            return;
        }

        optionalLanche = Optional.of((Lanche) lancheService.buscarPorNome(nomeLanche));
    }

    public void definirBebida(String nomeBebida) throws Exception {
        if (pedidoService.nomeDoItemEhVazio(nomeBebida)) {
            optionalBebiba = Optional.empty();
            return;
        }

        optionalBebiba = Optional.of((Bebida) bebidaService.buscarPorNome(nomeBebida));
    }

    public void definirAcompanhamento(String nomeAcompanhamento) throws Exception {
        if (pedidoService.nomeDoItemEhVazio(nomeAcompanhamento)) {
            optionalAcompanhamento = Optional.empty();
            return;
        }

        optionalAcompanhamento = Optional.of((Acompanhamento) acompanhamentoService.buscarPorNome(nomeAcompanhamento));
    }

    public void definirSobremesa(String nomeSobremesa) throws Exception {
        if (pedidoService.nomeDoItemEhVazio(nomeSobremesa)) {
            optionalSobremesa = Optional.empty();
            return;
        }

        optionalSobremesa = Optional.of((Sobremesa) sobremesaService.buscarPorNome(nomeSobremesa));

    }

    public Pedido criarPedido(Usuario usuario) {
        Pedido pedido = new Pedido(optionalLanche, optionalBebiba, optionalAcompanhamento, optionalSobremesa);
        pedido.setUsuario(usuario);

        return persistencePedidoAdapter.salvar(pedido);
    }

    public Pedido criarPedido() {
        return new Pedido(optionalLanche, optionalBebiba, optionalAcompanhamento, optionalSobremesa);
    }

    public Pedido prepararPedido(int id) throws Exception {
        Pedido pedido = buscarPedidoPorId(id);

        if (pedido.getStatusPedido() != StatusPedido.RECEBIDO) {
            throw new Exception("O status não pode ser alterado para EM PREPARAÇÃO");
        }

        pedido.setStatusPedido(StatusPedido.EM_PREPARACAO);

        return persistencePedidoAdapter.atualizar(pedido);
    }

    public Pedido finalizarPreparacaoDoPedido(int id) throws Exception {
        Pedido pedido = buscarPedidoPorId(id);

        if (pedido.getStatusPedido() != StatusPedido.EM_PREPARACAO) {
            throw new Exception("O status não pode ser alterado para PRONTO");
        }

        pedido.setStatusPedido(StatusPedido.PRONTO);

        return persistencePedidoAdapter.atualizar(pedido);
    }

    public Pedido retirarPedido(int id) throws Exception {
        Pedido pedido = buscarPedidoPorId(id);

        if (pedido.getStatusPedido() != StatusPedido.PRONTO) {
            throw new Exception("O status não pode ser alterado para RETIRADO");
        }

        pedido.setStatusPedido(StatusPedido.RETIRADO);

        return persistencePedidoAdapter.atualizar(pedido);
    }

    private Pedido buscarPedidoPorId(int id) throws Exception {
        Optional<Pedido> optionalPedido = persistencePedidoAdapter.buscarPorId(id);

        return optionalPedido.orElseThrow(() -> new Exception(String.format("Pedido com id %d não localizado", id)));
    }
}
