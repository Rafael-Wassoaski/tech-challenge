package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.StatusPedido;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.financial.SomatoriaItem;

import java.util.Optional;

public class Pedido {

    private Optional<Lanche> optionalLanche;
    private Optional<Bebida> optionalBebida;
    private Optional<Acompanhamento> optionalAcompanhamento;
    private Optional<Sobremesa> optionalSobremesa;
    private Optional<Usuario> usuario;
    private StatusPedido statusPedido;
    private Integer id;

    public Pedido(Integer id, Optional<Lanche> lanche, Optional<Bebida> bebida, Optional<Acompanhamento> acompanhamento, Optional<Sobremesa>  sobremesa) {
        this.optionalLanche = lanche;
        this.optionalBebida = bebida;
        this.optionalAcompanhamento = acompanhamento;
        this.optionalSobremesa = sobremesa;
        this.statusPedido = StatusPedido.RECEBIDO;
        this.id = id;
    }

    public Pedido(Integer id, Optional<Lanche> optionalLanche, Optional<Bebida> optionalBebida, Optional<Acompanhamento> optionalAcompanhamento, Optional<Sobremesa> optionalSobremesa, Optional<Usuario> usuario, StatusPedido statusPedido) {
        this.optionalLanche = optionalLanche;
        this.optionalBebida = optionalBebida;
        this.optionalAcompanhamento = optionalAcompanhamento;
        this.optionalSobremesa = optionalSobremesa;
        this.usuario = usuario;
        this.statusPedido = statusPedido;
        this.id = id;
    }

    public Pedido() {
        this.optionalLanche = Optional.empty();
        this.optionalBebida = Optional.empty();
        this.optionalAcompanhamento = Optional.empty();
        this.optionalSobremesa = Optional.empty();
        this.usuario = Optional.empty();
        this.statusPedido = StatusPedido.RECEBIDO;
    }

    public Optional<Lanche> getLanche() {
        return optionalLanche;
    }

    public Optional<Bebida> getBebida() {
        return optionalBebida;
    }

    public Optional<Acompanhamento> getAcompanhamento() {
        return optionalAcompanhamento;
    }

    public Optional<Sobremesa> getSobremesa() {
        return optionalSobremesa;
    }

    public Optional<Usuario> getUsuario() {
        return usuario;
    }

    public Integer getId() {
        return id;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setUsuario(Optional<Usuario> usuario) {
        this.usuario = usuario;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOptionalLanche(Optional<Lanche> optionalLanche) {
        this.optionalLanche = optionalLanche;
    }

    public void setOptionalBebida(Optional<Bebida> optionalBebida) {
        this.optionalBebida = optionalBebida;
    }

    public void setOptionalAcompanhamento(Optional<Acompanhamento> optionalAcompanhamento) {
        this.optionalAcompanhamento = optionalAcompanhamento;
    }

    public void setOptionalSobremesa(Optional<Sobremesa> optionalSobremesa) {
        this.optionalSobremesa = optionalSobremesa;
    }
}
